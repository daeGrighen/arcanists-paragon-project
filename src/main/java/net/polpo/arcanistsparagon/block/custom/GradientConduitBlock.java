package net.polpo.arcanistsparagon.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import net.polpo.arcanistsparagon.ArcanistsParagon;
import net.polpo.arcanistsparagon.block.entity.ModBlockEntities;
import net.polpo.arcanistsparagon.block.entity.RitualTableBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class GradientConduitBlock extends FacingBlock {
    public static final DirectionProperty FACING = Properties.FACING;
    public static final MapCodec<GradientConduitBlock> CODEC = createCodec(GradientConduitBlock::new);
    public static final BooleanProperty POWERED_OLD = BooleanProperty.of("powered_old");

    private static final VoxelShape NORTH_SOUTH = Stream.of(
            Block.createCuboidShape(5, 5, 14, 11, 11, 16),
            Block.createCuboidShape(5, 5, 0, 11, 11, 2),
            Block.createCuboidShape(6, 6, 2, 10, 10, 14)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

    private static final VoxelShape EAST_WEST = Stream.of(
            Block.createCuboidShape(14, 5, 5, 16, 11, 11),
            Block.createCuboidShape(0, 5, 5, 2, 11, 11),
            Block.createCuboidShape(2, 6, 6, 14, 10, 10)
            ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

    private static final VoxelShape UP_DOWN = Stream.of(
            Block.createCuboidShape(5, 14, 5, 11, 16, 11),
            Block.createCuboidShape(5, 0, 5, 11, 2, 11),
            Block.createCuboidShape(6, 2, 6, 10, 14, 10)
            ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

    public GradientConduitBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(POWERED_OLD, false));
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!world.isClient()) {
            balanceCharges(world, state, pos);
            world.scheduleBlockTick(pos, state.getBlock(), 5);
        }
    }



    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {
        Direction dir = state.get(FACING);
        return switch (dir) {
            case NORTH -> NORTH_SOUTH;
            case SOUTH -> NORTH_SOUTH;
            case EAST -> EAST_WEST;
            case WEST -> EAST_WEST;
            case UP -> UP_DOWN;
            case DOWN -> UP_DOWN;
            default -> VoxelShapes.fullCube();
        };
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        world.scheduleBlockTick(pos, state.getBlock(), 5);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        //return this.getDefaultState().with(FACING, context.getPlayerLookDirection().getOpposite().getOpposite()).with(POWERED_OLD, false);
        return this.getDefaultState().with(FACING, context.getSide()).with(POWERED_OLD, false);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (world.isReceivingRedstonePower(pos)) {
            if(!state.get(POWERED_OLD)) {
                cyclePoweredState(world, pos);
                if (!world.isClient()) {
                    balanceCharges((ServerWorld) world, state, pos);
                }

            }
        }else{
            if(state.get(POWERED_OLD)){
                cyclePoweredState(world, pos);
            }
        }

        super.neighborUpdate(state, world, pos, block, fromPos, notify);
    }

    private void balanceCharges(ServerWorld world, BlockState state, BlockPos pos) {
        Direction facing = state.get(FACING);
        Direction opposite = facing.getOpposite();

        BlockPos cell1_pos = pos.offset(facing);
        BlockPos cell2_pos = pos.offset(opposite);

        BlockState cell1_state = world.getBlockState(cell1_pos);
        BlockState cell2_state = world.getBlockState(cell2_pos);

        Block cell1_block = cell1_state.getBlock();
        Block cell2_block = cell2_state.getBlock();


        if (cell1_block instanceof EntropyCellBlock && cell2_block instanceof EntropyCellBlock) {
            int cell1_charges = cell1_state.get(EntropyCellBlock.CHARGES).intValue();
            int cell2_charges = cell2_state.get(EntropyCellBlock.CHARGES).intValue();
            int maxChargesCell1 = ((EntropyCellBlock) cell1_state.getBlock()).getMaxCharges();
            int maxChargesCell2 = ((EntropyCellBlock) cell2_state.getBlock()).getMaxCharges();


            int[] balancedCharges = balanceCharges(cell1_charges, maxChargesCell1, cell2_charges, maxChargesCell2);

            //set the final charges
            world.setBlockState(cell1_pos, cell1_state.with(EntropyCellBlock.CHARGES, balancedCharges[0]));
            world.setBlockState(cell2_pos, cell2_state.with(EntropyCellBlock.CHARGES, balancedCharges[1]));
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED_OLD);
    }

    private void cyclePoweredState(World world, BlockPos pos){
        ServerWorld server = (ServerWorld) world;
        server.setBlockState(pos, world.getBlockState(pos).cycle(POWERED_OLD));
    }

    public static int[] balanceCharges(int cell1_charges, int maxChargesCell1, int cell2_charges, int maxChargesCell2) {
        int totalCharges = cell1_charges + cell2_charges;
        int finalChargesCell1, finalChargesCell2;

        finalChargesCell1 = totalCharges / 2;
        finalChargesCell2 = totalCharges - finalChargesCell1;

        if (finalChargesCell1 > maxChargesCell1) {
            int overflow = finalChargesCell1 - maxChargesCell1;
            finalChargesCell1 = maxChargesCell1;
            finalChargesCell2 += overflow;
        }

        if (finalChargesCell2 > maxChargesCell2) {
            int overflow = finalChargesCell2 - maxChargesCell2;
            finalChargesCell2 = maxChargesCell2;
            finalChargesCell1 += overflow;
        }

        if (finalChargesCell1 + finalChargesCell2 < totalCharges) {
            if (cell1_charges > cell2_charges && finalChargesCell1 < maxChargesCell1) {
                finalChargesCell1++;
            } else if (finalChargesCell2 < maxChargesCell2) {
                finalChargesCell2++;
            }
        }

        return new int[]{finalChargesCell1, finalChargesCell2};
    }

    @Override
    protected MapCodec<? extends FacingBlock> getCodec() {
        return CODEC;
    }

}
