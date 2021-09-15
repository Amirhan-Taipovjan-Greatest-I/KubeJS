package dev.latvian.kubejs.util;

import dev.latvian.kubejs.KubeJS;
import net.minecraft.resources.ResourceLocation;

/**
 * @author LatvianModder
 */
public abstract class BuilderBase {
	public final ResourceLocation id;
	public String translationKey;
	public String displayName;

	public BuilderBase(String s) {
		id = UtilsJS.getMCID(KubeJS.appendModId(s));
		translationKey = getBuilderType() + "." + id.getNamespace() + "." + id.getPath();
		displayName = "";
	}

	public abstract String getBuilderType();

	@Deprecated
	public void add() {
		ConsoleJS.STARTUP.setLineNumber(true);
		ConsoleJS.STARTUP.log("You no longer need to use .add() at end of " + getBuilderType() + " builder!");
		ConsoleJS.STARTUP.setLineNumber(false);
	}

	public BuilderBase translationKey(String key) {
		translationKey = key;
		return this;
	}

	public BuilderBase displayName(String name) {
		displayName = name;
		return this;
	}
}
