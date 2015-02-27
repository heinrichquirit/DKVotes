package main.java.net.dkcraft.dkvotes;

import java.util.ArrayList;
import java.util.List;

import main.java.net.dkcraft.dkvotes.commands.CommandManager;
import main.java.net.dkcraft.dkvotes.listeners.VoteListener;
import main.java.net.dkcraft.dkvotes.utils.ConfigPath;

import org.bukkit.plugin.java.JavaPlugin;

public class DKVotes extends JavaPlugin {
	
	private List<String> commands = new ArrayList<String>();
	
	/* Configuration settings */
	public int vote_point_reward;
	public List<String> store;
	public ConfigHandler vote_handler;
	
	public void onEnable() {
		saveDefaultConfig();
		loadValues();
		vote_handler = new ConfigHandler(this, "votepoints.yml");
		vote_handler.load();
		getServer().getPluginManager().registerEvents(new VoteListener(this), this);
		loadCommands();
		for (String command : commands) {
			getCommand(command).setExecutor(new CommandManager(this));
		}
	}
	
	private void loadCommands() {
		commands.add("addvotepoint");
		commands.add("removevotepoint");
		commands.add("votepoints");
		commands.add("votestore");
	}
	
	private void loadValues() {
		vote_point_reward = getConfig().getInt(ConfigPath.VOTE_POINT_REWARD);
		store = getConfig().getStringList(ConfigPath.STORE);
	}
}
