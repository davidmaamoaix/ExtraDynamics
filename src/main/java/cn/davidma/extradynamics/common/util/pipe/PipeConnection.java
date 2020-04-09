package cn.davidma.extradynamics.common.util.pipe;

public enum PipeConnection {
	
	NONE, // When the block there is a non-connectable block.
	BROKEN, // When the connection is blocked (by click of wrench).
	CONNECTED, // Connected to another pipe.
	INVENTORY; // Connected to an inventory.
	
	public boolean shouldRenderAsConnected() {
		return this == CONNECTED || this == INVENTORY;
	}
}
