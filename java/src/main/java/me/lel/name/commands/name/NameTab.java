package me.lel.name.commands.name;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class NameTab implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        switch (args.length) {
            case 1:
                return List.of("reload");

            case 2:
                return List.of("config", "guns");

            default:
                return null;
        }
    }  
}
