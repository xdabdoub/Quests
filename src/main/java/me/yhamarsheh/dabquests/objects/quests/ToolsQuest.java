package me.yhamarsheh.dabquests.objects.quests;

import me.yhamarsheh.dabquests.components.Reward;
import me.yhamarsheh.dabquests.managers.DRequirement;
import me.yhamarsheh.dabquests.objects.Quest;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ToolsQuest extends Quest<InventoryClickEvent> {

    private int tools;
    private Player player;
    public ToolsQuest(Player player) {
        super("&bTempered Triumph: The Iron Crusade Unleashed", "This quest's objective is to Craft 4 Iron Tools and bring them to Arthur!", "Arthur", List.of(new DRequirement(DRequirement.Requirement.PLAYERS, 2)));
        this.player = player;
        addPlayer(player);
        addReward(new Reward("$70 coins", "eco give %player_name% 70"));
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    // Use saved data
    public ToolsQuest(Player player, int tools) {
        super("&bTempered Triumph: The Iron Crusade Unleashed", "This quest's objective is to Craft 4 Iron Tools and bring them to Arthur!", "Arthur", List.of(new DRequirement(DRequirement.Requirement.PLAYERS, 2)));
        this.player = player;
        this.tools = tools;

        if (tools != -1) {
            Bukkit.getPluginManager().registerEvents(this, plugin);
            addPlayer(player);
            addReward(new Reward("$70 coins", "eco give %player_name% 70"));
        }
    }

    @Override
    @EventHandler
    public void questHandler(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        if (player != p) return;
        if (!(event.getClickedInventory() instanceof CraftingInventory)) return;
        if (event.getSlot() != 0) return;

        ItemStack item = event.getCurrentItem();
        if (item == null || item.getType() == Material.AIR) return;

        if (item.getType() == Material.IRON_PICKAXE || item.getType() == Material.IRON_AXE ||
                item.getType() == Material.IRON_HOE || item.getType() == Material.IRON_SHOVEL) {
            tools++;
        }
    }

    @Override
    public boolean hasCompleted() {
        return tools == -1;
    }

    @Override
    public boolean checkCompletion(Player player) {
        int axe = 0;
        int pick = 0;
        int hoe = 0;
        int shovel = 0;

        for (ItemStack item : player.getInventory().getContents()) {
            if (item == null || item.getType() == Material.AIR) continue;

            if (item.getType() == Material.IRON_PICKAXE) {
                pick++;
            } else if (item.getType() == Material.IRON_AXE) {
                axe++;
            } else if (item.getType() == Material.IRON_HOE) {
                hoe++;
            } else if (item.getType() == Material.IRON_SHOVEL) {
                shovel++;
            }
            if (axe == 1 && pick == 1 && hoe == 1 && shovel == 1) break;
        }

        if (axe == 1 && pick == 1 && hoe == 1 && shovel == 1 && tools >= 4) {
            axe = 0;
            pick = 0;
            hoe = 0;
            shovel = 0;

            for (ItemStack item : player.getInventory().getContents()) {
                if (item == null || item.getType() == Material.AIR) continue;

                if (item.getType() == Material.IRON_PICKAXE) {
                    if (pick == 1) continue;
                    player.getInventory().removeItem(item);
                    pick++;
                } else if (item.getType() == Material.IRON_AXE) {
                    if (axe == 1) continue;
                    player.getInventory().removeItem(item);
                    axe++;
                } else if (item.getType() == Material.IRON_HOE) {
                    if (hoe == 1) continue;
                    player.getInventory().removeItem(item);
                    hoe++;
                } else if (item.getType() == Material.IRON_SHOVEL) {
                    if (shovel == 1) continue;
                    player.getInventory().removeItem(item);
                    shovel++;
                }
            }

            player.updateInventory();
            tools = -1;
            plugin.getQuestsManager().endActiveQuest(player, true);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return getNpcName() + ";" + tools;
    }
}
