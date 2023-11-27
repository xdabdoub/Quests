package me.yhamarsheh.dabquests.utilities;

import net.md_5.bungee.api.ChatColor;

public class ChatUtils {

    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
