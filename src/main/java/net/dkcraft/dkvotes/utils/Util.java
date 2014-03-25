package main.java.net.dkcraft.dkvotes.utils;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Util {
	
	public static boolean checkPermission(Player player, String permission){
		if (player.isOp() || player.hasPermission("*") || player.hasPermission(permission)){
			return true;
		}
		player.sendMessage(ChatColor.RED + "You do not have permission to use this.");
		return false;
	}
	
	public static void makeFile(File file) {
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace(); // So they notice it
				log(Level.SEVERE, file.getName() + " could not be created!");
			}
		}
	}
	
	public static void log(Level level, String message) {
		Bukkit.getLogger().log(level, "[DKVotes] " + message);
	}
	
}
