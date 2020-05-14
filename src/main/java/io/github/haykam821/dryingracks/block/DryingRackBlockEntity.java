package io.github.haykam821.dryingracks.block;

import io.github.haykam821.dryingracks.Main;
import io.github.haykam821.dryingracks.recipe.DryingRecipe;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.BasicInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Hand;
import net.minecraft.util.Tickable;

public class DryingRackBlockEntity extends BlockEntity implements Tickable, BlockEntityClientSerializable {
	protected DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
	public int dryTime = 0;

	public DryingRackBlockEntity() {
		super(Main.DRYING_RACK_BLOCK_ENTITY);
	}

	// Saving
	@Override
	public void fromTag(CompoundTag compoundTag) {
		super.fromTag(compoundTag);

		this.dryTime = compoundTag.getInt("DryTime");
		Inventories.fromTag(compoundTag, this.inventory);
	}

	@Override
	public CompoundTag toTag(CompoundTag compoundTag) {
		super.toTag(compoundTag);

		compoundTag.putInt("DryTime", this.dryTime);
		Inventories.toTag(compoundTag, this.inventory);

		return compoundTag;
	}

	@Override
	public void fromClientTag(CompoundTag compoundTag) {
		Inventories.fromTag(compoundTag, this.inventory);
	}

	@Override
	public CompoundTag toClientTag(CompoundTag compoundTag) {
		Inventories.toTag(compoundTag, this.inventory);
		return compoundTag;
	}

	// Behavior
	private boolean isDrying() {
		return this.dryTime > 0;
	}

	@Override
	public void tick() {
		if (this.isDrying()) {
			this.dryTime -= 1;
			return;
		} else if (this.inventory.get(0) == ItemStack.EMPTY) {
			return;
		}

		DryingRecipe recipe = this.getDryingRecipe(this.inventory.get(0));
		if (recipe != null) {
			this.inventory.set(0, recipe.getOutput());
			this.markDirty();
		}
	}
	
	private DryingRecipe getDryingRecipe(ItemStack stack) {
		Inventory inventory = new BasicInventory(stack);
		return this.world.getRecipeManager().getFirstMatch(Main.DRYING_RECIPE_TYPE, inventory, this.world).orElse(null);
	}

	public ActionResult takeRackItem(ItemStack handStack, PlayerEntity player, Hand hand) {
		// Do not attempt to take nothing
		if (this.inventory.get(0) == ItemStack.EMPTY) {
			return ActionResult.CONSUME;
		}

		// Give player the drying rack
		player.giveItemStack(this.inventory.get(0));

		// Clear drying rack contents
		this.inventory.set(0, ItemStack.EMPTY);
		this.dryTime = 0;
		this.markDirty();

		return ActionResult.SUCCESS;
	}

	public ActionResult startDrying(ItemStack handStack, PlayerEntity player, Hand hand) {
		if (this.inventory.get(0) != ItemStack.EMPTY) {
			return ActionResult.CONSUME;
		}

		ItemStack rackStack = handStack.copy();
		rackStack.setCount(1);

		this.inventory.set(0, rackStack);
		this.markDirty();

		// Remove one from player inventory if not in creative mode
		if (!player.abilities.creativeMode) {
			handStack.decrement(1);
		}

		DryingRecipe recipe = this.getDryingRecipe(rackStack);
		if (recipe == null) return ActionResult.CONSUME;

		// Start ticking down drying timer
		this.dryTime = recipe.getCookTime();

		return ActionResult.CONSUME;
	}
}