package de.dafuqs.spectrum.registries;

import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.mixin.accessors.FluidTagsAccessor;
import net.minecraft.fluid.Fluid;
import net.minecraft.tag.Tag;

public class SpectrumFluidTags {

	public static Tag<Fluid> LIQUID_CRYSTAL;
	public static Tag<Fluid> MUD;
	public static Tag<Fluid> MIDNIGHT_SOLUTION;

	private static Tag<Fluid> register(String id) {
		return FluidTagsAccessor.invokeRegister(SpectrumCommon.MOD_ID + ":" + id);
	}

	public static void register() {
		LIQUID_CRYSTAL = register("liquid_crystal");
		MUD = register("mud");
		MIDNIGHT_SOLUTION = register("midnight_solution");
	}
}
