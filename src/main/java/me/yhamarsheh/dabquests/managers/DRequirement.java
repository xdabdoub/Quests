package me.yhamarsheh.dabquests.managers;

import me.yhamarsheh.dabquests.objects.Quest;
import org.bukkit.entity.Player;

public class DRequirement {

    private Requirement requirement;
    private Object object;

    public DRequirement(Requirement requirement, Object object) {
        this.requirement = requirement;
        this.object = object;
    }

    public boolean isMet(Player player) {
        switch (requirement) {
            case PLAYERS:
                return true;
            case PERMISSION:
                return player.hasPermission((String) object);
            case PRIOR_QUEST:
            default:
                return false;
        }
    }


    public enum Requirement {
        PLAYERS(Integer.class, "Not enough players."),
        PERMISSION(String.class, "You don't have access to this quest."),
        PRIOR_QUEST(Quest.class, "You must finish");

        final Class<?> type;
        final String errorMessage;
        Requirement(Class<?> type, String errorMessage) {
            this.type = type;
            this.errorMessage = errorMessage;
        }

        public Class<?> getType() {
            return type;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }

}
