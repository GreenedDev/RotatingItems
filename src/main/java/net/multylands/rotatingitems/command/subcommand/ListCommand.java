package net.multylands.rotatingitems.command.subcommand;


import net.multylands.rotatingitems.RotatingItems;
import net.multylands.rotatingitems.util.Chat;
import net.multylands.rotatingitems.util.RIListUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Set;

public class ListCommand implements CommandExecutor {
    RotatingItems plugin;

    public ListCommand(RotatingItems RotatingSkulls) {
        plugin = RotatingSkulls;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("rotatingskulls.admin.list")) {
            Chat.sendMessageSender(sender, plugin.languageConfig.getString("no-perm"));
            return false;
        }
        String usage = plugin.languageConfig.getString("command-usage").replace("%command%", label) + " list";
        if (args.length != 0) {
            Chat.sendMessageSender(sender, usage);
            return false;
        }
        Set<String> listOfRIs = RIListUtils.getListOfRINamesFromDisk(plugin);
        if (listOfRIs.isEmpty()) {
            Chat.sendMessageSender(sender, plugin.languageConfig.getString("admin.list.no-items"));
            return false;
        }
        Chat.sendMessageSender(sender, plugin.languageConfig.getString("admin.list.top-message"));
        for (String name : RIListUtils.getListOfRINamesFromDisk(plugin)) {
            Chat.sendMessageSender(sender, plugin.languageConfig.getString("admin.list.format").replace("%name%", name));
        }
        return false;
    }
}