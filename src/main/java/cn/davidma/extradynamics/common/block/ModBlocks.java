package cn.davidma.extradynamics.common.block;

import cn.davidma.extradynamics.common.ExtraDynamics;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {
	
	private static final DeferredRegister<Block> BLOCKS =
			new DeferredRegister<>(ForgeRegistries.BLOCKS, ExtraDynamics.MOD_ID);
	
	public static final RegistryObject<Block> ITEM_PIPE =
			BLOCKS.register("item_pipe", () -> new ItemPipe());
	
	public static void init() {
		BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
}
