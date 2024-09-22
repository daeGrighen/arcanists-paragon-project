package net.polpo.arcanistsparagon.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntropyCellBlock extends Block {
    public static final int MAX_CHARGES = 8;
    public static final IntProperty CHARGES = IntProperty.of("charges", 0, 8);

    public EntropyCellBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CHARGES);
    }

    public int getMaxCharges(){
        return MAX_CHARGES;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient()){
            int charges = getCharges(state);
            int newCharges = charges;
            if(player.isSneaking()){
                if(charges!=0){
                    newCharges--;
                }
            }else{
                if(charges!=MAX_CHARGES){
                    newCharges++;
                }
            }
            setCharges((ServerWorld) world, pos, state, newCharges);
            player.sendMessage(Text.of("charges=" + newCharges), true);
        }
        return ActionResult.SUCCESS;
    }

    public int getCharges(BlockState state){
        return (Integer) state.get(CHARGES);
    }

    public void setCharges(ServerWorld world, BlockPos pos, BlockState state, int charges){
        world.setBlockState(pos, state.with(CHARGES, charges));
    }


}
