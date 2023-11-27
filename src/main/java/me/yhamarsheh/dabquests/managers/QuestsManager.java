package me.yhamarsheh.dabquests.managers;

import me.yhamarsheh.dabquests.DabQuests;
import me.yhamarsheh.dabquests.objects.Quest;
import me.yhamarsheh.dabquests.objects.quests.FishingQuest;
import me.yhamarsheh.dabquests.objects.quests.ToolsQuest;
import me.yhamarsheh.dabquests.storage.objects.DabPlayer;
import me.yhamarsheh.dabquests.utilities.ChatUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestsManager {

    private Map<Player, Quest<?>> activeQuests;
    private final DabQuests plugin;
    public QuestsManager(DabQuests plugin) {
        this.plugin = plugin;
        this.activeQuests = new HashMap<>();
    }

    public Quest<?> getActiveQuest(Player player) {
        if (!activeQuests.containsKey(player)) return null;
        return activeQuests.get(player);
    }

    public List<Quest<?>> getCompletedQuests(Player player) {
        DabPlayer dabPlayer = plugin.getPlayersManager().getDabPlayer(player);
        List<Quest<?>> quests = new ArrayList<>();

        for (Quest<?> quest : dabPlayer.getQuests()) {
            if (quest.hasCompleted()) quests.add(quest);
        }

        return quests;
    }

    public Quest<?> getQuestByName(Player player, String name) {
        DabPlayer dabPlayer = plugin.getPlayersManager().getDabPlayer(player);
        switch (name) {
            case "Kyle":
                Quest<?> fishingQuest = new FishingQuest(player);
                dabPlayer.getQuests().add(fishingQuest);
                return fishingQuest;
            case "Arthur":
                Quest<?> toolsQuest = new ToolsQuest(player);
                dabPlayer.getQuests().add(toolsQuest);
                return new ToolsQuest(player);
            default:
                return null;
        }
    }

    public Quest<?> getQuestByName(Player player, String name, int data) {
        switch (name) {
            case "Kyle":
                return new FishingQuest(player, data);
            case "Arthur":
                return new ToolsQuest(player, data);
            default:
                return null;
        }
    }

    public boolean hasActiveQuest(Player player) {
        return getActiveQuest(player) != null;
    }

    public void cancel(Player player, boolean completed) {
        Quest<?> quest = getActiveQuest(player);
        quest.removePlayer(player);
        HandlerList.unregisterAll(quest);

        this.activeQuests.remove(player);

        if (completed) {
            player.sendMessage(ChatUtils.color("&a&lCOMPLETED! &aYou've completed the quest " + quest.getQuestDisplayName()
                    + "&a.\n&e&lREWARDS &e- &cSoon"));
            return;
        }

        player.sendMessage(ChatUtils.color("&c&lCANCELLED! &cYou've cancelled the quest " + quest.getQuestDisplayName() + "&e."));
    }

    public Map<Player, Quest<?>> getActiveQuests() {
        return activeQuests;
    }

}
