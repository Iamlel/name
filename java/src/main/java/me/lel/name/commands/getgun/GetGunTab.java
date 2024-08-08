package me.lel.name.commands.getgun;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import me.lel.name.Main;
import me.lel.name.Guns.Gun;

public class GetGunTab implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        switch (args.length) {
            case 1:
                return Gun.getGuns().keySet().stream().map(gun -> gun.toString()).collect(Collectors.toList());
            
            case 2:
                return Main.getInstance().getServer().getOnlinePlayers()
                .stream().map(player -> player.getName()).collect(Collectors.toList());
        }
        return null;
    }
}
