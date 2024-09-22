package net.polpo.arcanistsparagon.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.polpo.arcanistsparagon.ArcanistsParagon;
import net.polpo.arcanistsparagon.util.DustCTPHelper;
import net.polpo.arcanistsparagon.util.ModEntropizingHelper;
import net.polpo.arcanistsparagon.util.ModTags;
import org.joml.Vector3f;

public class ScionbloomBlock extends FlowerBlock {
    public static final int MAX_CHARGES = 2;
    public static final IntProperty CHARGES = IntProperty.of("charges", 0, MAX_CHARGES);
    public static final BooleanProperty BLOCK_ENTROPIZABLE = BooleanProperty.of("block_entropizable");
    public static final BooleanProperty TICK_IS_BEING_SCHEDULED = BooleanProperty.of("tick_is_being_scheduled");

    public ScionbloomBlock(StatusEffect stewEffect, int duration, Settings settings) {
        super(stewEffect, duration, settings);
    }

    // Utility methods to prevent duplicate code
    private boolean isServerWorld(World world) {
        return world instanceof ServerWorld;
    }

    private ServerWorld getServerWorld(World world) {
        return (ServerWorld) world;
    }

    private void updateBlockState(ServerWorld serverWorld, BlockPos pos, BlockState state, Boolean entropizable, Boolean scheduledTick) {
        serverWorld.setBlockState(pos, state.with(BLOCK_ENTROPIZABLE, entropizable).with(TICK_IS_BEING_SCHEDULED, scheduledTick));
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        boolean isHighEntropy = world.getBlockState(pos.down()).isIn(ModTags.Blocks.HIGH_ENTROPY_BLOCKS);
        if (isServerWorld(world)) {
            ServerWorld serverWorld = getServerWorld(world);
            updateBlockState(serverWorld, pos, state, !isHighEntropy, false);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CHARGES, BLOCK_ENTROPIZABLE, TICK_IS_BEING_SCHEDULED);
    }

    public int getMaxCharges() {
        return MAX_CHARGES;
    }

    public BlockState withCharges(int charges) {
        return this.getDefaultState().with(CHARGES, charges);
    }

    public void setCharges(int charges, BlockPos pos, ServerWorld world) {
        world.setBlockState(pos, this.withCharges(charges), 1);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(ModTags.Blocks.ENTROPY_STORAGE_BLOCKS);
    }

    /*@Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient()) {
            ArcanistsParagon.LOGGER.info("Scionbloom right-clicked! charges=" + getCharges(state));
            world.addParticle(ParticleTypes.ANGRY_VILLAGER, pos.getX(), pos.getY(), pos.getZ(), 0.0, 0.0, 0.0);
        }
        return ActionResult.SUCCESS;
    }*/

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        spawnParticles(world, pos, DustCTPHelper.DARK_RED, DustCTPHelper.BLACK, 20);

        if (world.getBlockState(pos.down()).isIn(ModTags.Blocks.HIGH_ENTROPY_BLOCKS)) {
            world.scheduleBlockTick(pos, state.getBlock(), 20);
        } else {
            updateBlockState(world, pos, state, true, false);
            spawnParticles(world, pos, DustCTPHelper.GREEN, DustCTPHelper.LIME, 15);
        }
    }

    private void spawnParticles(ServerWorld world, BlockPos pos, Vector3f color1, Vector3f color2, int count) {
        world.spawnParticles(new DustCTPHelper(color1, color2, 1.0f), pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5, count, 0.6f, 0.6f, 0.6f, 0.2f);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!world.isClient() && entity instanceof MobEntity mob) {
            handleMobCollision((ServerWorld) world, state, pos, mob);
        }
    }

    private void handleMobCollision(ServerWorld world, BlockState state, BlockPos pos, MobEntity mob) {
        BlockState baseBlock = world.getBlockState(pos.down());
        ModEntropizingHelper entropize = new ModEntropizingHelper();

        if (!mob.isBaby()) {
            if (baseBlock.isIn(ModTags.Blocks.HIGH_ENTROPY_BLOCKS)) {
                if (!getTickIsBeingScheduled(state)) {
                    updateBlockState(world, pos, state, false, true);
                    world.scheduleBlockTick(pos, state.getBlock(), 15);
                }
                return;
            }

            if (getCharges(state) == MAX_CHARGES) {
                setCharges(0, pos, world);
                BlockState newBaseBlock = entropize.entropy_add(baseBlock);
                if (newBaseBlock != null) {
                    world.setBlockState(pos.down(), newBaseBlock, 4);
                }
            } else {
                setCharges(getCharges(state) + 1, pos, world);
            }

            spawnParticles(world, pos, DustCTPHelper.LAVENDER, DustCTPHelper.GOLDENROD, 20);
            world.playSound(null, pos, SoundEvents.ENTITY_DOLPHIN_AMBIENT_WATER, SoundCategory.PLAYERS, 1.0f, 0.3f);
            mob.setBaby(true);
        }
    }

    public boolean getTickIsBeingScheduled(BlockState state) {
        return state.get(TICK_IS_BEING_SCHEDULED);
    }

    public int getCharges(BlockState state) {
        return state.get(CHARGES);
    }
}
