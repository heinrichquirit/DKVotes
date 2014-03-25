package main.java.net.dkcraft.dkvotes.commands;

import java.util.logging.Level;

import main.java.net.dkcraft.dkvotes.DKVotes;
import main.java.net.dkcraft.dkvotes.utils.Util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class VoteStore extends BaseCommand {

	private final ChatColor GREEN = ChatColor.GREEN;
	private final ChatColor RED = ChatColor.RED;
	private final ChatColor YELLOW = ChatColor.YELLOW;
	
	private DKVotes p;
	public VoteStore(DKVotes p) {
		this.p = p;
	}
	
	@SuppressWarnings("deprecation")
	public void execute(Player player, String[] args) {
		if (args.length == 0) {
			if (p.store.size() == 0) {
				player.sendMessage(ChatColor.RED + "There are no packages to display, check your configuration settings.");
				return;
			}
			int count = 1;
			player.sendMessage(GREEN + "----------== VoteStore ==----------");
			for (String reward : p.store) {
				String[] value = reward.split(":");
				Material material = Material.getMaterial(value[1].toUpperCase());
				String material_name = material.name().substring(0, 1) + material.name().substring(1, material.name().length()).toLowerCase();
				player.sendMessage(YELLOW + "" + (count++) + ". " + "Buy " 
						+ GREEN + value[0] 
						+ YELLOW + " of " 
						+ GREEN + material_name
						+ YELLOW + " for " 
						+ GREEN + value[2] 
						+ YELLOW + " vote points.");
			}
			player.sendMessage(GREEN + "----------------------------------");
		}
		else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("buy")) {
				player.sendMessage(RED + "Incorrect syntax, usage: /votestore buy [id]");
			}
		}
		else if (args.length == 2) {
			if (args[0].equalsIgnoreCase("buy")) {
				int vote_balance = p.vote_handler.getConfig().getInt(player.getName());
				int reward_id = 1;
				try {
					reward_id = Integer.parseInt(args[1]);
				} catch (NumberFormatException e) {
					player.sendMessage(RED + args[1] + " is not a number.");
				}
				if (reward_id <= p.store.size()) {
					String reward = p.store.get(reward_id-1);
					String[] value = reward.split(":");
					try {
						ItemStack item = new ItemStack(Material.valueOf(value[1].toUpperCase()), Integer.parseInt(value[0]));
						int point_cost = Integer.parseInt(value[2]);
						if (vote_balance < point_cost) {
							int difference = point_cost - vote_balance;
							player.sendMessage(YELLOW + "You need " + GREEN + difference + YELLOW + " vote points to purchase this reward.");
							return;
						}
						p.vote_handler.reload();
						player.getInventory().addItem(item);
						player.updateInventory();
						player.sendMessage(YELLOW + "You received " + GREEN + item.getAmount() + " " + item.getType().name().toLowerCase() + "(s)" + YELLOW + ".");
						p.vote_handler.getConfig().set(player.getName(), vote_balance - point_cost);
						p.vote_handler.save();
					} catch (NumberFormatException e) {
						Util.log(Level.SEVERE, "Format your store list correctly in your configuration settings.");
						e.printStackTrace();
					}
				} else {
					player.sendMessage(RED + "Reward " + reward_id + " does not exist.");
				}
			}
		}
	}

}
