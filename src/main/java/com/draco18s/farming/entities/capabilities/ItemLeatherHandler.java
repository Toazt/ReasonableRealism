package com.draco18s.farming.entities.capabilities;

import com.draco18s.farming.FarmingBase;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class ItemLeatherHandler extends ItemStackHandler {
	public ItemLeatherHandler(int i) {
		super(i);
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		if(stack.getItem() != FarmingBase.rawLeather) return stack;
		return super.insertItem(slot, stack, simulate);
	}

	@Override
	protected void onContentsChanged(int slot) {
		super.onContentsChanged(slot);
		if(slot+1 >= stacks.size()) return;
		if(this.stacks.get(slot) != ItemStack.EMPTY && this.stacks.get(slot+1) != ItemStack.EMPTY) {
			if(ItemStack.areItemsEqual(this.stacks.get(slot), this.stacks.get(slot+1))) {
				int totalSize = this.stacks.get(slot).getCount() + this.stacks.get(slot+1).getCount();
				int change = this.stacks.get(slot+1).getCount();
				if(totalSize > this.stacks.get(slot).getMaxStackSize()) {
					change = this.stacks.get(slot).getMaxStackSize() - this.stacks.get(slot).getCount();
				}
				this.stacks.get(slot).grow(change);
				this.stacks.get(slot+1).shrink(change);
				if(this.stacks.get(slot+1).isEmpty()) {
					this.stacks.set(slot+1, ItemStack.EMPTY);
				}
				onContentsChanged(slot+1);
			}
		}
	}

	@Override
	protected int getStackLimit(int slot, ItemStack stack) {
		return 1;
	}
}