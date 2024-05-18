package net.multylands.rotatingitems.util;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Chat {
    public static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static void sendMessage(Player player, String message) {
        if (message.startsWith("$")) {
            Component parsed = ServerUtils.miniMessage().deserialize(message.substring(1));
            player.sendMessage(parsed);
        } else {
            player.sendMessage(color(message));
        }
    }
    public static void sendMessages(Player player, List<String> messages) {
        for (String message : messages) {
            sendMessage(player, message);
        }
    }

    public static void sendMessageSender(CommandSender sender, String message) {
        if (message.startsWith("$")) {
            Component parsed = ServerUtils.miniMessage().deserialize(message.substring(1));
            sender.sendMessage(parsed);
        } else {
            sender.sendMessage(color(message));
        }
    }
    public static void sendMessagesSender(CommandSender sender, List<String> messages) {
        for (String message : messages) {
            sendMessageSender(sender, message);
        }
    }
}
