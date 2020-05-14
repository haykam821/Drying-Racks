package io.github.haykam821.dryingracks.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class DryingRackBlock extends HorizontalFacingBlock implements BlockEntityProvider {
	public DryingRackBlock(Block.Settings settings) {
		super(settings);
	}

	private static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(16, 14, 16, 0, 16, 14);
	private static final VoxelShape EAST_SHAPE = Block.createCuboidShape(0, 14, 16, 2, 16, 0);
	private static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(0, 14, 0, 16, 16, 2);
	private static final VoxelShape WEST_SHAPE = Block.createCuboidShape(16, 14, 0, 14, 16, 16);

	@Override
	public BlockEntity createBlockEntity(BlockView blockView) {
		return new DryingRackBlockEntity();
	}

	@Override
	public ActionResult onUse(BlockState blockState, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hitResult) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (!(blockEntity instanceof DryingRackBlockEntity)) return ActionResult.PASS;

		ItemStack handStack = player.getStackInHand(hand);

		DryingRackBlockEntity dryingRackBlockEntity = (DryingRackBlockEntity) blockEntity;
		if ((handStack.isEmpty() || handStack.isItemEqual(dryingRackBlockEntity.inventory.get(0))) && hand == Hand.MAIN_HAND) {
			return dryingRackBlockEntity.takeRackItem(handStack, player, hand);
		} else {
			return dryingRackBlockEntity.startDrying(handStack, player, hand);
		}
	}

	@Override
	public VoxelShape getOutlineShape(BlockState blockState, BlockView blockView, BlockPos pos, EntityContext context) {
		switch (blockState.get(FACING)) {
			case NORTH:
			default:
				return NORTH_SHAPE;
			case EAST:
				return EAST_SHAPE;
			case SOUTH:
				return SOUTH_SHAPE;
			case WEST:
				return WEST_SHAPE;
		}
	}
	
	@Override
	public BlockState getPlacementState(ItemPlacementContext context) {
		return this.getDefaultState().with(FACING, context.getPlayerFacing().getOpposite());
	}

	@Override
	public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
}
