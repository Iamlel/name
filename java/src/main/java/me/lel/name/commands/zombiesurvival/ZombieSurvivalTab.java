package me.lel.name.commands.zombiesurvival;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class ZombieSurvivalTab implements TabCompleter {
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (sender.isOp()) {
            if (args.length == 1) { 
                return List.of("start", "stop", "show");
            } else if (args.length == 2 && args[0].equals("stop")) {
                List<String> keys = new ArrayList<String>(ZombieSurvival.getWaves().keySet());
                keys.add("all");
                return keys;
            }
        }
        return null;
    }
}
