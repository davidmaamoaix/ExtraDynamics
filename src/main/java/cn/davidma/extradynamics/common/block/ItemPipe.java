package cn.davidma.extradynamics.common.block;

import cn.davidma.extradynamics.common.tileentity.ItemPipeTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class ItemPipe extends IPipe {

	public ItemPipe() {
		super(0.1875F, Block.Properties.create(Material.IRON));
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new ItemPipeTile();
	}
}
