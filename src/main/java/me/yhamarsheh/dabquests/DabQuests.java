package me.yhamarsheh.dabquests;

import me.yhamarsheh.dabquests.commands.QuestCMD;
import me.yhamarsheh.dabquests.listeners.JoinListener;
import me.yhamarsheh.dabquests.managers.PlayersManager;
import me.yhamarsheh.dabquests.managers.QuestsManager;
import me.yhamarsheh.dabquests.storage.SQLDatabase;
import org.bukkit.plugin.java.JavaPlugin;

public class DabQuests extends JavaPlugin {

    private QuestsManager questsManager;
    private PlayersManager playersManager;
    private SQLDatabase sqlDatabase;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        questsManager = new QuestsManager(this);
        playersManager = new PlayersManager(this);
        sqlDatabase = new SQLDatabase(this);

        new QuestCMD(this);
        new JoinListener(this);
    }

    public void onDisable() {
        playersManager.disable();
    }

    public QuestsManager getQuestsManager() {
        return questsManager;
    }


    public SQLDatabase getSqlDatabase() {
        return sqlDatabase;
    }

    public PlayersManager getPlayersManager() {
        return playersManager;
    }
}
