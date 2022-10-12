package dev.latvian.mods.kubejs;

import dev.latvian.mods.kubejs.util.KubeJSPlugins;

import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.util.Properties;

/**
 * @author LatvianModder
 */
public class CommonProperties {
	private static CommonProperties instance;

	public static CommonProperties get() {
		if (instance == null) {
			instance = new CommonProperties();
		}

		return instance;
	}

	private final Properties properties;
	private boolean writeProperties;

	public boolean hideServerScriptErrors;
	public boolean serverOnly;
	public boolean announceReload;
	public String packMode;
	public boolean debugInfo;
	public boolean saveDevPropertiesInConfig;

	private CommonProperties() {
		properties = new Properties();

		try {
			var propertiesFile = KubeJSPaths.CONFIG.resolve("common.properties");
			writeProperties = false;

			if (Files.exists(propertiesFile)) {
				try (Reader reader = Files.newBufferedReader(propertiesFile)) {
					properties.load(reader);
				}
			} else {
				writeProperties = true;
			}

			hideServerScriptErrors = get("hideServerScriptErrors", false);
			serverOnly = get("serverOnly", false);
			announceReload = get("announceReload", true);
			packMode = get("packmode", "default");
			debugInfo = get("debugInfo", false);
			saveDevPropertiesInConfig = get("saveDevPropertiesInConfig", false);

			KubeJSPlugins.forEachPlugin(p -> p.loadCommonProperties(this));

			if (writeProperties) {
				try (Writer writer = Files.newBufferedWriter(propertiesFile)) {
					properties.store(writer, "KubeJS Common Properties");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		KubeJS.LOGGER.info("Loaded common.properties");
	}

	public void remove(String key) {
		var s = properties.getProperty(key);

		if (s != null) {
			properties.remove(key);
			writeProperties = true;
		}
	}

	public String get(String key, String def) {
		var s = properties.getProperty(key);

		if (s == null) {
			properties.setProperty(key, def);
			writeProperties = true;
			return def;
		}

		return s;
	}

	public boolean get(String key, boolean def) {
		return get(key, def ? "true" : "false").equals("true");
	}
}