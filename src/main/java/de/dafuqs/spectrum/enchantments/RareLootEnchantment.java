package de.dafuqs.spectrum.enchantments;

import de.dafuqs.spectrum.SpectrumCommon;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class RareLootEnchantment extends Enchantment {

    public RareLootEnchantment(Rarity weight, EquipmentSlot... slotTypes) {
        super(weight, EnchantmentTarget.WEAPON, slotTypes);
    }

    public int getMinPower(int level) {
        return 20;
    }

    public int getMaxPower(int level) {
        return super.getMinPower(level) + 30;
    }

    public int getMaxLevel() {
        return SpectrumCommon.CONFIG.RareLootMaxLevel;
    }

    public boolean isTreasure() {
        return false;
    }

    public boolean isAvailableForEnchantedBookOffer() {
        return true;
    }

    public boolean isAvailableForRandomSelection() {
        return false;
    }

    public boolean canAccept(Enchantment other) {
        return super.canAccept(other);
    }

}