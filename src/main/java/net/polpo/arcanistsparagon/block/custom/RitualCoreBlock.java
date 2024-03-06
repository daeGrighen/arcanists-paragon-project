package net.polpo.arcanistsparagon.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.polpo.arcanistsparagon.ArcanistsParagon;
import net.polpo.arcanistsparagon.block.ModBlocks;
import net.polpo.arcanistsparagon.block.entity.RitualCoreBlockEntity;
import net.polpo.arcanistsparagon.item.ModItems;
import net.polpo.arcanistsparagon.util.RitualRecipe;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class RitualCoreBlock extends BlockWithEntity implements GeoAnimatable{
    public static final MapCodec<RitualCoreBlock> CODEC = RitualCoreBlock.createCodec(RitualCoreBlock::new);
    public static final VoxelShape SHAPE = Stream.of(
            Block.createCuboidShape(2, 0, 2, 14, 1, 14),
            Block.createCuboidShape(3, 1, 3, 13, 2, 13),
            Block.createCuboidShape(6, 2, 6, 10, 4, 10)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();


    private final RitualRecipe BLAZING_COAL_RECIPE = new RitualRecipe(List.of(Items.COAL.getDefaultStack(), Items.COAL.getDefaultStack(), Items.COAL.getDefaultStack(), Items.BLAZE_POWDER.getDefaultStack()),
            Blocks.MAGMA_BLOCK.getDefaultState(), ModItems.BLAZING_COAL.getDefaultStack());

    private final List<RitualRecipe> RECIPES = List.of(BLAZING_COAL_RECIPE);


    public RitualCoreBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return null;
    }

    @Override
    public double getTick(Object object) {
        return 0;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new RitualCoreBlockEntity(pos, state);
    }



    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient()) return ActionResult.SUCCESS;

        if(isStructureCorrect(world, pos)){
            ArcanistsParagon.LOGGER.info("Structure is assembled!");
            List<ItemStack> itemList = List.of(
                    ((Inventory) world.getBlockEntity(pos.north(3))).getStack(0),
                    ((Inventory) world.getBlockEntity(pos.south(3))).getStack(0),
                    ((Inventory) world.getBlockEntity(pos.east(3))).getStack(0),
                    ((Inventory) world.getBlockEntity(pos.west(3))).getStack(0)
                    );
            ItemStack recipeResult =  getPossibleRecipe(itemList, world.getBlockState(pos.down()));

            if (!recipeResult.isEmpty()){
                ArcanistsParagon.LOGGER.info("Recipe matches!");
                Inventory northPedestal = (Inventory) world.getBlockEntity(pos.north(3));
                Inventory southPedestal = (Inventory) world.getBlockEntity(pos.south(3));
                Inventory eastPedestal = (Inventory) world.getBlockEntity(pos.east(3));
                Inventory westPedestal = (Inventory) world.getBlockEntity(pos.west(3));

                northPedestal.removeStack(0, 1);
                southPedestal.removeStack(0, 1);
                eastPedestal.removeStack(0, 1);
                westPedestal.removeStack(0, 1);

                double x = pos.getX();
                double y = pos.getY()+1;
                double z = pos.getZ();

                ItemEntity item_res_entity = new ItemEntity(world, x, y, z, recipeResult);
                world.spawnEntity(item_res_entity);
                ArcanistsParagon.LOGGER.info("Entity is (likely) spawned!");
            }


        }
        return ActionResult.SUCCESS;
    }

    private boolean isStructureCorrect(World world, BlockPos pos) {
        return world.getBlockState(pos.north(3)).isOf(ModBlocks.RITUAL_PEDESTAL) && world.getBlockState(pos.south(3)).isOf(ModBlocks.RITUAL_PEDESTAL) &&
                world.getBlockState(pos.east(3)).isOf(ModBlocks.RITUAL_PEDESTAL) && world.getBlockState(pos.west(3)).isOf(ModBlocks.RITUAL_PEDESTAL);
    }

    private ItemStack getPossibleRecipe(List<ItemStack> itemList, BlockState blockState) {
        for(RitualRecipe recipe : RECIPES){
            if(recipe.recipeMatches(itemList, blockState)){
                return recipe.getResult();
            }
        }
        return ItemStack.EMPTY;
    }


}
