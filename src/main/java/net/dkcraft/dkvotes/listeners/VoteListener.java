package main.java.net.dkcraft.dkvotes.listeners;

import main.java.net.dkcraft.dkvotes.DKVotes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.vexsoftware.votifier.model.VotifierEvent;

public class VoteListener implements Listener {
	
	private final ChatColor GREEN = ChatColor.GREEN;
	private final ChatColor YELLOW = ChatColor.YELLOW;
	
	private DKVotes p;
	public VoteListener(DKVotes p) {
		this.p = p;
	}
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onVote(VotifierEvent event) {
		String name = event.getVote().getUsername();
		Player player = Bukkit.getPlayer(name);
		if (!p.vote_handler.getConfig().contains(name)) {
			p.vote_handler.reload();
			p.vote_handler.getConfig().set(name, 1);
			p.vote_handler.save();
			return;
		}
		p.vote_handler.reload();
		p.vote_handler.getConfig().set(name, p.vote_handler.getConfig().getInt(name) + 1);
		if (player != null) {
			player.sendMessage(YELLOW + "You received " + GREEN + "1" + YELLOW + " vote point for voting.");
		}
		p.vote_handler.save();
	}
	
}
