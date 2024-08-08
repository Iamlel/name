package me.lel.name.commands.getgun;

import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.lel.name.Main;
import me.lel.name.Guns.Gun;
import me.lel.name.commands.NameCommand;

public class GetGun extends NameCommand implements CommandExecutor {

    public GetGun() {
        super("Usage: /getgun <id> [<player>]");
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = null; Integer id = null;
        if (args.length < 2 && sender instanceof Player) { player = (Player) sender; }
        else if (args.length == 2) { player = Main.getInstance().getServer().getPlayer(args[1]); }

        try { id = Integer.parseInt(args[0]); }
        catch (NumberFormatException e) {}

        if (player == null || id == null || !Gun.getGuns().containsKey(id)) { sendMessage(MessageColor.NONE, usage, sender); }
        else {
            final Map<Integer, ItemStack> map = player.getInventory().addItem(Gun.getGuns().get(id).getItem());

            if (player.equals(sender)) {
                if (map.isEmpty()) { sendMessage(MessageColor.CORRECT, "You have received a gun!", player); }
                else { sendMessage(MessageColor.CORRECT, "You don't have enough room to hold the gun!", player); }

            } else {
                sendMessage(MessageColor.INFO, "You have recevied a gun from " + sender.getName() + "!", player);

                if (map.isEmpty()) { sendMessage(MessageColor.CORRECT, "You have given " + player.getName() + "a gun!", sender); }
                else { 
                    sendMessage(MessageColor.INCORRECT, "You don't have enough room to hold the gun!", player);
                    sendMessage(MessageColor.INCORRECT, player.getName() + " does not have enough room to hold the gun!", sender);
                }
            }
        }
        return true;
    }
}
