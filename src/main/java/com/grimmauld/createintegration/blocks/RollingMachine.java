package com.grimmauld.createintegration.blocks;


import javax.annotation.Nullable;

import com.simibubi.create.foundation.block.ITE;
import com.simibubi.create.modules.contraptions.base.DirectionalAxisKineticBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class RollingMachine extends DirectionalAxisKineticBlock implements ITE<RollingMachineTile>{
	
	public static final BooleanProperty RUNNING = BooleanProperty.create("running");
	
	// done
	public RollingMachine() {
		super(Properties.from(Blocks.ANDESITE));
		setRegistryName("rolling_machine");  // TODO
		setDefaultState(getDefaultState().with(RUNNING, false));
	}
	
	//done
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	// has to be implemented here
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new RollingMachineTile();
	}
	
	// done
	@Override
	protected boolean hasStaticPart() {
		return true;
	}
	

	
/*	@Override
	public void onLanded(IBlockReader worldIn, Entity entityIn) {
		super.onLanded(worldIn, entityIn);
		if (!(entityIn instanceof ItemEntity)) {
			return;
		}
		if (entityIn.world.isRemote) {
			return;
		}

		BlockPos pos = entityIn.getPosition();
		withTileEntityDo(entityIn.world, pos, te -> {
			if (te.getSpeed() == 0) {
				return;
			}
			te.insertItem((ItemEntity) entityIn);
		});
	}*/



	// done
	@Override
	public PushReaction getPushReaction(BlockState state) {
		return PushReaction.NORMAL;
	}

	// done
	public static boolean isHorizontal(BlockState state) {
		return true; // state.get(BlockStateProperties.FACING).getAxis().isHorizontal();
	}

	
	// done
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(RUNNING);
		super.fillStateContainer(builder);
	}

	
	// TODO
	@Override
	public Class<RollingMachineTile> getTileEntityClass() {
		return RollingMachineTile.class;
	}
	
	
	// done
  	@Override
	public boolean hasShaftTowards(IWorldReader world, BlockPos pos, BlockState state, Direction face) {
		return face == state.get(BlockStateProperties.FACING) || face == state.get(BlockStateProperties.FACING).getOpposite();
	}
  
  	// Done
	@Override
	public Axis getRotationAxis(BlockState state) {
		return state.get(BlockStateProperties.FACING).getAxis();
	}
	
	// done
    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
        if (entity != null) {
            world.setBlockState(pos, state.with(BlockStateProperties.FACING, getFacingFromEntity(pos, entity)), 2);
        }
    }
	
    
    // done
	private static Direction getFacingFromEntity(BlockPos clickedBlock, LivingEntity entity) {
        Vec3d vec = entity.getPositionVec();
        return Direction.getFacingFromVector((float) (entity.isSneaking()?-1:1)*(vec.x - clickedBlock.getX()), .0f, (float) (entity.isSneaking()?-1:1)*(vec.z - clickedBlock.getZ()));
    }
}