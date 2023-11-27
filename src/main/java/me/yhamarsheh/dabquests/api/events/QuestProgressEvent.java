package me.yhamarsheh.dabquests.api.events;

import me.yhamarsheh.dabquests.objects.Quest;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class QuestProgressEvent extends Event implements Cancellable {

    private Quest<?> quest;
    private Player player;
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;

    public QuestProgressEvent(Quest<?> quest, Player player) {
        this.quest = quest;
        this.player = player;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public Quest<?> getQuest() {
        return quest;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.isCancelled = b;
    }
}
