package de.dafuqs.pigment.blocks.chromatic_tree;

import de.dafuqs.pigment.interfaces.PlayerOwned;
import de.dafuqs.pigment.PigmentBlockEntityType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;

import java.util.UUID;

public class OminousSaplingBlockEntity extends BlockEntity implements PlayerOwned {

    public UUID ownerUUID;

    public OminousSaplingBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(PigmentBlockEntityType.OMINOUS_SAPLING_BLOCK_ENTITY_TYPE, blockPos, blockState);
    }

    public OminousSaplingBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    @Override
    public void setPlayerUUID(UUID playerUUID) {
        this.ownerUUID = playerUUID;
    }

    @Override
    public UUID getPlayerUUID() {
        return this.ownerUUID;
    }

    // Serialize the BlockEntity
    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);

        // Save the current value of the number to the tag
        tag.putUuid ("uuid", this.ownerUUID);

        return tag;
    }

    // Deserialize the BlockEntity
    @Override
    public void fromTag(CompoundTag tag) {
        super.fromTag(tag);
        this.ownerUUID = tag.getUuid("uuid");
    }

}