package net.polpo.arcanistsparagon.item.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.data.client.BlockStateVariantMap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.polpo.arcanistsparagon.block.custom.EnderRelayBlock;
import net.polpo.arcanistsparagon.block.custom.EntropyCellBlock;
import net.polpo.arcanistsparagon.util.DustCTPHelper;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BindingWandItem extends Item {
    public static IntProperty POS_X = IntProperty.of("x", Integer.MIN_VALUE, Integer.MAX_VALUE);
    public static IntProperty POS_Y = IntProperty.of("y", Integer.MIN_VALUE, Integer.MAX_VALUE);
    public static IntProperty POS_Z = IntProperty.of("z", Integer.MIN_VALUE, Integer.MAX_VALUE);


    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        ItemStack stack = context.getStack();
        BlockState state = world.getBlockState(pos);

        if(!world.isClient()){
            ServerWorld serverWorld = (ServerWorld) world;
            PlayerEntity player = context.getPlayer();
            Hand hand = context.getHand();
            if(player.isSneaking()){
                savePosition(stack, pos, player, hand);
                serverWorld.spawnParticles(new DustCTPHelper(DustCTPHelper.GREEN, DustCTPHelper.SKY_BLUE, 1.0f), pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5, 20, 1.0f, 1.0f, 1.0f, 0.0f);
            } else if (world.getBlockState(pos).getBlock() instanceof EnderRelayBlock) {
                setPosition((ServerWorld) world, stack, pos, state);
            } else {
                serverWorld.spawnParticles(new DustCTPHelper(DustCTPHelper.BLACK, DustCTPHelper.CORAL, 1.0f), pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5, 20, 1.0f, 1.0f, 1.0f, 0.0f);
            }
        }

        return ActionResult.SUCCESS;
    }

    public void savePosition(ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        NbtCompound nbt = stack.getOrCreateNbt();
        nbt.putInt("posX", pos.getX());
        nbt.putInt("posY", pos.getY());
        nbt.putInt("posZ", pos.getZ());

        stack.setNbt(nbt);

        player.setStackInHand(hand, stack);
    }

    public void setPosition(ServerWorld world, ItemStack stack, BlockPos pos, BlockState state){
        NbtCompound nbt = stack.getOrCreateNbt();
        int x = nbt.getInt("posX");
        int y = nbt.getInt("posY");
        int z = nbt.getInt("posZ");

        world.setBlockState(pos, state.with(EnderRelayBlock.X, x).with(EnderRelayBlock.Y, y).with(EnderRelayBlock.Z, z).with(EnderRelayBlock.LINKED, true));
        world.spawnParticles(ParticleTypes.PORTAL, x+0.5, y+1.2, x+0.5, 20, 0.05f, 0.6f, 0.05f, 0.0f);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        NbtCompound nbt = stack.getOrCreateNbt();
        int x = nbt.getInt("posX");
        int y = nbt.getInt("posY");
        int z = nbt.getInt("posZ");
        tooltip.add(Text.literal("The wand is bound to x:" + x + " y:" + y + " z:" + z).formatted(Formatting.AQUA));
        super.appendTooltip(stack, world, tooltip, context);
    }

    public BindingWandItem(Settings settings) {
        super(settings);
    }
}
