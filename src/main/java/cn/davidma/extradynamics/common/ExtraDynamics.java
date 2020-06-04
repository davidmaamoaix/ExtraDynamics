package cn.davidma.extradynamics.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.davidma.extradynamics.common.block.ModBlocks;
import cn.davidma.extradynamics.common.item.ModItems;
import cn.davidma.extradynamics.common.tileentity.ModTileEntities;
import net.minecraftforge.fml.common.Mod;

@Mod(ExtraDynamics.MOD_ID)
public class ExtraDynamics {
	
	public static final String MOD_ID = "extradynamics";
	
	public static Logger LOGGER = LogManager.getLogger();
	
	private static ExtraDynamics instance;
	
	public ExtraDynamics() {
		instance = this;
		
		ModBlocks.init();
		ModTileEntities.init();
		ModItems.init();
	}
	
	public static ExtraDynamics get() {
		return instance;
	}
}
