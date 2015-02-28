package main.java.net.dkcraft.dkvotes.listeners;

import java.util.logging.Level;

import main.java.net.dkcraft.dkvotes.DKVotes;
import main.java.net.dkcraft.dkvotes.utils.UUIDFetcher;
import main.java.net.dkcraft.dkvotes.utils.Util;

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

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onVote(VotifierEvent event) {
		String name = event.getVote().getUsername();
		Player player = Bukkit.getPlayer(name);
		String pId = "";
		if (player != null) {
			pId = player.getUniqueId().toString();
		} else {
			try {
				// Need to use UUID fetcher to fetch offline UUID since
				// Spigots/Bukkits offline fetch method returns the wrong 
				// UUID or is the MD5 checksum of the name string.
				// This UUID fetcher utilizes Mojangs UUID api, which
				// is a reliable way of fetching an accurate uuid.
				pId = UUIDFetcher.getUUIDOf(name).toString();
			} catch (Exception e) {
				// Print error so they notice
				Util.log(Level.SEVERE, "UUIDFetcher could not retrieve UUID for " + name + ", using OfflinePlayer fetch method instead.");
				pId = Bukkit.getOfflinePlayer(name).getUniqueId().toString();
			}
		}
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
