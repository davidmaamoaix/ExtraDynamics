package cn.davidma.extradynamics.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SixWayBlock;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class IPipe extends SixWayBlock {

	protected IPipe(float apothem, Properties properties) {
		super(apothem, properties);
	}
	
	@Override
	public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
		return true;
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(DOWN, UP, NORTH, EAST, WEST, SOUTH);
	}
}
