package ladysnake.dissolution.common.init;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import com.google.common.base.Preconditions;

import ladysnake.dissolution.common.Dissolution;
import ladysnake.dissolution.common.Reference;
import ladysnake.dissolution.common.blocks.BlockFluidMercury;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModFluids {

	public static final Set<Fluid> FLUIDS = new HashSet<>();

	public static final Set<IFluidBlock> MOD_FLUID_BLOCKS = new HashSet<>();


	public static final Fluid NORMAL = createFluid("mercury", true,
			fluid -> fluid.setLuminosity(10).setDensity(1600).setViscosity(1000),
			fluid -> new BlockFluidMercury(fluid, new MaterialLiquid(MapColor.LIGHT_BLUE)));

	private static <T extends Block & IFluidBlock> Fluid createFluid(final String name, final boolean hasFlowIcon, final Consumer<Fluid> fluidPropertyApplier, final Function<Fluid, T> blockFactory) {
		final String texturePrefix = Reference.MOD_ID + ":" + "blocks/fluid_";

		final ResourceLocation still = new ResourceLocation(texturePrefix + name + "_still");
		final ResourceLocation flowing = hasFlowIcon ? new ResourceLocation(texturePrefix + name + "_flow") : still;

		Fluid fluid = new Fluid(name, still, flowing);
		final boolean useOwnFluid = FluidRegistry.registerFluid(fluid);

		if (useOwnFluid) {
			fluidPropertyApplier.accept(fluid);
			MOD_FLUID_BLOCKS.add(blockFactory.apply(fluid));
		} else {
			fluid = FluidRegistry.getFluid(name);
		}

		FLUIDS.add(fluid);

		return fluid;
	}

	public static class RegistrationHandler {

		public static void registerBlocks() {
			
			for (final IFluidBlock fluidBlock : MOD_FLUID_BLOCKS) {
				final Block block = (Block) fluidBlock;
				block.setRegistryName(Reference.MOD_ID, "fluid." + fluidBlock.getFluid().getName());
				block.setUnlocalizedName(Reference.MOD_ID + ":" + fluidBlock.getFluid().getUnlocalizedName());
				block.setCreativeTab(Dissolution.CREATIVE_TAB);
				GameRegistry.register(block);
			}
		}

		public static void registerItems() {
						
			for (final IFluidBlock fluidBlock : MOD_FLUID_BLOCKS) {
				final Block block = (Block) fluidBlock;
				final ItemBlock itemBlock = new ItemBlock(block);
				final ResourceLocation registryName = Preconditions.checkNotNull(block.getRegistryName());
				itemBlock.setRegistryName(registryName);
				GameRegistry.register(itemBlock);
			}

			registerFluidContainers();
		}
	}

	private static void registerFluidContainers() {

		for (final Fluid fluid : FLUIDS) {
			registerBucket(fluid);
		}
	}

	private static void registerBucket(final Fluid fluid) {
		FluidRegistry.addBucketForFluid(fluid);
	}


}
