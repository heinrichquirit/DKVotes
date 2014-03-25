package main.java.net.dkcraft.dkvotes.commands;

import java.util.HashMap;

import main.java.net.dkcraft.dkvotes.DKVotes;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandManager implements CommandExecutor {

	private HashMap<String, BaseCommand> commands = new HashMap<String, BaseCommand>();
	
	private DKVotes p;
	public CommandManager(DKVotes p) {
		this.p = p;
		loadCommands();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		if (!(sender instanceof Player)) return true;
		Player player = (Player) sender;
		String cmdName = cmd.getName();
		if (!commands.containsKey(cmdName)) {
			player.sendMessage(ChatColor.RED + "That command doesn't exist.");
			return true;
		}
		commands.get(cmdName).execute(player, args);
		return true;
	}
	
	private void loadCommands() {
		commands.put("addvotepoint", new AddVote(p));
		commands.put("removevotepoint", new RemoveVote(p));
		commands.put("votepoints", new VotePoints(p));
		commands.put("votestore", new VoteStore(p));
	}
	
}
