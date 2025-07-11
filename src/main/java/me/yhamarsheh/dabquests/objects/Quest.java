package me.yhamarsheh.dabquests.objects;

import me.yhamarsheh.dabquests.DabQuests;
import me.yhamarsheh.dabquests.components.Reward;
import me.yhamarsheh.dabquests.managers.DRequirement;
import me.yhamarsheh.dabquests.utilities.ChatUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public abstract class Quest<T extends Event> implements Listener {

    protected DabQuests plugin;
    private String questDisplayName;
    private String questDescription;
    private String npcName;
    private List<DRequirement> requirements;
    private List<Player> players;
    private List<Reward> rewards;

    protected Quest(String questDisplayName, String questDescription, String npcName, List<DRequirement> requirements) {
        this.questDisplayName = questDisplayName;
        this.questDescription = questDescription;
        this.requirements = requirements;
        this.npcName = npcName;
        this.players = new ArrayList<>();
        this.plugin = (DabQuests) JavaPlugin.getProvidingPlugin(DabQuests.class);
        this.rewards = new ArrayList<>();
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

    public void addReward(Reward reward) {
        rewards.add(reward);
    }

    public abstract boolean checkCompletion(Player player);


    public boolean areRequirementsMet(Player player) {
        for (DRequirement requirement : this.requirements) {
            if (!requirement.isMet(player)) return false;
        }
        return true;
    }

    public void giveRewards(Player player) {
        for (Reward reward : rewards) {
            reward.give(player);
        }
    }


    public String getQuestDisplayName() {
        return this.questDisplayName;
    }
    public String getQuestDescription() {
        return this.questDescription;
    }
    public String getNpcName() {
        return this.npcName;
    }
    public abstract String toString();

    public List<Reward> getRewards() {
        return rewards;
    }

    public String getRewardsAsString() {
        StringBuilder sb = new StringBuilder();
        for (Reward reward : rewards) {
            sb.append(ChatUtils.color("&7- &b" + reward.toString())).append("\n");
        }

        if (sb.toString().isEmpty()) sb.append("&7- &cNo rewards");
        return sb.toString();
    }

}
