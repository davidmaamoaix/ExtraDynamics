package cn.davidma.extradynamics.common;

import cn.davidma.extradynamics.common.block.ModBlocks;
import net.minecraftforge.fml.common.Mod;

@Mod(ExtraDynamics.MOD_ID)
public class ExtraDynamics {
	
	public static final String MOD_ID = "extradynamics";
	
	private static ExtraDynamics instance;
	
	public ExtraDynamics() {
		instance = this;
		
		ModBlocks.init();
	}
	
	public static ExtraDynamics get() {
		return instance;
	}
}
