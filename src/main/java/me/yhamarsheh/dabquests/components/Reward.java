package me.yhamarsheh.dabquests.components;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Reward {

    private final String command;
    private final String description;

    public Reward(String description, String command) {
        this.description = description;
        this.command = command;
    }

    public void give(Player player) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command
                .replace("%player_name%", player.getName()));
    }

    public String getCommand() {
        return command;
    }

    public String toString() {
        return description;
    }
}
