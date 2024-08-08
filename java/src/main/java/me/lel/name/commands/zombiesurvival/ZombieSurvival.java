package me.lel.name.commands.zombiesurvival;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import me.lel.name.Main;
import me.lel.name.Waves.Waves;
import me.lel.name.commands.NameCommand;

public class ZombieSurvival extends NameCommand implements CommandExecutor {
    
    private static Map<String, Waves> waves = new HashMap<String, Waves>();

    public ZombieSurvival() {
        super("Usage: /zombiesurvival <start <name> <location x> <location y> <location z> <location x-2> <location z-2>>/stop <name>/show>");
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (player.isOp()) {
                if (args.length > 0) {
                    switch (args[0]) {
                        case "start":
                            if (args.length > 6) {
                                String name = args[1];
                                if (waves.containsKey(name)) {
                                    sendMessage(MessageColor.INCORRECT, "A wave with that name already exists!", sender);
                                    return true;
                                }

                                double[] locs = new double[args.length - 1];
                                for (int i = 2; i <= 6; i++) {
                                    try {
                                        locs[i - 2] = (Double.parseDouble(args[i]));
                                    } catch (NumberFormatException e) {
                                        sendMessage(MessageColor.INCORRECT, "That is not a valid location!", sender);
                                    }
                                }
                                Waves wave = new Waves(locs, player.getWorld(), name);
                                waves.put(name, wave);
                                wave.startWave();
                                return true;
                            }

                        case "stop":
                            String tag = null;
                            NamespacedKey key = NamespacedKey.fromString("ownerid", Main.getInstance());
                            
                            if (args[1].equals("all")) {
                                waves.clear();
                            } else {
                                try {
                                    waves.remove(args[1]);
                                    tag = args[1];
                                } catch (Exception e) {
                                    sendMessage(MessageColor.INCORRECT, "That wave does not exist!", sender);
                                    return true;
                                }
                            }

                            for (World world : Bukkit.getWorlds()) {
                                for (Entity entity : world.getEntities()) {
                                    if (entity.getPersistentDataContainer().has(key)) {
                                        if (tag != null) {
                                            if (!entity.getPersistentDataContainer().get(key, PersistentDataType.STRING).equals(tag)) { continue; }
                                        }
                                        entity.remove();
                                    }
                                }
                            }
                            return true;

                        case "show":
                            sendMessage(MessageColor.INFO, "Waves: " + waves.keySet().toString(), sender);
                            return true;
                    }
                }

            } else {
                sendMessage(MessageColor.NONE, permission, player);
                return true;
            }

        } else {
            sendMessage(MessageColor.INCORRECT, "You must be a player to use this command.", sender);
            return true;
        }

        sendMessage(MessageColor.NONE,usage, sender);
        return true;
    }

    public static Map<String, Waves> getWaves() {
        return waves;
    }
}
