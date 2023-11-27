package me.yhamarsheh.dabquests.managers;

import me.yhamarsheh.dabquests.DabQuests;
import me.yhamarsheh.dabquests.storage.objects.DabPlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayersManager {

    private DabQuests plugin;
    private Map<UUID, DabPlayer> playersList;
    public PlayersManager(DabQuests plugin) {
        this.plugin = plugin;
        this.playersList = new HashMap<>();
    }

    public void addPlayer(UUID uuid, DabPlayer player) {
        this.playersList.put(uuid, player);
    }

    public void addPlayer(Player player, DabPlayer dabPlayer) {
        this.playersList.put(player.getUniqueId(), dabPlayer);
    }

    public void removePlayer(UUID uuid) {
        this.playersList.remove(uuid);
    }

    public void removePlayer(Player player) {
        this.playersList.remove(player.getUniqueId());
    }

    public DabPlayer getDabPlayer(Player player) {
        return playersList.get(player.getUniqueId());
    }

    public void disable() {
        for (DabPlayer dabPlayer : playersList.values()) {
            dabPlayer.saveData();
        }
    }
}
