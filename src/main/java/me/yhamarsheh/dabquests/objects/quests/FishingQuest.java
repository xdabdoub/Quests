package me.yhamarsheh.dabquests.objects.quests;

import me.yhamarsheh.dabquests.managers.DRequirement;
import me.yhamarsheh.dabquests.objects.Quest;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class FishingQuest extends Quest<PlayerFishEvent> {

    private int caught;
    private Player player;
    public FishingQuest(Player player) {
        super("&bOcean Odyssey: The Tropical Fin Expedition", "This quest objective is to fish 25 Tropical Fishes!", "Kyle", List.of(new DRequirement(DRequirement.Requirement.PLAYERS, 2)));
        this.player = player;
        addPlayer(player);

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    // Use saved data
    public FishingQuest(Player player, int caught) {
        super("&bOcean Odyssey: The Tropical Fin Expedition", "This quest objective is to fish 25 Tropical Fishes!", "Kyle", List.of(new DRequirement(DRequirement.Requirement.PLAYERS, 2)));
        this.player = player;
        this.caught = caught;

        if (caught != -1) {
            Bukkit.getPluginManager().registerEvents(this, plugin);
            addPlayer(player);
        }
    }

    @Override
    @EventHandler
    public void questHandler(PlayerFishEvent event) {
        Player p = event.getPlayer();
        if (player != p) return;
        if (event.getCaught() == null) return;
        // if (event.getCaught().getType() == EntityType.TROPICAL_FISH) caught++;
        caught++;
    }

    @Override
    public boolean hasCompleted() {
        return caught == -1;
    }

    @Override
    public boolean checkCompletion(Player player) {
        int invCaught = 0;
        for (ItemStack item : player.getInventory().getContents()) {
            if (item == null || item.getType() == Material.AIR) continue;

            if (item.getType() == Material.TROPICAL_FISH) {
                invCaught = invCaught + item.getAmount();
                if (invCaught == 25) break;
            }
        }

        if (invCaught == 25 && caught >= 2) {
            int amountToRemove = 25;
            invCaught = 0;

            for (ItemStack item : player.getInventory().getContents()) {
                if (item != null && item.getType() == Material.TROPICAL_FISH) {
                    int amountInStack = item.getAmount();

                    if (amountInStack <= amountToRemove) {
                        player.getInventory().removeItem(item);
                        invCaught += amountInStack;
                        amountToRemove -= amountInStack;
                    } else {
                        item.setAmount(amountInStack - amountToRemove);
                        invCaught += amountToRemove;
                        amountToRemove = 0;
                        break;
                    }
                }
            }

            player.updateInventory();

            invCaught = 0;
            caught = -1;
            plugin.getQuestsManager().endActiveQuest(player, true);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return getNpcName() + ";" + caught;
    }
}
