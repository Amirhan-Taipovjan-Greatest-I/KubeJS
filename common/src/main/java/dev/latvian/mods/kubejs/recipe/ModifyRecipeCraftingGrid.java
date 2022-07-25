package dev.latvian.mods.kubejs.recipe;

import dev.latvian.mods.kubejs.item.ItemStackJS;
import dev.latvian.mods.kubejs.item.ingredient.IngredientJS;
import dev.latvian.mods.kubejs.item.ingredient.MatchAllIngredientJS;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ModifyRecipeCraftingGrid {
	private final CraftingContainer container;

	public ModifyRecipeCraftingGrid(CraftingContainer c) {
		container = c;
	}

	public ItemStackJS get(int index) {
		return ItemStackJS.of(container.getItem(index).copy());
	}

	public List<ItemStackJS> findAll(IngredientJS ingredient) {
		List<ItemStackJS> list = new ArrayList<>();

		for (int i = 0; i < container.getContainerSize(); i++) {
			ItemStack stack = container.getItem(i);

			if (ingredient.test(stack)) {
				list.add(ItemStackJS.of(stack.copy()));
			}
		}

		return list;
	}

	public List<ItemStackJS> findAll() {
		return findAll(MatchAllIngredientJS.INSTANCE);
	}

	public ItemStackJS find(IngredientJS ingredient, int skip) {
		for (int i = 0; i < container.getContainerSize(); i++) {
			ItemStack stack = container.getItem(i);

			if (ingredient.test(stack)) {
				if (skip > 0) {
					skip--;
				} else {
					return ItemStackJS.of(stack.copy());
				}
			}
		}

		return ItemStackJS.EMPTY;
	}

	public ItemStackJS find(IngredientJS ingredient) {
		return find(ingredient, 0);
	}

	public int getWidth() {
		return container.getWidth();
	}

	public int getHeight() {
		return container.getHeight();
	}

	@Nullable
	public AbstractContainerMenu getMenu() {
		return container.menu;
	}

	@Nullable
	public Player getPlayer() {
		if (container.menu instanceof CraftingMenu menu && menu.player != null) {
			return menu.player;
		} else if (container.menu instanceof InventoryMenu menu && menu.owner != null) {
			return menu.owner;
		}

		return null;
	}
}
