package dev.latvian.mods.kubejs.player;

import dev.latvian.mods.kubejs.item.InventoryJS;
import dev.latvian.mods.kubejs.level.BlockContainerJS;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

/**
 * @author LatvianModder
 */
public class ChestEventJS extends InventoryEventJS {
	private InventoryJS inventory;

	public ChestEventJS(ServerPlayer player, AbstractContainerMenu menu) {
		super(player, menu);
	}

	public Container getWrappedInventory() {
		return ((ChestMenu) getInventoryContainer()).getContainer();
	}

	public InventoryJS getInventory() {
		if (inventory == null) {
			inventory = new InventoryJS(getWrappedInventory());
		}

		return inventory;
	}

	@Nullable
	public BlockContainerJS getBlock() {
		if (getWrappedInventory() instanceof BlockEntity be) {
			return getLevel().kjs$getBlock(be);
		}

		return null;
	}
}