package net.polpo.arcanistsparagon.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class EnderRelayBlock extends EntropyCellBlock{
    public static final int MAX_CHARGES = 16;
    public static final IntProperty CHARGES = IntProperty.of("charges", 0, MAX_CHARGES);
    public static final IntProperty X = IntProperty.of("posX", Integer.MIN_VALUE, Integer.MAX_VALUE);
    public static final IntProperty Y = IntProperty.of("posY", Integer.MIN_VALUE, Integer.MAX_VALUE);
    public static final IntProperty Z = IntProperty.of("posZ", Integer.MIN_VALUE, Integer.MAX_VALUE);
    public static final BooleanProperty LINKED = BooleanProperty.of("linked");


    public EnderRelayBlock(Settings settings) {
        super(settings);
    }

    @Override
    public int getCharges(BlockState state) {
        return state.get(EntropyCellBlock.CHARGES).intValue();
    }

    @Override
    public int getMaxCharges() {
        return MAX_CHARGES;
    }

    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!world.isClient()) {
            ServerWorld serverWorld = (ServerWorld) world;
            serverWorld.setBlockState(pos, state.with(LINKED, false));
        }
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

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if(!world.isClient()){
            if(state.get(EnderRelayBlock.LINKED).booleanValue()) {

                int x = state.get(X).intValue();
                int y = state.get(Y).intValue();
                int z = state.get(Z).intValue();
                BlockPos linkedPos = new BlockPos(x, y, z);
                BlockState linkedState = world.getBlockState(linkedPos);
                if(linkedState.getBlock() instanceof EntropyCellBlock) {

                    ServerWorld serverWorld = (ServerWorld) world;
                    int linkedCharges = linkedState.get(EntropyCellBlock.CHARGES).intValue();
                    int cellCharges = state.get(EntropyCellBlock.CHARGES).intValue();
                    int maxChargesLinked = ((EntropyCellBlock) state.getBlock()).getMaxCharges();
                    int maxChargesCell = ((EntropyCellBlock) state.getBlock()).getMaxCharges();

                    int totalCharges = linkedCharges + cellCharges;
                    int finalChargesLinked, finalChargesCell;

                    if ((totalCharges / 2) % 2 == 0) {
                        finalChargesCell = totalCharges / 2;
                        finalChargesLinked = finalChargesCell;
                    } else {
                        finalChargesLinked = (totalCharges - 1) / 2;
                        finalChargesCell = finalChargesLinked + 1;
                    }

                    if (finalChargesCell > maxChargesCell) {
                        int difference = finalChargesCell - maxChargesCell;
                        finalChargesCell -= difference;
                        finalChargesLinked += difference;
                    }

                    if (finalChargesLinked >= maxChargesLinked) {
                        int difference = finalChargesLinked - maxChargesLinked;
                        finalChargesLinked -= difference;
                        finalChargesCell += difference;
                    }

                    //set the final charges
                    serverWorld.setBlockState(linkedPos, linkedState.with(EntropyCellBlock.CHARGES, finalChargesLinked));
                    serverWorld.setBlockState(pos, state.with(EntropyCellBlock.CHARGES, finalChargesCell));
                }
            }
        }
    }



    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CHARGES, X, Y, Z, LINKED);
    }
}
