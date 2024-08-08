package me.lel.name.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.lel.name.utils.Chat;

public abstract class NameCommand {
    
    protected final String permission = MessageColor.INCORRECT.getColor()  + "You do not have permission to use this command.";
    protected final String usage;

    public NameCommand(String usage) {
        this.usage = MessageColor.INCORRECT.getColor() + usage;
    }

    protected enum MessageColor {
        INCORRECT("&c"),
        CORRECT("&a"),
        NONE(""),
        INFO("&6");
        
        private final String color;

        private MessageColor(String color) {
            this.color = color;
        }

        public String getColor() {
            return color;
        }
    }

    protected static void sendMessage(MessageColor color, String message, CommandSender sender) {
        message = ChatColor.translateAlternateColorCodes('&', Chat.serverString + color.getColor() + message);
        if (sender instanceof Player) {
            sender.sendMessage(message);
        } else {
            sender.sendMessage(ChatColor.stripColor(message));
        }
    }
}
