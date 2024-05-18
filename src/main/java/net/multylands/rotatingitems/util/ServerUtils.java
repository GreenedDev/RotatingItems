package net.multylands.rotatingitems.util;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.multylands.rotatingitems.RotatingItems;
import net.multylands.rotatingitems.command.RotatingItemsCommand;
import net.multylands.rotatingitems.command.subcommand.*;
import net.multylands.rotatingitems.command.subcommand.modify.*;
import net.multylands.rotatingitems.command.subcommand.modify.set.SetItemCommand;
import net.multylands.rotatingitems.command.subcommand.modify.set.SetRotationDirectionCommand;
import net.multylands.rotatingitems.command.subcommand.modify.set.SetSizeCommand;
import net.multylands.rotatingitems.command.subcommand.modify.set.SetSpeedCommand;
import net.multylands.rotatingitems.listener.ChunkListener;
import net.multylands.rotatingitems.listener.RIHitBox;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SingleLineChart;

public class ServerUtils {
    public static void registerListeners(RotatingItems plugin) {
        plugin.getServer().getPluginManager().registerEvents(new RIHitBox(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new ChunkListener(plugin), plugin);
        correctDataIfNeeded(plugin);
        //correctdatafirst
        RIListUtils.restartAnimationInLoadedChunks(plugin);

    }


    public static void correctDataIfNeeded(RotatingItems plugin) {
        for (String name : RIListUtils.getListOfRINamesFromDisk(plugin)) {
            CorruptedElementsFixer.isDisplayCorrupted_RemoveItInDataOnly(plugin, name);
        }
    }

    public static void registerCommands(RotatingItems plugin) {
        plugin.getCommand("rotatingitems").setExecutor(new RotatingItemsCommand(plugin));

        RotatingItems.commandExecutors.put("reload", new ReloadCommand(plugin));
        RotatingItems.commandExecutors.put("create", new CreateCommand(plugin));
        RotatingItems.commandExecutors.put("delete", new DeleteCommand(plugin));
        RotatingItems.commandExecutors.put("setsize", new SetSizeCommand(plugin));
        RotatingItems.commandExecutors.put("setspeed", new SetSpeedCommand(plugin));
        RotatingItems.commandExecutors.put("setitem", new SetItemCommand(plugin));
        RotatingItems.commandExecutors.put("setrotationdirection", new SetRotationDirectionCommand(plugin));
        RotatingItems.commandExecutors.put("clickcommands", new ClickCommandsCommand(plugin));
        RotatingItems.commandExecutors.put("move", new MoveCommand(plugin));
        RotatingItems.commandExecutors.put("list", new ListCommand(plugin));
    }

    public static void implementBStats(RotatingItems plugin) {
        Metrics metrics = new Metrics(plugin, 21176);
        metrics.addCustomChart(new SingleLineChart("servers", () -> {
            return 1;
        }));
    }

    public static MiniMessage miniMessage() {
        if (RotatingItems.miniMessage == null) {
            throw new IllegalStateException("miniMessage is null when getting it from the main class");
        }
        return RotatingItems.miniMessage;
    }
}
