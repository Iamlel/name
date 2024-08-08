package me.lel.name.commands.name;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.lel.name.Main;
import me.lel.name.Guns.LoadGuns;
import me.lel.name.commands.NameCommand;

public class Name extends NameCommand implements CommandExecutor {

    public Name() {
        super("Usage: /name <reload> <guns/config>");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (!player.isOp()) {
                sendMessage(MessageColor.NONE, permission, player);
                return true;
            }
        } 

        if (args.length >= 2) {
            switch (args[0]) {
                case "reload":
                    Main main = Main.getInstance();
                    switch (args[1]) {
                        case "config":
                            main.getMyConfig().reloadFile();
                            sendMessage(MessageColor.CORRECT, "Config reloaded", sender);
                            return true;

                        case "guns":
                            main.getGuns().reloadFile();
                            LoadGuns.load();
                            sendMessage(MessageColor.CORRECT, "&aGuns reloaded", sender);
                            return true;
                    }
            }
        }
        sendMessage(MessageColor.NONE, usage, sender);
        return true;
    }
}