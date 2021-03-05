package de.dafuqs.pigment.blocks.ender_dropper;

import de.dafuqs.pigment.interfaces.PlayerOwned;
import de.dafuqs.pigment.registries.PigmentBlockEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;

import java.util.UUID;

public class EnderDropperBlockEntity extends BlockEntity implements PlayerOwned {

    private UUID ownerUUID;
    private String ownerName;

    public EnderDropperBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(PigmentBlockEntityTypes.ENDER_DROPPER, blockPos, blockState);
    }

    protected Text getContainerName() {
        if(this.ownerName == null) {
            return new TranslatableText("block.pigment.ender_dropper");
        } else {
            return new TranslatableText("block.pigment.ender_dropper_with_owner", this.ownerName);
        }
    }

    public int chooseNonEmptySlot() {
        if(this.hasOwner()) {
            PlayerEntity playerEntity = world.getPlayerByUuid(this.ownerUUID);
            if (playerEntity == null) {
                return -1; // player not online => no drop
            } else {
                int i = -1;
                int j = 1;

                EnderChestInventory enderInventory = playerEntity.getEnderChestInventory();
                for (int k = 0; k < enderInventory.size(); ++k) {
                    if (!(enderInventory.getStack(k)).isEmpty() && world.random.nextInt(j++) == 0) {
                        i = k;
                    }
                }

                return i;
            }
        } else {
            return -1; // no owner
        }
    }

    public ItemStack getStack(int slot) {
        PlayerEntity playerEntity = world.getPlayerByUuid(this.ownerUUID);
        EnderChestInventory enderInventory = playerEntity.getEnderChestInventory();
        return enderInventory.getStack(slot);
    }

    public boolean canPlayerUse(PlayerEntity player) {
        if (this.world.getBlockEntity(this.pos) != this || !isOwner(player)) {
            return false;
        } else {
            return !(player.squaredDistanceTo((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) > 64.0D);
        }
    }

    public void setStack(int slot, ItemStack itemStack) {
        PlayerEntity playerEntity = world.getPlayerByUuid(this.ownerUUID);
        EnderChestInventory enderInventory = playerEntity.getEnderChestInventory();
        enderInventory.setStack(slot, itemStack);
    }

    @Override
    public UUID getOwnerUUID() {
        return this.ownerUUID;
    }

    @Override
    public String getOwnerName() {
        return this.ownerName;
    }

    @Override
    public void setOwner(PlayerEntity playerEntity) {
        this.ownerUUID = playerEntity.getUuid();
        this.ownerName = playerEntity.getName().asString();
    }

    public void readNbt(CompoundTag tag) {
        super.readNbt(tag);

        if(tag.contains("OwnerUUID")) {
            this.ownerUUID = tag.getUuid("OwnerUUID");
        } else {
            this.ownerUUID = null;
        }
        if(tag.contains("OwnerName")) {
            this.ownerName = tag.getString("OwnerName");
        } else {
            this.ownerName = null;
        }
    }

    public CompoundTag writeNbt(CompoundTag tag) {
        super.writeNbt(tag);

        if(this.ownerUUID != null) {
            tag.putUuid("OwnerUUID", this.ownerUUID);
        }
        if(this.ownerName != null) {
            tag.putString("OwnerName", this.ownerName);
        }

        return tag;
    }

}