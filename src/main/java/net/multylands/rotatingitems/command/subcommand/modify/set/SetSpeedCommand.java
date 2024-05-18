package net.multylands.rotatingitems.command.subcommand.modify.set;


import net.multylands.rotatingitems.RotatingItems;
import net.multylands.rotatingitems.util.Chat;
import net.multylands.rotatingitems.util.ModifyRI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SetSpeedCommand implements CommandExecutor {
    RotatingItems plugin;

    public SetSpeedCommand(RotatingItems RotatingSkulls) {
        plugin = RotatingSkulls;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("rotatingskulls.admin.setspeed")) {
            Chat.sendMessageSender(sender, plugin.languageConfig.getString("no-perm"));
            return false;
        }
        String usage = plugin.languageConfig.getString("command-usage").replace("%command%", label) + " setspeed name speed(number)";
        if (args.length != 2) {
            Chat.sendMessageSender(sender, usage);
            return false;
        }
        String name = args[0];
        int size;
        try {
            size = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            Chat.sendMessageSender(sender, usage);
            return false;
        }
        if (plugin.dataConfig.getString(name+".display")==null) {
            Chat.sendMessageSender(sender, plugin.languageConfig.getString("admin.doesnt-exist"));
            return false;
        }
        ModifyRI.setSpeedOfRI(plugin, name, size);
        Chat.sendMessageSender(sender, plugin.languageConfig.getString("admin.setspeed.success").replace("%name%", name));
        return false;
    }
}