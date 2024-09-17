package net.polpo.arcanistsparagon.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemConvertible;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.polpo.arcanistsparagon.item.ModItems;

public class RisingbulbCropBlock extends CropBlock {
    public static final int MAX_AGE = 8;
    public static final IntProperty AGE = IntProperty.of("age", 0, 8);
    public static final VoxelShape AGE_8_SHAPE =  Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if(this.getAge(state)==8){
            return AGE_8_SHAPE;
        }
        return super.getOutlineShape(state, world, pos, context);
    }

    public RisingbulbCropBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    public IntProperty getAgeProperty() {
        return AGE;
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return ModItems.RISINGBULB_CLOVE;
    }
}
