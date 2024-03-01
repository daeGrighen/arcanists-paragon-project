package net.polpo.arcanistsparagon.item.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.polpo.arcanistsparagon.util.ModTags;

public class DiviningRodItem extends Item {
    public DiviningRodItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if(!context.getWorld().isClient()){
            BlockPos position = context.getBlockPos();
            PlayerEntity player = context.getPlayer();
            boolean foundblock = false;

            for(int i=0; i< position.getY() + 64; i++){
                BlockState state = context.getWorld().getBlockState(position.down(i));

                if (isValuableBlock(state)){
                    player.sendMessage(Text.literal("Found " + state.getBlock().asItem().getName().getString()).withColor(0x6699ff), true);
                    foundblock=true;
                    break;
                }
            }
            if(!foundblock){
                player.sendMessage(Text.literal("Found none").withColor(0xba0019), true);
            }

        }
        context.getStack().damage(1, context.getPlayer(), playerEntity -> playerEntity.sendToolBreakStatus(playerEntity.getActiveHand()));
        return ActionResult.SUCCESS;
    }

    private boolean isValuableBlock(BlockState state) {
        return state.isIn(ModTags.Blocks.DIVINER_ROD_FINDABLE_BLOCKS);
    }
}
