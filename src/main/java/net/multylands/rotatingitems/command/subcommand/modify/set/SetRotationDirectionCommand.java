package net.multylands.rotatingitems.command.subcommand.modify.set;


import net.multylands.rotatingitems.RotatingItems;
import net.multylands.rotatingitems.util.Chat;
import net.multylands.rotatingitems.util.ModifyRI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SetRotationDirectionCommand implements CommandExecutor {
    RotatingItems plugin;

    public SetRotationDirectionCommand(RotatingItems RotatingSkulls) {
        plugin = RotatingSkulls;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("rotatingskulls.admin.setrotationdirection")) {
            Chat.sendMessageSender(sender, plugin.languageConfig.getString("no-perm"));
            return false;
        }
        String usage = plugin.languageConfig.getString("command-usage").replace("%command%", label) + " setrotationdirection name clockwise/counterclockwise";
        if (args.length != 2) {
            Chat.sendMessageSender(sender, usage);
            return false;
        }
        String name = args[0];
        String direction = args[1];
        if (!(direction.equalsIgnoreCase("clockwise") || direction.equalsIgnoreCase("counterclockwise") )) {
            Chat.sendMessageSender(sender, usage);
            return false;
        }
        if (plugin.dataConfig.getString(name+".display")==null) {
            Chat.sendMessageSender(sender, plugin.languageConfig.getString("admin.doesnt-exist"));
            return false;
        }
        ModifyRI.setRotationOfRI(plugin, name, direction);
        Chat.sendMessageSender(sender, plugin.languageConfig.getString("admin.setrotationdirection.success").replace("%name%", name));
        return false;
    }
}