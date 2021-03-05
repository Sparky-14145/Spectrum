package de.dafuqs.pigment.mixin;

import de.dafuqs.pigment.PigmentCommon;
import de.dafuqs.pigment.enchantments.AutoSmeltEnchantment;
import de.dafuqs.pigment.enchantments.PigmentEnchantments;
import de.dafuqs.pigment.inventories.InventoryHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mixin(Block.class)
public abstract class BlockMixin {

    @Inject(method = "Lnet/minecraft/block/Block;getDroppedStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)Ljava/util/List;", at = @At("RETURN"), cancellable = true)
    private static void getDroppedStacks(BlockState state, ServerWorld world, BlockPos pos, BlockEntity blockEntity, Entity entity, ItemStack stack, CallbackInfoReturnable<List<ItemStack>> cir) {
        List<ItemStack> returnStacks = cir.getReturnValue();
        if(returnStacks.size() > 0) {
            Map<Enchantment, Integer> enchantmentMap = EnchantmentHelper.get(stack);

            // Voiding curse: no drops
            if (enchantmentMap.containsKey(PigmentEnchantments.VOIDING)) {
                world.spawnParticles(ParticleTypes.SMOKE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 10, 0.5, 0.5, 0.5,0.05);
                cir.setReturnValue(new ArrayList<>());
            } else {
                // Autosmelt enchant: try smelting recipe for each stack
                if (enchantmentMap.containsKey(PigmentEnchantments.AUTO_SMELT)) {
                    returnStacks = AutoSmeltEnchantment.applyAutoSmelt(world, returnStacks);
                }
                // Inventory Insertion enchant? Add it to players inventory if there is room
                if (enchantmentMap.containsKey(PigmentEnchantments.INVENTORY_INSERTION)) {
                    List<ItemStack> leftoverReturnStacks = new ArrayList<>();

                    if(entity instanceof PlayerEntity) {
                        PlayerEntity playerEntity = (PlayerEntity) entity;

                        for(ItemStack itemStack : returnStacks) {
                            Item item = itemStack.getItem();
                            int count = itemStack.getCount();

                            if (playerEntity.getInventory().insertStack(itemStack)) {
                                if (itemStack.isEmpty()) {
                                    itemStack.setCount(count);
                                }
                                playerEntity.increaseStat(Stats.PICKED_UP.getOrCreateStat(item), count);
                            } else {
                                leftoverReturnStacks.add(itemStack);
                            }
                        }
                    }

                    returnStacks = leftoverReturnStacks;
                }
                cir.setReturnValue(returnStacks);
            }
        }
    }



}