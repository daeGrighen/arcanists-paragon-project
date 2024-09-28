package net.polpo.arcanistsparagon.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.polpo.arcanistsparagon.ArcanistsParagon;
import net.polpo.arcanistsparagon.block.ModBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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


    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        int charges = 0;
        NbtCompound nbtData = stack.getNbt();
        if (nbtData != null) {
            charges = Integer.parseInt(nbtData.getCompound("BlockStateTag").getString("charges"));

        }
        tooltip.add(Text.literal("The storage contains " + charges + " charges").formatted(Formatting.AQUA));
        super.appendTooltip(stack, world, tooltip, options);
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {
        List<ItemStack> drops = super.getDroppedStacks(state, builder);

        for (ItemStack stack : drops) {
            if (stack.getItem() == ModBlocks.ENTROPY_CELL.asItem()) {
                int charges = state.get(EntropyCellBlock.CHARGES);
                stack.getOrCreateNbt().putInt("CustomModelData", charges);
            }
        }

        return drops;
    }
}
