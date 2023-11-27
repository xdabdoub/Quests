package me.yhamarsheh.dabquests.commands.sub;

import me.yhamarsheh.dabquests.commands.DabCommand;
import me.yhamarsheh.dabquests.objects.Quest;
import me.yhamarsheh.dabquests.utilities.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartQuestCMD extends DabCommand {

    public StartQuestCMD() {
        super("start");
    }

    @Override
    public void execute(Player player, String[] args) {
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("dabquests.admin.start")) {
            sender.sendMessage(ChatUtils.color("&cYou don't have access to this command."));
            return;
        }

        if (args.length <= 2) {
            sender.sendMessage(ChatUtils.color("&cInvalid Usage: /dabquests start <player> <quest>"));
            return;
        }

        Player player = Bukkit.getPlayer(args[1]);
        String questName = args[2];
        Quest<?> quest = questsManager.getQuestByName(player, questName);

        if (player == null) {
            sender.sendMessage(ChatUtils.color("&cInvalid player."));
            return;
        }

        player.sendMessage(ChatUtils.color(String.format("""
                &aEmbark on a thrilling journey—%s beckons with a daring new quest!
                &a&lQUEST STARTED! &7- &b%s &a!
                &7%s &7Upon completing this quest, you'll receive:
                &7- &crewards soon.""", quest.getNpcName(), quest.getQuestDisplayName(), quest.getQuestDescription())));

//        player.sendMessage(ChatUtils.color("&aEmbark on a thrilling journey—Kyle beckons with a daring new quest!\n" +
//                "&a&lQUEST STARTED! &7- &b" + quest.getQuestDisplayName() + "&a!\n" +
//                "&7This quest objective is to fish 25 Tropical Fishes! Upon completing this quest, you'll receive:\n&7- &crewards soon."));
    }
}
