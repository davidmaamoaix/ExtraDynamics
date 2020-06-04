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

public abstract class IPipeTile extends TileEntity {
	
	private static final Direction[] DIRECTIONS = Direction.values();
	
	private Map<Direction, PipeConnection> connections;

	public IPipeTile(TileEntityType<?> tileEntityType) {
		super(tileEntityType);
		this.connections = MapPopulator.createMap(
				Lists.newArrayList(DIRECTIONS),
				Arrays.asList(PipeConnection.NONE, PipeConnection.NONE, PipeConnection.NONE,
						PipeConnection.NONE, PipeConnection.NONE, PipeConnection.NONE)
		);
	}
	
	public BlockState onPipePlaced(BlockState state) {
		for (Direction i: DIRECTIONS) {
			BlockPos otherPos = this.getPos().offset(i);
			TileEntity other = this.world.getTileEntity(otherPos);
			if (other == null) continue;
			
			if (other instanceof IPipeTile) {
				IPipeTile pipe = (IPipeTile) other;
				if (this.canConnectToPipe(pipe)) {
					this.connections.put(i, PipeConnection.CONNECTED);
					this.markDirty();
					this.world.setBlockState(otherPos, pipe.setConnection(i.getOpposite(),
							PipeConnection.CONNECTED), 2);
				}
			} else if (this.canConnectToTileEntity(other)) {
				this.connections.put(i, PipeConnection.INVENTORY);
				this.markDirty();
			}
		}
		
		return this.getUpdatePipeBlockState(state);
	}
	
	/**
	 * Attempt to connect to the block there.
	 * Will ignore pipes, as pipe connection is handled on pipe placement.
	 */
	public BlockState onBlockUpdate(BlockState state, Direction direction, BlockPos target) {
		TileEntity tileEntity = this.world.getTileEntity(target);
		
		if (tileEntity == null) {
			return this.setConnection(direction, PipeConnection.NONE);
		}
		
		if (this.canConnectToTileEntity(tileEntity)) {
			return this.setConnection(direction, PipeConnection.INVENTORY);
		}
		
		return state;
	}
	
	/**
	 * Attemps to toggle the connection on that side (used by wrench).
	 * Will fail if that side is not a connectable block.
	 */
	public BlockState updateConnection(Direction dir) {
		BlockPos otherPos = this.pos.offset(dir);
		TileEntity other = this.world.getTileEntity(otherPos);
		if (other == null) return this.getBlockState();
		if (other instanceof IPipeTile) {
			IPipeTile pipe = (IPipeTile) other;
			if (this.canConnectToPipe(pipe)) {
				PipeConnection target = this.connections.get(dir) == PipeConnection.CONNECTED ?
						PipeConnection.BROKEN : PipeConnection.CONNECTED;
				
				this.world.setBlockState(otherPos,
						pipe.setConnection(dir.getOpposite(), target), 2);
				return this.setConnection(dir, target);
			}
		} else if (this.canConnectToTileEntity(other)) {
			PipeConnection target = this.connections.get(dir) == PipeConnection.INVENTORY ?
					PipeConnection.BROKEN : PipeConnection.INVENTORY;
			return this.setConnection(dir, target);
		}
		
		return this.getBlockState();
	}
	
	private BlockState setConnection(Direction dir, PipeConnection conn) {
		this.connections.put(dir, conn);
		this.markDirty();
		return this.getUpdatePipeBlockState();
	}
	
	private BlockState getUpdatePipeBlockState() {
		return this.getUpdatePipeBlockState(this.getBlockState());
	}
	
	private BlockState getUpdatePipeBlockState(BlockState state) {
		BlockState oldState = state;
		BlockState newState = oldState;
		for (Direction i: Direction.values()) {
			final BooleanProperty property = SixWayBlock.FACING_TO_PROPERTY_MAP.get(i);
			newState = newState.with(property,
					this.connections.get(i).shouldRenderAsConnected());
		}
		//this.world.notifyBlockUpdate(this.pos, oldState, newState, 2);
		
		return newState;
	}
	
	/**
	 * Used to determine whether the pipe can connect to another pipe type.
	 */
	public abstract boolean canConnectToPipe(IPipeTile pipe);
	/**
	 * Used to determine whether the pipe can connect to chest, tank, etc.
	 * The given tile entity should NEVER be a pipe (use canConnectToPipe).
	 */
	public abstract boolean canConnectToTileEntity(TileEntity tileEntity);
}
