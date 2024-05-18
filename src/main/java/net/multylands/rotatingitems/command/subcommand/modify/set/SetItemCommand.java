package net.multylands.rotatingitems.command.subcommand.modify.set;


import net.multylands.rotatingitems.RotatingItems;
import net.multylands.rotatingitems.util.Chat;
import net.multylands.rotatingitems.util.ModifyRI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetItemCommand implements CommandExecutor {
    RotatingItems plugin;

    public SetItemCommand(RotatingItems RotatingSkulls) {
        plugin = RotatingSkulls;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("rotatingskulls.admin.setitem")) {
            Chat.sendMessageSender(sender, plugin.languageConfig.getString("no-perm"));
            return false;
        }
        if (!(sender instanceof Player)) {
            Chat.sendMessageSender(sender, plugin.languageConfig.getString("only-player-command"));
            return false;
        }
        Player player = (Player) sender;
        String usage = plugin.languageConfig.getString("command-usage").replace("%command%", label) + " setitem name";
        if (args.length != 1) {
            Chat.sendMessageSender(sender, usage);
            return false;
        }
        String name = args[0];
        if (plugin.dataConfig.getString(name+".display")==null) {
            Chat.sendMessageSender(sender, plugin.languageConfig.getString("admin.doesnt-exist"));
            return false;
        }
        ModifyRI.setItemOfRI(plugin, name, player.getInventory().getItemInMainHand());
        Chat.sendMessageSender(sender, plugin.languageConfig.getString("admin.setitem.success").replace("%name%", name));
        return false;
    }
}