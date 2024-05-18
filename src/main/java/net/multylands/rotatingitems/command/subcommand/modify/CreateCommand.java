package net.multylands.rotatingitems.command.subcommand.modify;


import net.multylands.rotatingitems.RotatingItems;
import net.multylands.rotatingitems.util.Chat;
import net.multylands.rotatingitems.util.ModifyRI;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CreateCommand implements CommandExecutor {
    RotatingItems plugin;

    public CreateCommand(RotatingItems RotatingSkulls) {
        plugin = RotatingSkulls;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("rotatingskulls.admin.create")) {
            Chat.sendMessageSender(sender, plugin.languageConfig.getString("no-perm"));
            return false;
        }
        if (!(sender instanceof Player)) {
            Chat.sendMessageSender(sender, plugin.languageConfig.getString("only-player-command"));
            return false;
        }
        Player player = (Player) sender;
        String usage = plugin.languageConfig.getString("command-usage").replace("%command%", label) + " create name";
        if (args.length != 1) {
            Chat.sendMessageSender(sender, usage);
            return false;
        }
        String name = args[0];
        if (plugin.dataConfig.getString(name+".display")!=null) {
            Chat.sendMessageSender(sender, plugin.languageConfig.getString("admin.create.already-exists"));
            return false;
        }
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item == null || item.getType() == Material.AIR) {
            Chat.sendMessageSender(sender, plugin.languageConfig.getString("admin.create.no-item-in-main-hand"));
            return false;
        }
        Float speed = (float) plugin.getConfig().getDouble("default-values.speed");
        Float size = (float) plugin.getConfig().getDouble("default-values.size");
        String direction = plugin.getConfig().getString("default-values.direction");
        if (!(direction.equalsIgnoreCase("clockwise") || direction.equalsIgnoreCase("counterclockwise"))) {
            Chat.sendMessageSender(sender, "&cInvalid default direction value (config.yml)");
            return false;
        }
        ModifyRI.createRI(plugin, player.getLocation(), args[0], speed, item, size, direction);
        Chat.sendMessageSender(sender, plugin.languageConfig.getString("admin.create.success").replace("%name%", args[0]));
        return false;
    }
}