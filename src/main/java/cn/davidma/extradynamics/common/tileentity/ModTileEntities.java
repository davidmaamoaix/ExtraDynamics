package cn.davidma.extradynamics.common.tileentity;

import cn.davidma.extradynamics.common.ExtraDynamics;
import cn.davidma.extradynamics.common.block.ModBlocks;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntities {
	
	private static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES =
			new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, ExtraDynamics.MOD_ID);
	
	public static final RegistryObject<TileEntityType<?>> ITEM_PIPE =
			TILE_ENTITIES.register("item_pipe", () -> TileEntityType.Builder
					.create(ItemPipeTile::new, ModBlocks.ITEM_PIPE.get()).build(null));
	
	public static void init() {
		TILE_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
}
