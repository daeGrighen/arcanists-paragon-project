package net.polpo.arcanistsparagon.block.custom;

import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.polpo.arcanistsparagon.ArcanistsParagon;
import net.polpo.arcanistsparagon.util.ArcanistsParagonParticleDecoder;
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

    private static final String particle = ParticleTypes.ASH.asString();

    private static final RitualRecipe BLAZING_COAL_RECIPE =
            new RitualRecipe(List.of(Items.COAL.getDefaultStack(), Items.COAL.getDefaultStack(), Items.COAL.getDefaultStack(), Items.BLAZE_POWDER.getDefaultStack()),
            Blocks.MAGMA_BLOCK.getDefaultState(), ModItems.BLAZING_COAL.getDefaultStack());

    private static final RitualRecipe ARCANE_CORE_RECIPE =
            new RitualRecipe(List.of(Items.ANDESITE.getDefaultStack(), ModItems.ASPHODITE_CHUNK.getDefaultStack(), Items.IRON_NUGGET.getDefaultStack(), Items.ENDER_PEARL.getDefaultStack()),
            Blocks.OBSIDIAN.getDefaultState(), ModItems.ARCANE_CORE.getDefaultStack());

    private static final List<RitualRecipe> RECIPES = List.of(
            BLAZING_COAL_RECIPE,
            ARCANE_CORE_RECIPE);


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

        if(player.getMainHandStack().isOf(Items.STICK)) {
            if (isStructureCorrect(world, pos)) {
                ArcanistsParagon.LOGGER.info("Structure is assembled!");

                Inventory northPedestal = (Inventory) world.getBlockEntity(pos.north(3));
                Inventory southPedestal = (Inventory) world.getBlockEntity(pos.south(3));
                Inventory eastPedestal = (Inventory) world.getBlockEntity(pos.east(3));
                Inventory westPedestal = (Inventory) world.getBlockEntity(pos.west(3));

                List<ItemStack> itemList = List.of(
                        northPedestal.getStack(0),
                        southPedestal.getStack(0),
                        eastPedestal.getStack(0),
                        westPedestal.getStack(0)
                );

                ItemStack recipeResult = getPossibleRecipe(itemList, world.getBlockState(pos.down()), world);
                ArcanistsParagon.LOGGER.info(recipeResult.toString());

                if (!recipeResult.isEmpty()) {

                    int maxDrops = getMaxCraft(itemList);

                    northPedestal.removeStack(0, maxDrops);
                    northPedestal.markDirty();

                    southPedestal.removeStack(0, maxDrops);
                    southPedestal.markDirty();

                    eastPedestal.removeStack(0, maxDrops);
                    eastPedestal.markDirty();

                    westPedestal.removeStack(0, maxDrops);
                    westPedestal.markDirty();


                    double x = pos.getX() +0.5;
                    double y = pos.getY() +0.9;
                    double z = pos.getZ() +0.5;

                    ItemEntity itemResEntity = new ItemEntity(world, x, y +2.0, z, recipeResult.copyWithCount(maxDrops));

                    ArcanistsParagon.LOGGER.info("3");
                    world.spawnEntity(itemResEntity);
                    world.playSound(null, pos,
                            SoundEvents.ENTITY_PARROT_IMITATE_WARDEN,
                            SoundCategory.BLOCKS, 2f, 15f);

                    Vec3d pedestalPos = new Vec3d(x, y +2.0, z);
                    Vec3d northPedestalPos = new Vec3d(x, y, z + 3);
                    Vec3d eastPedestalPos = new Vec3d(x + 3, y, z);
                    Vec3d southPedestalPos = new Vec3d(x, y, z - 3);
                    Vec3d westPedestalPos = new Vec3d(x - 3, y, z);

                    List<Vec3d> pedestalPositions = new ArrayList<>();

                    pedestalPositions.add(northPedestalPos);
                    pedestalPositions.add(eastPedestalPos);
                    pedestalPositions.add(southPedestalPos);
                    pedestalPositions.add(westPedestalPos);
                    int particleAmount = (int) (pedestalPos.distanceTo(northPedestalPos) * 6);

                    for(Vec3d vec : pedestalPositions) {
                        for (int i = 0; i < particleAmount; i++) {
                            Vec3d particlePos = pedestalPos.lerp(vec, i / (float) particleAmount);
                            PacketByteBuf buf = PacketByteBufs.create();

                            buf.writeVec3d(particlePos);
                            buf.writeInt(ArcanistsParagonParticleDecoder.HONEY);

                            send((ServerPlayerEntity) player, buf);
                        }
                    }

                    ArcanistsParagon.LOGGER.info("Entity is (likely) spawned!");
                }


            }
        }
        return ActionResult.SUCCESS;
    }

    private int getMaxCraft(List<ItemStack> itemList) {
        int minCount = 64;
        for (ItemStack item : itemList){
            if(item.getCount() < minCount)
                minCount = item.getCount();
        }
        return minCount;
    }

    private boolean isStructureCorrect(World world, BlockPos pos) {
        return world.getBlockState(pos.north(3)).isOf(ModBlocks.RITUAL_PEDESTAL) && world.getBlockState(pos.south(3)).isOf(ModBlocks.RITUAL_PEDESTAL) &&
                world.getBlockState(pos.east(3)).isOf(ModBlocks.RITUAL_PEDESTAL) && world.getBlockState(pos.west(3)).isOf(ModBlocks.RITUAL_PEDESTAL);
    }

    private ItemStack getPossibleRecipe(List<ItemStack> itemList, BlockState blockState, World world) {
        if (!world.isClient()) {
            ArcanistsParagon.LOGGER.info("getPossibleRecipe called");
            for (RitualRecipe recipe : RECIPES) {
                if (recipe.recipeMatches(itemList, blockState)) {
                    ArcanistsParagon.LOGGER.info(recipe.getResult().toString());
                    return recipe.getResult();
                }
            }
            ArcanistsParagon.LOGGER.info("no match!");
        }
        return ItemStack.EMPTY;
    }

    private List<RitualRecipe> getRecipes() {
        return RECIPES;
    }

    private void send(ServerPlayerEntity user, PacketByteBuf buf){
        ServerPlayNetworking.send((ServerPlayerEntity) user, ArcanistsParagon.PLAY_PARTICLE_PACKET_ID, buf);
    }

}
