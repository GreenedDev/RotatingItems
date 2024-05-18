package net.multylands.rotatingitems.command;

import net.multylands.rotatingitems.RotatingItems;
import net.multylands.rotatingitems.util.Chat;
import net.multylands.rotatingitems.util.RIListUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class RotatingItemsCommand implements CommandExecutor, TabCompleter {
    public RotatingItems plugin;

    public RotatingItemsCommand(RotatingItems plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0 || RotatingItems.commandExecutors.get(args[0]) == null) {
            for (String message : plugin.languageConfig.getStringList("admin.help")) {
                sender.sendMessage(Chat.color(message));
            }
            return false;
        }
        CommandExecutor executor = RotatingItems.commandExecutors.get(args[0]);
        executor.onCommand(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> tabCompleteStrings = new ArrayList<>();
        if (args[0].equalsIgnoreCase("delete")
                || args[0].equalsIgnoreCase("move")
                || args[0].equalsIgnoreCase("setitem")
                || args[0].equalsIgnoreCase("setrotationdirection")
                || args[0].equalsIgnoreCase("setsize")
                || args[0].equalsIgnoreCase("setspeed")) {
            if (args.length == 2) {
                doRINamesStuff(plugin, args, tabCompleteStrings);
            }
        }
        if (args[0].equalsIgnoreCase("clickcommands")) {
            //clickcommand name add/remove console/player
            if (args.length == 2) {
                doRINamesStuff(plugin, args, tabCompleteStrings);
            }
            if (args.length == 3) {
                List<String> actions = new ArrayList<>();
                actions.add("add");
                actions.add("remove");
                for (String action : actions) {
                    if (!action.startsWith(args[2])) {
                        continue;
                    }
                    if (action.equalsIgnoreCase(args[2])) {
                        continue;
                    }
                    tabCompleteStrings.add(action);
                }
            }
            if (args.length == 4) {
                List<String> executors = new ArrayList<>();
                executors.add("player");
                executors.add("console");
                for (String executor : executors) {
                    if (!executor.startsWith(args[3])) {
                        continue;
                    }
                    if (executor.equalsIgnoreCase(args[3])) {
                        continue;
                    }
                    tabCompleteStrings.add(executor);
                }
            }
        } else {
            for (String commandFromLoop : RotatingItems.commandExecutors.keySet()) {
                if (!commandFromLoop.startsWith(args[0])) {
                    continue;
                }
                if (commandFromLoop.equalsIgnoreCase(args[0])) {
                    continue;
                }
                tabCompleteStrings.add(commandFromLoop);
            }
        }
        return tabCompleteStrings;
    }
    public static void doRINamesStuff(RotatingItems plugin, String[] args, List<String> tabCompleteStrings) {
        Set<String> RINames = RIListUtils.getListOfRINamesFromDisk(plugin);
        for (String name : RINames) {
            if (!name.startsWith(args[1])) {
                continue;
            }
            if (name.equalsIgnoreCase(args[1])) {
                continue;
            }
            tabCompleteStrings.add(name);
        }
    }
}
