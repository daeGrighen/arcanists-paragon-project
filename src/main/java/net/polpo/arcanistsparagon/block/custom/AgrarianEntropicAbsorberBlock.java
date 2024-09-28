package net.polpo.arcanistsparagon.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.polpo.arcanistsparagon.ArcanistsParagon;
import net.polpo.arcanistsparagon.util.DustCTPHelper;
import org.joml.Vector3f;

public class AgrarianEntropicAbsorberBlock extends Block {
    public static int ENTROPY_COST = 1;
    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if(world.getBlockState(pos.up()).getBlock() instanceof EntropyCellBlock){

            

            for(int x = -2; x<=2; x++){
                for(int y = -2; y<=2; y++){
                    for(int z = -2; z<=2; z++){
                        //ArcanistsParagon.LOGGER.info("checked pos "+ x + " "+ y + " "+ z);
                        BlockPos pos_check = new BlockPos(pos.getX()+x, pos.getY()+y, pos.getZ()+z);
                        BlockState cropBS = world.getBlockState(pos_check);
                        Block possibleCrop = cropBS.getBlock();
                        if(possibleCrop instanceof CropBlock){
                            CropBlock crop = (CropBlock) possibleCrop;
                            int age = crop.getAge(cropBS);
                            if(age==crop.getMaxAge()){
                                if(canAddOrRemoveEntropyToStorage(world.getBlockState(pos.up()), ENTROPY_COST)) {
                                    world.setBlockState(pos_check, cropBS.with(CropBlock.AGE, 0));
                                    addOrRemoveEntropyToStorage(world, pos.up(), world.getBlockState(pos.up()), ENTROPY_COST);
                                    spawnDustParticles(world, pos, DustCTPHelper.GREEN, DustCTPHelper.LIME, 20, 0.7f);
                                }else{
                                    spawnDustParticles(world, pos.up(), DustCTPHelper.BLACK, DustCTPHelper.RED, 7, 0.7f);
                                }
                            }
                        }
                    }
                }
            }

        }

    }


    public AgrarianEntropicAbsorberBlock(Settings settings) {
        super(settings);
    }

    protected void spawnDustParticles(ServerWorld world, BlockPos pos, Vector3f color1, Vector3f color2, int count, float spread) {
        world.spawnParticles(new DustCTPHelper(color1, color2, 1.0f), pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5, count, spread, spread, spread, 0.0f);
    }

    protected int getStorageCharges(BlockState state){
        if(state.getBlock() instanceof EntropyCellBlock block){
            return state.get(EntropyCellBlock.CHARGES).intValue();
        }
        return 0;
    }

    protected int getStorageMaxCharges(BlockState state){
        if(state.getBlock() instanceof EntropyCellBlock block){
            return block.getMaxCharges();
        }
        return 0;
    }

    protected void setStorageCharges(ServerWorld world, BlockPos storagePos, BlockState state, int charges){
        world.setBlockState(storagePos, state.with(EntropyCellBlock.CHARGES, charges));
    }

    protected boolean canModifyEntropyOfStorage(BlockState state, int final_charges){
        int max_charges = getStorageMaxCharges(state);

        return final_charges <= max_charges && final_charges >= 0;
    }

    protected boolean canAddOrRemoveEntropyToStorage(BlockState state, int change){
        int max_charges = getStorageMaxCharges(state);
        int current_charges = getStorageCharges(state);
        int final_charges = current_charges + change;

        return final_charges <= max_charges && final_charges >= 0;
    }

    protected boolean addOrRemoveEntropyToStorage(ServerWorld world, BlockPos storagePos, BlockState state, int change){
        /** if it can, returns true and adds entropy to the storage, if it can't it doesn't and returns false**/

        int current_charges = getStorageCharges(state);
        int final_charges = current_charges + change;

        if(canModifyEntropyOfStorage(state, final_charges)){
            setStorageCharges(world, storagePos, state, final_charges);
            return true;
        }
        return false;
    }
}
