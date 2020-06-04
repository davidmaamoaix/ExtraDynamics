package cn.davidma.extradynamics.common.block;

import cn.davidma.extradynamics.common.tileentity.IPipeTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SixWayBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class IPipe extends SixWayBlock {

	protected IPipe(float apothem, Properties properties) {
		super(apothem, properties);
		this.setDefaultState(this.stateContainer.getBaseState()
				.with(NORTH, false)
				.with(EAST, false)
				.with(SOUTH, false)
				.with(WEST, false)
				.with(UP, false)
				.with(DOWN, false)
		);
	}
	
	@Override
	public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
		return true;
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(DOWN, UP, NORTH, EAST, WEST, SOUTH);
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, BlockState state,
			LivingEntity placer, ItemStack stack) {
		
		if (world.isRemote()) return;
		
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof IPipeTile) {
			IPipeTile pipe = (IPipeTile) tile;
			world.setBlockState(pos, pipe.onPipePlaced(state), 2);
		}
	}
	
	@Override
	public BlockState updatePostPlacement(BlockState state, Direction facing,
			BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
		
		if (world.isRemote()) return state;
		
		TileEntity tileEntity = world.getTileEntity(currentPos);
		if (tileEntity instanceof IPipeTile) {
			IPipeTile pipe = (IPipeTile) tileEntity;
			return pipe.onBlockUpdate(state, facing, facingPos);
		}
		
		return state;
	}
}
