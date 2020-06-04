package cn.davidma.extradynamics.client;

import cn.davidma.extradynamics.common.ExtraDynamics;
import cn.davidma.extradynamics.common.block.ModBlocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = ExtraDynamics.MOD_ID, value = {Dist.CLIENT}, bus = Bus.MOD)
public class ClientEventSubscriber {
	
	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) {
		RenderTypeLookup.setRenderLayer(ModBlocks.ITEM_PIPE.get(), RenderType.getCutout());
	}
}
