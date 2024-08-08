package me.lel.name.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import net.kyori.adventure.text.Component;

public class Chat {

    public static String serverString = "&f[&5&lServer&r&f] ";

    public static Component getComponent(String message) {
        return Component.text(ChatColor.translateAlternateColorCodes('&', message));
    }

    public static void broadcastMessage(String message) {
        Bukkit.getServer().broadcast(getComponent(serverString + message));
    }
}
