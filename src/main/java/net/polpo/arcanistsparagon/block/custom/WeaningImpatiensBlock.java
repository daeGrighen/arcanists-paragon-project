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

public class WeaningImpatiensBlock extends FlowerBlock {
    public static final BooleanProperty BLOCK_DEENTROPIZABLE = BooleanProperty.of("block_deentropizable");
    public static final BooleanProperty TICK_IS_BEING_SCHEDULED = BooleanProperty.of("tick_is_being_scheduled");

    public WeaningImpatiensBlock(StatusEffect stewEffect, int duration, Settings settings) {
        super(stewEffect, duration, settings);
    }


    private boolean isServerWorld(World world) {
        return world instanceof ServerWorld;
    }

    private ServerWorld getServerWorld(World world) {
        return (ServerWorld) world;
    }

    private void updateBlockState(ServerWorld serverWorld, BlockPos pos, BlockState state, Boolean entropizable, Boolean scheduledTick) {
        serverWorld.setBlockState(pos, state.with(BLOCK_DEENTROPIZABLE, entropizable).with(TICK_IS_BEING_SCHEDULED, scheduledTick));
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        BlockState blockBelow = world.getBlockState(pos.down());
        boolean hasEntropy = !blockBelow.isIn(ModTags.Blocks.LOW_ENTROPY_BLOCKS);

        if (isServerWorld(world)) {
            ServerWorld serverWorld = getServerWorld(world);
            updateBlockState(serverWorld, pos, state, hasEntropy, false);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(BLOCK_DEENTROPIZABLE, TICK_IS_BEING_SCHEDULED);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(ModTags.Blocks.ENTROPY_STORAGE_BLOCKS);
    }

    /*@Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient()) {
            ArcanistsParagon.LOGGER.info("Weaning Impatiens right-clicked!");
            world.addParticle(ParticleTypes.HAPPY_VILLAGER, pos.getX(), pos.getY(), pos.getZ(), 0.0, 0.0, 0.0);
        }
        return ActionResult.SUCCESS;
    }*/

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        spawnParticles(world, pos, DustCTPHelper.DARK_RED, DustCTPHelper.BLACK);

        if (world.getBlockState(pos.down()).isIn(ModTags.Blocks.HIGH_ENTROPY_BLOCKS)) {
            world.scheduleBlockTick(pos, state.getBlock(), 20);
        } else {
            updateBlockState(world, pos, state, true, false);
            spawnParticles(world, pos, DustCTPHelper.GREEN, DustCTPHelper.LIME);
        }
    }

    private void spawnParticles(ServerWorld world, BlockPos pos, Vector3f color1, Vector3f color2) {
        world.spawnParticles(new DustCTPHelper(color1, color2, 1.0f), pos.getX(), pos.getY(), pos.getZ(), 10, 1.0f, 1.0f, 1.0f, 0.2f);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!world.isClient() && entity instanceof MobEntity mob) {
            handleMobCollision((ServerWorld) world, state, pos, mob);
        }
    }

    private void handleMobCollision(ServerWorld world, BlockState state, BlockPos pos, MobEntity mob) {
        BlockState blockBelow = world.getBlockState(pos.down());
        ModEntropizingHelper entropize = new ModEntropizingHelper();

        if (!blockBelow.isIn(ModTags.Blocks.LOW_ENTROPY_BLOCKS)) { // Only act if the block has entropy left
            if (mob.isBaby()) {

                // Lower the entropy of the block below
                BlockState newBaseBlock = entropize.entropy_remove(blockBelow);
                if (newBaseBlock != null) {
                    world.setBlockState(pos.down(), newBaseBlock, 4);
                }

                // Age the mob (make it not a baby)
                mob.setBaby(false);

                // Show particles and play sound
                spawnParticles(world, pos, DustCTPHelper.WHITE, DustCTPHelper.HOT_PINK);
                world.playSound(null, pos, SoundEvents.ENTITY_DOLPHIN_AMBIENT, SoundCategory.PLAYERS, 1.0f, 0.5f);

                // If the block below becomes low entropy, stop entropizing
                if (newBaseBlock.isIn(ModTags.Blocks.LOW_ENTROPY_BLOCKS)) {
                    updateBlockState(world, pos, state, false, false);
                }
            }
        }else{
            world.scheduleBlockTick(pos, state.getBlock(), 20);
        }
    }

    public boolean getTickIsBeingScheduled(BlockState state) {
        return state.get(TICK_IS_BEING_SCHEDULED);
    }
}
