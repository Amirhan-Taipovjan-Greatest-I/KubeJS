package dev.latvian.mods.kubejs.recipe.mod;

import dev.latvian.mods.kubejs.recipe.RecipeArguments;
import dev.latvian.mods.kubejs.recipe.RecipeJS;

/**
 * @author LatvianModder
 */
public class ArsNouveauGlyphPressRecipeJS extends RecipeJS {
	@Override
	public void create(RecipeArguments args) {
		outputItems.add(parseResultItem(args.get(0)));
		inputItems.add(parseIngredientItem(args.get(1)));
		json.addProperty("tier", args.get(2).toString());
	}

	@Override
	public void deserialize() {
		outputItems.add(parseResultItem(json.get("output")));
		inputItems.add(parseIngredientItem(json.get("input")));
	}

	@Override
	public void serialize() {
		if (serializeOutputs) {
			json.addProperty("output", outputItems.get(0).getId());
		}

		if (serializeInputs) {
			json.addProperty("input", inputItems.get(0).getFirst().getId());
		}
	}
}