package net.multylands.rotatingitems.command.subcommand;

import net.multylands.rotatingitems.RotatingItems;
import net.multylands.rotatingitems.util.*;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ItemDisplay;

public class ReloadCommand implements CommandExecutor {
    RotatingItems plugin;

    public ReloadCommand(RotatingItems RotatingSkulls) {
        plugin = RotatingSkulls;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("rotatingskulls.admin.reload")) {
            Chat.sendMessageSender(sender, plugin.languageConfig.getString("no-perm"));
            return false;
        }
        if (args.length != 0) {
            Chat.sendMessageSender(sender, plugin.languageConfig.getString("command-usage").replace("%command%", label) + " reload");
            return false;
        }
        plugin.reloadDataConfig();
        plugin.reloadConfig();
        plugin.reloadLanguageConfig();
        ServerUtils.correctDataIfNeeded(plugin);

        RIListUtils.stopAllAnimations(plugin);
        RIListUtils.restartAnimationInLoadedChunks(plugin);
        Chat.sendMessageSender(sender, plugin.languageConfig.getString("admin.reload.all-config-reloaded"));
        return false;
    }
}
