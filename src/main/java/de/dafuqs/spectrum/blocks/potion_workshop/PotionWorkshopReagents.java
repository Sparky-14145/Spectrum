package de.dafuqs.spectrum.blocks.potion_workshop;

import de.dafuqs.spectrum.registries.SpectrumBlocks;
import de.dafuqs.spectrum.registries.SpectrumItems;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.HashMap;
import java.util.Random;

public class PotionWorkshopReagents {
	
	public static final HashMap<Item, PotionReagentEffect> reagentEffects = new HashMap<>();
	
	public abstract static class PotionReagentEffect {
		public abstract PotionMod modify(PotionMod potionMod, Random random);
	}
	
	public static boolean isReagent(Item item) {
		return reagentEffects.containsKey(item);
	}
	
	public static PotionMod modify(Item item, PotionMod potionMod, Random random) {
		if(reagentEffects.containsKey(item)) {
			return reagentEffects.get(item).modify(potionMod, random);
		}
		return potionMod;
	}
	
	public PotionWorkshopReagents() {
		reagentEffects.put(Items.REDSTONE, new PotionReagentEffect() {
			public PotionMod modify(PotionMod potionMod, Random random) {
				potionMod.multiplicativeDurationBonus *= 1.0F;
				return potionMod;
			}
		});
		reagentEffects.put(Items.GLOWSTONE_DUST, new PotionReagentEffect() {
			public PotionMod modify(PotionMod potionMod, Random random) {
				potionMod.flatPotencyBonus += 1;
				return potionMod;
			}
		});
		reagentEffects.put(Items.GUNPOWDER, new PotionReagentEffect() {
			public PotionMod modify(PotionMod potionMod, Random random) {
				potionMod.makeSplashing = true;
				return potionMod;
			}
		});
		reagentEffects.put(Items.DRAGON_BREATH, new PotionReagentEffect() {
			public PotionMod modify(PotionMod potionMod, Random random) {
				potionMod.makeLingering = true;
				return potionMod;
			}
		});
		reagentEffects.put(SpectrumItems.TOPAZ_POWDER, new PotionReagentEffect() {
			public PotionMod modify(PotionMod potionMod, Random random) {
				potionMod.noParticles = true;
				return potionMod;
			}
		});
		reagentEffects.put(SpectrumItems.AMETHYST_POWDER, new PotionReagentEffect() {
			public PotionMod modify(PotionMod potionMod, Random random) {
				potionMod.flatDurationBonusTicks += 600;
				return potionMod;
			}
		});
		reagentEffects.put(SpectrumItems.CITRINE_POWDER, new PotionReagentEffect() {
			public PotionMod modify(PotionMod potionMod, Random random) {
				potionMod.flatPotencyBonus += 0.25F;
				return potionMod;
			}
		});
		reagentEffects.put(SpectrumItems.ONYX_POWDER, new PotionReagentEffect() {
			public PotionMod modify(PotionMod potionMod, Random random) {
				potionMod.unidentifiable = true;
				return potionMod;
			}
		});
		reagentEffects.put(SpectrumItems.MOONSTONE_POWDER, new PotionReagentEffect() {
			public PotionMod modify(PotionMod potionMod, Random random) {
				potionMod.makeEffectsPositive = true;
				return potionMod;
			}
		});
		reagentEffects.put(SpectrumItems.SPARKLESTONE_GEM, new PotionReagentEffect() {
			public PotionMod modify(PotionMod potionMod, Random random) {
				potionMod.multiplicativeDurationBonus *= 0.1F;
				potionMod.multiplicativePotencyBonus /= 0.1F;
				return potionMod;
			}
		});
		reagentEffects.put(SpectrumItems.QUITOXIC_POWDER, new PotionReagentEffect() {
			public PotionMod modify(PotionMod potionMod, Random random) {
				potionMod.flatPotencyBonusNegativeEffects += 1.0F;
				return potionMod;
			}
		});
		reagentEffects.put(SpectrumItems.SCARLET_FRAGMENTS, new PotionReagentEffect() {
			public PotionMod modify(PotionMod potionMod, Random random) {
				potionMod.flatPotencyBonus += 1.0F;
				potionMod.additionalRandomNegativeEffectCount += 1.0F;
				return potionMod;
			}
		});
		reagentEffects.put(SpectrumItems.PALETUR_FRAGMENTS, new PotionReagentEffect() {
			public PotionMod modify(PotionMod potionMod, Random random) {
				potionMod.additionalRandomPositiveEffectCount += 1.0F;
				return potionMod;
			}
		});
		reagentEffects.put(SpectrumItems.STARDUST, new PotionReagentEffect() {
			public PotionMod modify(PotionMod potionMod, Random random) {
				potionMod.chanceToAddLastEffect += 0.33F;
				return potionMod;
			}
		});
		reagentEffects.put(SpectrumItems.SHOOTING_STAR, new PotionReagentEffect() {
			public PotionMod modify(PotionMod potionMod, Random random) {
				potionMod.chanceToAddLastEffect += 1.0F;
				potionMod.lastEffectPotencyMod = 0.75F;
				return potionMod;
			}
		});
		reagentEffects.put(SpectrumItems.LIGHTNING_STONE, new PotionReagentEffect() {
			public PotionMod modify(PotionMod potionMod, Random random) {
				potionMod.potentDecreasingEffect = true;
				return potionMod;
			}
		});
		reagentEffects.put(SpectrumItems.RAW_AZURITE, new PotionReagentEffect() {
			public PotionMod modify(PotionMod potionMod, Random random) {
				potionMod.negateDecreasingDuration = true;
				return potionMod;
			}
		});
		reagentEffects.put(SpectrumItems.VEGETAL, new PotionReagentEffect() {
			public PotionMod modify(PotionMod potionMod, Random random) {
				potionMod.multiplicativeDurationBonus *= 2.0F;
				potionMod.multiplicativePotencyBonus /= 0.5F;
				return potionMod;
			}
		});
		reagentEffects.put(SpectrumItems.NEOLITH, new PotionReagentEffect() {
			public PotionMod modify(PotionMod potionMod, Random random) {
				potionMod.flatYieldBonus += 1.0F;
				return potionMod;
			}
		});
		reagentEffects.put(SpectrumItems.BEDROCK_DUST, new PotionReagentEffect() {
			public PotionMod modify(PotionMod potionMod, Random random) {
				potionMod.multiplicativeDurationBonus *= 2.0F;
				potionMod.multiplicativePotencyBonus /= 0.25F;
				return potionMod;
			}
		});
		reagentEffects.put(SpectrumBlocks.FOUR_LEAF_CLOVER.asItem(), new PotionReagentEffect() {
			public PotionMod modify(PotionMod potionMod, Random random) {
				int rand = random.nextInt(5);
				switch (rand) {
					case 0: {
						potionMod.flatDurationBonusTicks += 2400F;
					}
					case 1: {
						potionMod.flatPotencyBonus *= 2.0F;
					}
					case 2: {
						potionMod.multiplicativeDurationBonus *= 2.0F;
					}
					case 3: {
						potionMod.multiplicativePotencyBonus /= 0.25F;
					}
					case 4: {
						potionMod.flatYieldBonus += 2.0F;
					}
				}
				return potionMod;
			}
		});
	}
	
}
