package net.polpo.arcanistsparagon.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;

import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import net.minecraft.world.World;

import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.polpo.arcanistsparagon.ArcanistsParagon;
import org.apache.commons.io.input.ObservableInputStream;
import org.apache.commons.io.input.SequenceReader;


public class SwapperBlock extends FacingBlock {
    public static final DirectionProperty FACING = Properties.FACING;
    public static final MapCodec<SwapperBlock> CODEC = createCodec(SwapperBlock::new);
    public static final BooleanProperty POWERED_OLD = BooleanProperty.of("powered_old");

    public SwapperBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(POWERED_OLD, false));
    }

    @Override
    protected MapCodec<? extends FacingBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING, POWERED_OLD});
    }


    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (world.isReceivingRedstonePower(pos)) {
            if(!state.get(POWERED_OLD)) {
                cyclePoweredState(world, pos);
                if (!world.isClient()) {
                    ArcanistsParagon.LOGGER.info("swapBlocks called!");
                    swapBlocks((ServerWorld) world, state, pos);
                }

            }
        }else{
            if(state.get(POWERED_OLD)){
                cyclePoweredState(world, pos);
            }
        }

        super.neighborUpdate(state, world, pos, block, fromPos, notify);
    }

    private void cyclePoweredState(World world, BlockPos pos){
        ServerWorld server = (ServerWorld) world;
        server.setBlockState(pos, world.getBlockState(pos).cycle(POWERED_OLD));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return this.getDefaultState().with(FACING, context.getPlayerLookDirection().getOpposite().getOpposite()).with(POWERED_OLD, false);
    }


    // Swaps the blocks on the facing and opposite directions.
    private void swapBlocks(ServerWorld world, BlockState state, BlockPos pos) {
        Direction facing = state.get(FACING);
        Direction opposite = facing.getOpposite();

        BlockPos facingPos = pos.offset(facing);
        BlockPos oppositePos = pos.offset(opposite);

        // Get the blocks in the two positions
        BlockState facingBlock = world.getBlockState(facingPos);
        BlockState oppositeBlock = world.getBlockState(oppositePos);

        if (!facingBlock.isAir() && !oppositeBlock.isAir()) {
            world.setBlockState(facingPos, oppositeBlock);
            world.setBlockState(oppositePos, facingBlock);
        }
    }

}