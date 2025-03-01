package me.yhamarsheh.dabquests.objects.quests;

import me.yhamarsheh.dabquests.components.Reward;
import me.yhamarsheh.dabquests.managers.DRequirement;
import me.yhamarsheh.dabquests.objects.Quest;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class FishingQuest extends Quest<PlayerFishEvent> {

    private int caught;
    private Player player;
    public FishingQuest(Player player) {
        super("&bOcean Odyssey: The Tropical Fin Expedition", "This quest objective is to fish 25 Raw Cod and bring them to Kyle!", "Kyle", List.of(new DRequirement(DRequirement.Requirement.PLAYERS, 2)));
        this.player = player;
        addPlayer(player);
        addReward(new Reward("$150 coins", "eco give %player_name% 150"));
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    // Use saved data
    public FishingQuest(Player player, int caught) {
        super("&bOcean Odyssey: The Tropical Fin Expedition", "This quest objective is to fish 25 Raw Cod and bring them to Kyle!", "Kyle", List.of(new DRequirement(DRequirement.Requirement.PLAYERS, 2)));
        this.player = player;
        this.caught = caught;

        if (caught != -1) {
            Bukkit.getPluginManager().registerEvents(this, plugin);
            addPlayer(player);
            addReward(new Reward("$150 coins", "eco give %player_name% 150"));
        }
    }

    @Override
    @EventHandler
    public void questHandler(PlayerFishEvent event) {
        Player p = event.getPlayer();
        if (player != p) return;
        if (event.getCaught() == null) return;
        Item item = (Item) event.getCaught();
        if (item == null) return;

        if (item.getItemStack().getType() == Material.COD) caught++;
    }

    @Override
    public boolean hasCompleted() {
        return caught == -1;
    }

    @Override
    public boolean checkCompletion(Player player) {
        boolean invCaught = player.getInventory().containsAtLeast(new ItemStack(Material.COD), 5);

        if (invCaught && caught >= 25) {
            player.getInventory().removeItem(new ItemStack(Material.COD, 25));
            player.updateInventory();

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
