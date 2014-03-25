package main.java.net.dkcraft.dkvotes.commands;

import main.java.net.dkcraft.dkvotes.DKVotes;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class VotePoints extends BaseCommand {

	private final ChatColor GREEN = ChatColor.GREEN;
	private final ChatColor YELLOW = ChatColor.YELLOW;
	
	private DKVotes p;
	public VotePoints(DKVotes p) {
		this.p = p;
	}
	
	public void execute(Player player, String[] args) {
		if (args.length == 0) {
			player.sendMessage(YELLOW + "You have " + GREEN + p.vote_handler.getConfig().getInt(player.getName()) + YELLOW + " vote points.");
		}
	}

}
