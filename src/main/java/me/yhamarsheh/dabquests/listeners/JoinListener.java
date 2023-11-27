package me.yhamarsheh.dabquests.listeners;

import me.yhamarsheh.dabquests.DabQuests;
import me.yhamarsheh.dabquests.storage.objects.DabPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class JoinListener implements Listener {

    private DabQuests plugin;
    public JoinListener(DabQuests plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPreJoin(AsyncPlayerPreLoginEvent e) {
        UUID uuid = e.getUniqueId();
        DabPlayer dabPlayer = new DabPlayer(plugin, uuid);
        plugin.getPlayersManager().addPlayer(uuid, dabPlayer);
    }

    @EventHandler
    public void onPreJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        DabPlayer dabPlayer = plugin.getPlayersManager().getDabPlayer(player);
        dabPlayer.setPlayer(player);
        Bukkit.getScheduler().runTaskAsynchronously(plugin, dabPlayer::reload);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        DabPlayer dabPlayer = plugin.getPlayersManager().getDabPlayer(player);
        Bukkit.getScheduler().runTaskAsynchronously(plugin, dabPlayer::saveData);
    }
}
