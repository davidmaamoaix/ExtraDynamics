package cn.davidma.extradynamics.common.tileentity;

import java.util.Arrays;
import java.util.Map;

import com.google.common.collect.Lists;

import cn.davidma.extradynamics.common.util.pipe.PipeConnection;
import net.minecraft.block.BlockState;
import net.minecraft.block.SixWayBlock;
import net.minecraft.state.BooleanProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.MapPopulator;
import net.minecraft.util.math.BlockPos;

public abstract class ITilePipe extends TileEntity {
	
	private static final Direction[] DIRECTIONS = Direction.values();
	
	private Map<Direction, PipeConnection> connections;

	public ITilePipe(TileEntityType<?> tileEntityType) {
		super(tileEntityType);
		this.connections = MapPopulator.createMap(
				Lists.newArrayList(DIRECTIONS),
				Arrays.asList(PipeConnection.NONE, PipeConnection.NONE, PipeConnection.NONE,
						PipeConnection.NONE, PipeConnection.NONE, PipeConnection.NONE)
		);
	}
	
	/**
	 * Attemps to toggle the connection on that side (used by wrench).
	 * Will fail if that side is not a connectable block.
	 */
	public void updateConnection(Direction dir) {
		this.updateConnection(dir, true);
	}
	
	private void updateConnection(Direction dir, boolean updateOther) {
		BlockPos otherPos = this.pos.offset(dir);
		TileEntity other = this.world.getTileEntity(otherPos);
		if (other == null) return;
		if (other instanceof ITilePipe) {
			ITilePipe pipe = (ITilePipe) other;
			if (this.canConnectToPipe(pipe)) {
				PipeConnection target = this.connections.get(dir) == PipeConnection.CONNECTED ?
						PipeConnection.BROKEN : PipeConnection.CONNECTED;
				this.setConnectionAndSync(dir, target);
				pipe.setConnectionAndSync(dir.getOpposite(), target);
			}
		} else if (this.canConnectToTileEntity(other)) {
			this.setConnectionAndSync(dir, PipeConnection.INVENTORY);
		}
	}
	
	private void setConnectionAndSync(Direction dir, PipeConnection conn) {
		this.connections.put(dir, conn);
		this.markDirty();
		this.updatePipeBlockState();
		// TODO: Sync te.
	}
	
	private void updatePipeBlockState() {
		BlockState state = this.world.getBlockState(this.pos);
		for (Direction i: Direction.values()) {
			final BooleanProperty property = SixWayBlock.FACING_TO_PROPERTY_MAP.get(i);
			state = state.with(property, this.connections.get(i).shouldRenderAsConnected());
		}
		this.world.setBlockState(this.pos, state, 2);
	}
	
	/**
	 * Used to determine whether the pipe can connect to another pipe type.
	 */
	public abstract boolean canConnectToPipe(ITilePipe pipe);
	/**
	 * Used to determine whether the pipe can connect to chest, tank, etc.
	 * The given tile entity should never be a pipe.
	 */
	public abstract boolean canConnectToTileEntity(TileEntity tileEntity);
}
