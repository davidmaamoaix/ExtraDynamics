package cn.davidma.extradynamics.common.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.CapabilityItemHandler;

public class ItemPipeTile extends IPipeTile {

	public ItemPipeTile() {
		super(ModTileEntities.ITEM_PIPE.get());
	}

	@Override
	public boolean canConnectToPipe(IPipeTile pipe) {
		return pipe instanceof ItemPipeTile;
	}

	@Override
	public boolean canConnectToTileEntity(TileEntity tileEntity) {
		return tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
				.isPresent();
	}

}
