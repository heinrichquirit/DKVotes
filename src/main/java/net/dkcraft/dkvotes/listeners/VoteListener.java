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
		@SuppressWarnings("deprecation")
		Player player = Bukkit.getPlayer(event.getVote().getUsername());
		String pId = player.getUniqueId().toString();
		if (!p.vote_handler.getConfig().contains(pId)) {
			p.vote_handler.reload();
			p.vote_handler.getConfig().set(pId, 1);
			p.vote_handler.save();
			return;
		}
		p.vote_handler.reload();
		p.vote_handler.getConfig().set(pId, p.vote_handler.getConfig().getInt(pId) + 1);
		if (player != null) {
			player.sendMessage(YELLOW + "You received " + GREEN + "1" + YELLOW + " vote point for voting.");
		}
		p.vote_handler.save();
	}
	
}
