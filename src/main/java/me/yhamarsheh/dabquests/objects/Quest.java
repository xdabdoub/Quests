package me.yhamarsheh.dabquests.objects;

import me.yhamarsheh.dabquests.DabQuests;
import me.yhamarsheh.dabquests.managers.DRequirement;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public abstract class Quest<T extends Event> implements Listener {

    protected DabQuests plugin;
    private String questDisplayName;
    private String npcName;
    private List<DRequirement> requirements;
    private List<Player> players;
   // private List<Reward> rewards;

    protected Quest(String questDisplayName, String npcName, List<DRequirement> requirements) {
        this.questDisplayName = questDisplayName;
        this.requirements = requirements;
        this.npcName = npcName;
        this.players = new ArrayList<>();
        this.plugin = (DabQuests) JavaPlugin.getProvidingPlugin(DabQuests.class);
    }

    protected Quest() {
        this.players = new ArrayList<>();
    }

    public abstract void questHandler(T event);
    public abstract boolean hasCompleted();

    public void addPlayer(Player player) {
        this.players.add(player);
        plugin.getQuestsManager().getActiveQuests().put(player, this);
    }

    public void removePlayer(Player player) {
        this.players.remove(player);
        plugin.getQuestsManager().getActiveQuests().remove(player);
    }

    public abstract boolean checkCompletion(Player player);


    public boolean areRequirementsMet(Player player) {
        for (DRequirement requirement : requirements) {
            if (!requirement.isMet(player)) return false;
        }
        return true;
    }


    public String getQuestDisplayName() {
        return questDisplayName;
    }
    public String getNpcName() {
        return npcName;
    }
    public abstract String toString();

}
