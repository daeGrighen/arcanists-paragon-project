package net.polpo.arcanistsparagon.item.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;

import java.util.UUID;

public class MobReplicatorItem extends Item {

    public MobReplicatorItem(Settings settings) {
        super(settings);
    }

    /*@Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        ItemStack stack = context.getStack();
        PlayerEntity player = context.getPlayer();
        Hand hand = context.getHand();

        if (!context.getWorld().isClient()) {

            if (!player.isSneaking()) {
                // Get existing NBT or create a new one
                NbtCompound nbt = stack.getOrCreateNbt();

                // Modify the NBT
                nbt.putInt("test", 1);

                // Apply the updated NBT back to the item
                stack.setNbt(nbt);

                // Send confirmation message to the player
                player.sendMessage(Text.of("NBT should be written"), true);
            } else {
                // Read and display the "test" value from NBT
                NbtCompound nbt = stack.getNbt();
                if (nbt != null && nbt.contains("test")) {
                    player.sendMessage(Text.of(nbt.getInt("test") + ""), true);
                } else {
                    player.sendMessage(Text.of("No NBT data found"), true);
                }
            }
        }

        return ActionResult.SUCCESS;
    }*/

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity player, LivingEntity entity, Hand hand) {
        if (!player.getWorld().isClient()) {
            // Get or create NBT for the item
            NbtCompound itemNbt = stack.getOrCreateNbt();

            // Save the entity data into the NBT
            NbtCompound entityData = new NbtCompound();

            //added this
            entity.writeNbt(entityData);
            entity.saveNbt(entityData);  // Serialize the entity to NBT

            // Save entity's class name to reconstruct the exact entity later
            entityData.putString("EntityType", EntityType.getId(entity.getType()).toString());

            // Store the entity's NBT data inside the item's NBT under the "StoredEntity" key
            itemNbt.put("StoredEntity", entityData);

            // Apply the updated NBT back to the item
            stack.setNbt(itemNbt);
            player.setStackInHand(hand, stack);


            // Feedback to the player
            player.sendMessage(Text.of("Entity data stored"), true);

        }
        return ActionResult.SUCCESS;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        ItemStack stack = context.getStack();
        PlayerEntity player = context.getPlayer();

        if (!context.getWorld().isClient()) {
            // Get the NBT from the item
            NbtCompound itemNbt = stack.getNbt();

            if (itemNbt != null && itemNbt.contains("StoredEntity")) {
                // Retrieve the stored entity data
                NbtCompound entityData = itemNbt.getCompound("StoredEntity");

                // Get the entity type
                String entityTypeString = entityData.getString("EntityType");
                EntityType<?> entityType = Registries.ENTITY_TYPE.get(new Identifier(entityTypeString));

                // Spawn the entity at the block's location
                if (entityType != null) {
                    Entity clonedEntity = entityType.create(context.getWorld());
                    if (clonedEntity != null) {
                        // Deserialize the NBT back into the entity
                        clonedEntity.readNbt(entityData);

                        // Refresh the entity's position
                        clonedEntity.refreshPositionAndAngles(
                                context.getBlockPos().getX() + 0.5,
                                context.getBlockPos().getY() + 1,
                                context.getBlockPos().getZ() + 0.5,
                                player.getYaw(),
                                player.getPitch()
                        );

                        clonedEntity.setUuid(UUID.randomUUID());

                        // Add the cloned entity to the world
                        context.getWorld().spawnEntity(clonedEntity);

                        // Feedback to the player
                        player.sendMessage(Text.of("Entity cloned and spawned"), true);
                    }
                } else {
                    player.sendMessage(Text.of("Failed to spawn entity"), true);
                }
            } else {
                player.sendMessage(Text.of("No entity stored"), true);
            }
        }
        return ActionResult.SUCCESS;
    }


}
