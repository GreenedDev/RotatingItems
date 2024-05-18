package net.multylands.rotatingitems.command.subcommand.modify;


import net.multylands.rotatingitems.RotatingItems;
import net.multylands.rotatingitems.util.Chat;
import net.multylands.rotatingitems.util.ModifyRI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ClickCommandsCommand implements CommandExecutor {
    RotatingItems plugin;

    public ClickCommandsCommand(RotatingItems RotatingSkulls) {
        plugin = RotatingSkulls;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("rotatingskulls.admin.modifyclickcommand")) {
            Chat.sendMessageSender(sender, plugin.languageConfig.getString("no-perm"));
            return false;
        }
        String usage = plugin.languageConfig.getString("command-usage").replace("%command%", label) + " clickcommand name add/remove console/player \"command\"";
        if (!(args.length > 4)) {
            Chat.sendMessageSender(sender, usage);
            return false;
        }
        String name = args[0];
        String action = args[1];
        String executor = args[2];
        String clickCommand = "";
        for (int i =0; i<=args.length-1; i++) {
            if (i==0 || i==1 || i==2) {
                continue;
            }
            clickCommand = clickCommand +" " +args[i];
        }
        clickCommand = clickCommand.substring(1);
        if (plugin.dataConfig.getString(name+".display")==null) {
            Chat.sendMessageSender(sender, plugin.languageConfig.getString("admin.doesnt-exist"));
            return false;
        }
        if (!(executor.equalsIgnoreCase("player") || executor.equalsIgnoreCase("console"))) {
            Chat.sendMessageSender(sender, usage);
            return false;
        }
        if (!(action.equalsIgnoreCase("add") || action.equalsIgnoreCase("remove"))) {
            Chat.sendMessageSender(sender, usage);
            return false;
        }
        ModifyRI.addCommandOfRI(plugin, sender, name, clickCommand, action, executor);
        return false;
    }
}