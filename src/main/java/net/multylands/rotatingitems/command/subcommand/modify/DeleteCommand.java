package net.multylands.rotatingitems.command.subcommand.modify;


import net.multylands.rotatingitems.RotatingItems;
import net.multylands.rotatingitems.util.Chat;
import net.multylands.rotatingitems.util.ModifyRI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DeleteCommand implements CommandExecutor {
    RotatingItems plugin;

    public DeleteCommand(RotatingItems RotatingSkulls) {
        plugin = RotatingSkulls;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("rotatingskulls.admin.delete")) {
            Chat.sendMessageSender(sender, plugin.languageConfig.getString("no-perm"));
            return false;
        }
        String usage = plugin.languageConfig.getString("command-usage").replace("%command%", label) + " delete name";
        if (args.length != 1) {
            Chat.sendMessageSender(sender, usage);
            return false;
        }
        String name = args[0];
        if (plugin.dataConfig.getString(name+".display")==null) {
            Chat.sendMessageSender(sender, plugin.languageConfig.getString("admin.doesnt-exist"));
            return false;
        }
        ModifyRI.deleteRI(plugin, args[0]);
        Chat.sendMessageSender(sender, plugin.languageConfig.getString("admin.delete.success").replace("%name%", args[0]));
        return false;
    }
}