package me.yhamarsheh.dabquests.storage.objects;

import me.yhamarsheh.dabquests.DabQuests;
import me.yhamarsheh.dabquests.objects.Quest;
import me.yhamarsheh.dabquests.storage.SQLDatabase;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DabPlayer {

    private UUID uuid;
    private DabQuests plugin;
    private SQLDatabase sql;
    private ArrayList<Quest<?>> quests;

    private Player player;

    private boolean newPlayer;

    public DabPlayer(DabQuests plugin, OfflinePlayer player) {
        this.plugin = plugin;
        this.uuid = player.getUniqueId();
        this.sql = plugin.getSqlDatabase();
        this.quests = new ArrayList<>();

        Bukkit.getScheduler().runTaskAsynchronously(plugin, this::create);
    }

    public DabPlayer(DabQuests plugin, UUID uuid) {
        this.plugin = plugin;
        this.uuid = uuid;
        this.sql = plugin.getSqlDatabase();
        this.quests = new ArrayList<>();

        Bukkit.getScheduler().runTaskAsynchronously(plugin, this::create);
    }

    public void create() {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            UUID uuid = this.uuid;
            connection = sql.getConnection();
            if (!sql.exists(uuid, "quests_data")) {
                statement = connection.prepareStatement("INSERT IGNORE INTO quests_data (UUID, QUESTS)" +
                        "VALUES ('" + uuid + "'," + "'[]')");
                statement.executeUpdate();
                newPlayer = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            sql.close(connection, statement, null);
        }
    }

    public void reload() {
        if (newPlayer) return;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = sql.getConnection();
            statement = connection.prepareStatement("SELECT * FROM quests_data WHERE UUID=?");
            statement.setString(1, this.uuid.toString());
            rs = statement.executeQuery();

            if (rs.next()) {
                String s = rs.getString("QUESTS");
                String data1 = s.replace("[", "");
                String data2 = data1.replace("]", "");
                if (data2.equals("")) return;

                String[] quests = data2.split(",");

                for (String quest : quests) {
                    String[] data = quest.split(";");
                    String name = data[0];
                    int done = Integer.parseInt(data[1]);
                    Quest<?> dQuest = plugin.getQuestsManager().getQuestByName(player, name, done);
                    this.quests.add(dQuest);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            sql.close(connection, statement, rs);
        }
    }

    public void saveData() {
        StringBuilder questBuilder = new StringBuilder("[");
        for (Quest<?> quest : quests) {
            questBuilder.append(quest.toString());
            if (quests.indexOf(quest) == quests.size() - 1) {
                questBuilder.append("]");
                break;
            }
            questBuilder.append(",");
        }

        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = sql.getConnection();
            statement = connection.prepareStatement(
                    "REPLACE INTO quests_data (UUID, QUESTS) VALUES " +
                            "('" + uuid + "','"
                            + questBuilder.toString() + "')"
            );

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            sql.close(connection, statement, null);
        }
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Quest<?>> getQuests() {
        return quests;
    }

}
