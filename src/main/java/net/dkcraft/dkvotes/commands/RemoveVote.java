package main.java.net.dkcraft.dkvotes.commands;

import main.java.net.dkcraft.dkvotes.DKVotes;
import main.java.net.dkcraft.dkvotes.utils.Permission;
import main.java.net.dkcraft.dkvotes.utils.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class RemoveVote extends BaseCommand {
	
	private final ChatColor GREEN = ChatColor.GREEN;
	private final ChatColor RED = ChatColor.RED;
	private final ChatColor YELLOW = ChatColor.YELLOW;
	
	private DKVotes p;
	public RemoveVote(DKVotes p) {
		this.p = p;
	}

	public void execute(Player player, String[] args) {
		if (!Util.checkPermission(player, Permission.VOTE_POINT_REMOVE)) return;
		if (args.length <= 1) {
			 player.sendMessage(RED + "Incorrect syntax, usage: /removevotepoint <player> <amount>");
		}
		else if (args.length == 2) {
			@SuppressWarnings("deprecation")
			Player target = Bukkit.getPlayer(args[0]);
			int amount = 0;
			try {
				amount = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				player.sendMessage(RED + args[1] + " is not a number.");
				return;
			}
			if (target == null) {
				player.sendMessage(RED + args[0] + " is offline.");
				return;
			}
			String targetId = target.getUniqueId().toString();
			if (amount > p.vote_handler.getConfig().getInt(targetId)) {
				player.sendMessage(RED + target.getName() + " vote points is less than " + amount + ".");
				return;
			}
			p.vote_handler.reload();
			p.vote_handler.getConfig().set(targetId, p.vote_handler.getConfig().getInt(targetId) - amount);
			p.vote_handler.save();
			player.sendMessage(YELLOW + "Removed " + GREEN + amount + YELLOW + " points from " + GREEN + target.getName() + YELLOW + ".");
			target.sendMessage(GREEN + player.getName() + YELLOW + " removed " + GREEN + amount + YELLOW + " vote points from your balance.");
		}
	}
	
}
