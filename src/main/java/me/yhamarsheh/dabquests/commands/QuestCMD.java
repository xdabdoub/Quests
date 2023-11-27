package me.yhamarsheh.dabquests.commands;

import me.yhamarsheh.dabquests.DabQuests;
import me.yhamarsheh.dabquests.commands.sub.StartQuestCMD;
import me.yhamarsheh.dabquests.utilities.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class QuestCMD implements CommandExecutor {

    private DabQuests plugin;
    public QuestCMD(DabQuests plugin) {
        this.plugin = plugin;
        plugin.getCommand("dabquests").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(ChatUtils.color("&cNot enough arguments."));
            return true;
        }

        if (args[0].equalsIgnoreCase("start")) {
            new StartQuestCMD().execute(sender, args);
            return true;
        }

        if (!(sender instanceof Player)) return true;

        Player player = (Player) sender;
        return false;
    }
}
