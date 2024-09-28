package net.polpo.arcanistsparagon.block.custom;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.polpo.arcanistsparagon.ArcanistsParagon;
import net.polpo.arcanistsparagon.util.DustCTPHelper;
import net.polpo.arcanistsparagon.util.ModTags;
import org.joml.Vector3f;

public class ScionbloomBlossomBlock extends FlowerBlock{
    public static int ENTROPY_COST = 1;
    public static final BooleanProperty SCHEDULING_TICK = BooleanProperty.of("scheduling_tick");

    public ScionbloomBlossomBlock(StatusEffect stewEffect, int duration, Settings settings) {
        super(stewEffect, duration, settings);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SCHEDULING_TICK);
    }

    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!world.isClient()) {
            ServerWorld serverWorld = (ServerWorld) world;
            boolean is_floor_good = canAddOrRemoveEntropyToStorage(world.getBlockState(pos.down()), ENTROPY_COST);
            serverWorld.setBlockState(pos, state.with(SCHEDULING_TICK, is_floor_good));
            if(!is_floor_good){
                world.scheduleBlockTick(pos, state.getBlock(), 2);
            }
        }
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(ModTags.Blocks.ENTROPY_STORAGE_BLOCKS);
    }


    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if(!world.isClient()){
            BlockState floorBlockState = world.getBlockState(pos.down());

            if(entity instanceof MobEntity mob && !mob.isBaby()){
                ServerWorld serverWorld = (ServerWorld) world;

                if(canAddOrRemoveEntropyToStorage(floorBlockState, ENTROPY_COST)) {
                    addOrRemoveEntropyToStorage(serverWorld, pos.down(), floorBlockState, ENTROPY_COST);
                    mob.setBaby(true);
                    spawnDustParticles(serverWorld, pos, DustCTPHelper.BLACK, DustCTPHelper.HOT_PINK, 20, 0.2f);

                }else{
                    if(!state.get(SCHEDULING_TICK).booleanValue()) {
                        ArcanistsParagon.LOGGER.info("Scheduling the first tick!");
                        serverWorld.scheduleBlockTick(pos, state.getBlock(), 2);
                        serverWorld.setBlockState(pos, state.with(SCHEDULING_TICK, true));
                    }
                }
            }
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient()){
            ArcanistsParagon.LOGGER.info(world.getBlockState(pos).get(SCHEDULING_TICK).toString());
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if(!canAddOrRemoveEntropyToStorage(world.getBlockState(pos.down()), ENTROPY_COST)){
            ArcanistsParagon.LOGGER.info("Another tick scheduled");
            spawnDustParticles(world, pos.down(), DustCTPHelper.CRIMSON, DustCTPHelper.GRAY, 20, 0.6f);
            world.scheduleBlockTick(pos, state.getBlock(), 20);
        }else{
            ArcanistsParagon.LOGGER.info("Stopped the scheduling");
            world.setBlockState(pos, state.with(SCHEDULING_TICK, false));
            spawnDustParticles(world, pos.down(), DustCTPHelper.GREEN, DustCTPHelper.LIME, 20, 0.6f);
        }
    }

    protected void spawnDustParticles(ServerWorld world, BlockPos pos, Vector3f color1, Vector3f color2, int count, float spread) {
        world.spawnParticles(new DustCTPHelper(color1, color2, 1.0f), pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5, count, spread, spread, spread, 0.0f);
    }

    protected int getStorageCharges(BlockState state){
        return state.get(EntropyCellBlock.CHARGES).intValue();
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
