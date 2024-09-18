package net.polpo.arcanistsparagon.block.custom;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.polpo.arcanistsparagon.ArcanistsParagon;
import net.polpo.arcanistsparagon.util.ModTags;

public class ScionbloomBlossomBlock extends FlowerBlock {
    public static final int MAX_CHARGES = 8;
    public static final IntProperty CHARGES = IntProperty.of("charges", 0, MAX_CHARGES);

    public ScionbloomBlossomBlock(StatusEffect stewEffect, int duration, Settings settings) {
        super(stewEffect, duration, settings);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CHARGES);
    }

    public int getMaxCharges() {
        return MAX_CHARGES;
    }

    public BlockState withCharges(int charges) {
        return (BlockState)this.getDefaultState().with(this.getChargesProperty(), charges);
    }

    public void setCharges(int charges, BlockPos pos, ServerWorld world){
        world.setBlockState(pos, this.withCharges(charges), 1);
    }

    public IntProperty getChargesProperty() {
        return CHARGES;
    }
    public int getCharges(BlockState state) {
        return (Integer)state.get(this.getChargesProperty());
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(ModTags.Blocks.LOW_ENTROPY_BLOCKS);
    }

    /*@Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient()) {
            ArcanistsParagon.LOGGER.info("Scionbloom right-clicked! charges=" + this.getCharges(state));
        }
        return ActionResult.SUCCESS;
    }*/

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if(!world.isClient()){
            if(entity instanceof MobEntity){


                MobEntity mob = (MobEntity) entity;
                if(!mob.isBaby()){
                    //ArcanistsParagon.LOGGER.info("the mob is not a baby");
                    if(this.getCharges(state) != 8){
                        this.setCharges(getCharges(state) + 1, pos, (ServerWorld) world);
                        //ArcanistsParagon.LOGGER.info("charge added");
                    }
                    else{
                        world.addBlockBreakParticles(pos, state);

                        world.setBlockState(pos, Blocks.AIR.getDefaultState());
                    }
                    mob.setBaby(true);
                    //ArcanistsParagon.LOGGER.info(this.getCharges(state) + " charges in the flower, entity has been de-aged");
                }


            }
        }
    }
}
