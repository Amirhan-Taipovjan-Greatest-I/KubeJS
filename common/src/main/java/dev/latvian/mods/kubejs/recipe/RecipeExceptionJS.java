package dev.latvian.mods.kubejs.recipe;

/**
 * @author LatvianModder
 */
public class RecipeExceptionJS extends IllegalArgumentException {
	public boolean fallback;
	public boolean error;

	public RecipeExceptionJS(String m) {
		super(m);
		fallback = false;
		error = false;
	}

	public RecipeExceptionJS(String m, Throwable cause) {
		super(m, cause);
		fallback = false;
		error = false;
	}

	@Override
	public String toString() {
		return getLocalizedMessage();
	}

	public RecipeExceptionJS fallback() {
		fallback = true;
		return this;
	}

	public RecipeExceptionJS error() {
		error = true;
		return this;
	}
}