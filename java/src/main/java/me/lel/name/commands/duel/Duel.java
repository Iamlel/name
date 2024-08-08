package me.lel.name.commands.duel;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.lel.name.commands.NameCommand;

public class Duel extends NameCommand implements CommandExecutor {

    public Duel() {
        super("Usage: /duel <player>");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 1) {
                if (Bukkit.getPlayer(args[0]) != null) {
                    // teleport both players to arena
                }
            }
        }
        sendMessage(MessageColor.NONE, usage, sender);
        return true;
    }
    
}
