package dev.latvian.mods.kubejs.recipe.filter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LatvianModder
 */
public class OrFilter implements RecipeFilter {
	public final List<RecipeFilter> list = new ArrayList<>(2);

	@Override
	public boolean test(FilteredRecipe r) {
		for (var p : list) {
			if (p.test(r)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public String toString() {
		return "OrFilter[" + list + ']';
	}
}
