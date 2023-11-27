package me.yhamarsheh.dabquests.commands;

import me.yhamarsheh.dabquests.DabQuests;
import me.yhamarsheh.dabquests.managers.QuestsManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class DabCommand {

    private String command;
    private DabQuests plugin;
    protected QuestsManager questsManager;

    public DabCommand(String command) {
        this.command = command;
        this.plugin = (DabQuests) JavaPlugin.getProvidingPlugin(DabQuests.class);
        this.questsManager = plugin.getQuestsManager();
    }

    public abstract void execute(Player player, String[] args);
    public abstract void execute(CommandSender sender, String[] args);

}
