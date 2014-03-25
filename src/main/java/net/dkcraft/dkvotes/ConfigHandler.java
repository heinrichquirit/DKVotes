package main.java.net.dkcraft.dkvotes;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import main.java.net.dkcraft.dkvotes.utils.Util;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigHandler {

	private Plugin plugin;
	private String file_name;
	private File file;
	private FileConfiguration configuration;
	
	public ConfigHandler(Plugin plugin, String file_name) {
		this.plugin = plugin;
		this.file_name = file_name;
		file = new File(plugin.getDataFolder(), file_name);
		configuration = YamlConfiguration.loadConfiguration(file);
	}
	
	public FileConfiguration getConfig() {
		return configuration;
	}
	
	public void load() {
		file = new File(plugin.getDataFolder(), file_name);
		Util.makeFile(file);
		configuration = YamlConfiguration.loadConfiguration(file);
		reload();
	}
	
	public void reload() {
		if (file == null) {
			file = new File(plugin.getDataFolder(), file_name);
		}
		configuration = YamlConfiguration.loadConfiguration(file);
		InputStream stream = plugin.getResource(file_name);
		if (stream == null) return;
		YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(stream);
		configuration.setDefaults(defConfig);
	}
	
	public void save() {
		if (configuration == null || file == null) return;
		try {
			configuration.save(file);
		} catch (IOException e) {
			e.printStackTrace();
			Util.log(Level.SEVERE, "Could not save configuration changes to " + file.getName());
		}
	}
	
}
