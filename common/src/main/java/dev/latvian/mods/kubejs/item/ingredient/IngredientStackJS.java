package dev.latvian.mods.kubejs.item.ingredient;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.item.ItemStackJS;
import dev.latvian.mods.kubejs.item.ItemStackSet;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author LatvianModder
 */
public class IngredientStackJS implements IngredientJS {
	public static IngredientStackJS stackOf(IngredientJS in) {
		return in instanceof IngredientStackJS ? (IngredientStackJS) in : new IngredientStackJS(in, 1);
	}

	public IngredientJS ingredient;
	private final int countOverride;
	public String ingredientKey;
	public String countKey;

	public IngredientStackJS(IngredientJS i, int c) {
		ingredient = i;
		countOverride = c;
		ingredientKey = "ingredient";
		countKey = "count";
	}

	public IngredientJS getIngredient() {
		return ingredient;
	}

	@Override
	public int getCount() {
		return countOverride;
	}

	@Override
	public IngredientJS withCount(int count) {
		if (count <= 0) {
			return ItemStackJS.EMPTY;
		} else if (count == 1) {
			return ingredient.copy();
		}

		return count == countOverride ? copy() : new IngredientStackJS(ingredient, count);
	}

	@Override
	public IngredientJS copy() {
		return new IngredientStackJS(ingredient.copy(), countOverride);
	}

	@Override
	public boolean test(ItemStack stack) {
		return ingredient.test(stack);
	}

	@Override
	public boolean testItem(Item item) {
		return ingredient.testItem(item);
	}

	@Override
	public boolean isEmpty() {
		return ingredient.isEmpty();
	}

	@Override
	public boolean isInvalidRecipeIngredient() {
		return countOverride <= 0 || ingredient.isInvalidRecipeIngredient();
	}

	@Override
	public void gatherStacks(ItemStackSet set) {
		ingredient.gatherStacks(set);
	}

	@Override
	public void gatherItemTypes(Set<Item> set) {
		ingredient.gatherItemTypes(set);
	}

	@Override
	public IngredientJS not() {
		return new IngredientStackJS(ingredient.not(), countOverride);
	}

	@Override
	public IngredientJS filter(IngredientJS filter) {
		return new IngredientStackJS(ingredient.filter(filter), countOverride);
	}

	@Override
	public ItemStack getFirst() {
		return ingredient.getFirst().kjs$withCount(getCount());
	}

	@Override
	public String toString() {
		return getCount() == 1 ? ingredient.toString() : (getCount() + "x " + ingredient);
	}

	@Override
	public JsonElement toJson() {
		if (RecipeJS.currentRecipe != null) {
			var e = RecipeJS.currentRecipe.serializeIngredientStack(this);

			if (e != null) {
				return e;
			}
		}

		if (countOverride == 1) {
			return ingredient.toJson();
		}

		var json = new JsonObject();
		json.add(ingredientKey, ingredient.toJson());
		json.addProperty(countKey, countOverride);
		return json;
	}

	@Override
	public IngredientStackJS asIngredientStack() {
		return this;
	}

	@Override
	public List<IngredientJS> unwrapStackIngredient() {
		if (countOverride <= 1) {
			return Collections.singletonList(ingredient.withCount(1));
		}

		List<IngredientJS> list = new ArrayList<>();

		for (var i = 0; i < countOverride; i++) {
			list.add(ingredient.withCount(1));
		}

		return list;
	}
}