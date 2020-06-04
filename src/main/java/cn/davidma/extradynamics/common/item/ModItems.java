package cn.davidma.extradynamics.common.item;

import cn.davidma.extradynamics.common.ExtraDynamics;
import cn.davidma.extradynamics.common.block.ModBlocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
	
	private static final ItemGroup GROUP = new ItemGroup(ExtraDynamics.MOD_ID) {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(ITEM_PIPE.get());
		}
	};
	
	private static final DeferredRegister<Item> ITEMS =
			new DeferredRegister<>(ForgeRegistries.ITEMS, ExtraDynamics.MOD_ID);
	
	public static final RegistryObject<Item> ITEM_PIPE = ITEMS.register("item_pipe",
			() -> new BlockItem(ModBlocks.ITEM_PIPE.get(), new Item.Properties().group(GROUP)));
	
	public static void init() {
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
}
