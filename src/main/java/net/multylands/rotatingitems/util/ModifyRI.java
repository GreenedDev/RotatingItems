package net.multylands.rotatingitems.util;

import net.multylands.rotatingitems.RotatingItems;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public class ModifyRI {
    public static void setSizeOfRI(RotatingItems plugin, String name, float size) {
        UUID displayUUID = UUID.fromString(plugin.dataConfig.getString(name + ".display"));
        ItemDisplay display = (ItemDisplay) Bukkit.getEntity(displayUUID);

        ItemRotator.stopAnimation(plugin, display);

        float speed = (float) plugin.dataConfig.getDouble(name + ".speed");
        String direction = plugin.dataConfig.getString(name + ".direction");

        ItemRotator.startAnimation(plugin, display, speed, size, direction);
        Interaction interaction = PersistentDataUtils.getHitBoxOfDisplay(plugin, display);
        HitBoxUtils.setHitBoxSizeAndLocation(display.getItemStack(), interaction, display.getLocation(), size);

        plugin.dataConfig.set(name + ".size", size);
        plugin.saveDataConfig();
    }

    public static void setRotationOfRI(RotatingItems plugin, String name, String direction) {
        UUID displayUUID = UUID.fromString(plugin.dataConfig.getString(name + ".display"));
        ItemDisplay display = (ItemDisplay) Bukkit.getEntity(displayUUID);

        ItemRotator.stopAnimation(plugin, display);

        float speed = (float) plugin.dataConfig.getDouble(name + ".speed");
        float size = (float) plugin.dataConfig.getDouble(name + ".size");

        ItemRotator.startAnimation(plugin, display, speed, size, direction);
        plugin.dataConfig.set(name + ".direction", direction.toLowerCase());
        plugin.saveDataConfig();
    }

    public static void setItemOfRI(RotatingItems plugin, String name, ItemStack item) {
        UUID displayUUID = UUID.fromString(plugin.dataConfig.getString(name + ".display"));
        ItemDisplay display = (ItemDisplay) Bukkit.getEntity(displayUUID);

        display.setItemStack(item);

        ItemRotator.stopAnimation(plugin, display);

        float speed = (float) plugin.dataConfig.getDouble(name + ".speed");
        float size = (float) plugin.dataConfig.getDouble(name + ".size");
        String direction = plugin.dataConfig.getString(name + ".direction");

        ItemRotator.startAnimation(plugin, display, speed, size, direction);
        plugin.dataConfig.set(name + ".item", item);
        plugin.saveDataConfig();
    }

    public static void setLocationOfRI(RotatingItems plugin, String name, Location location) {
        UUID displayUUID = UUID.fromString(plugin.dataConfig.getString(name + ".display"));
        ItemDisplay display = (ItemDisplay) Bukkit.getEntity(displayUUID);
        location.setYaw(0);
        location.setPitch(0);
        float speed = (float) plugin.dataConfig.getDouble(name + ".speed");
        float size = (float) plugin.dataConfig.getDouble(name + ".size");
        String direction = plugin.dataConfig.getString(name + ".direction");

        display.teleport(location);
        Interaction interaction = PersistentDataUtils.getHitBoxOfDisplay(plugin, display);
        HitBoxUtils.setHitBoxSizeAndLocation(display.getItemStack(), interaction, location, size);

        ItemRotator.stopAnimation(plugin, display);


        ItemRotator.startAnimation(plugin, display, speed, size, direction);
        plugin.dataConfig.set(name + ".location", location);
        plugin.saveDataConfig();
    }

    public static void setSpeedOfRI(RotatingItems plugin, String name, float speed) {
        UUID displayUUID = UUID.fromString(plugin.dataConfig.getString(name + ".display"));
        ItemDisplay display = (ItemDisplay) Bukkit.getEntity(displayUUID);

        ItemRotator.stopAnimation(plugin, display);

        float size = (float) plugin.dataConfig.getDouble(name + ".size");
        String direction = plugin.dataConfig.getString(name + ".direction");

        ItemRotator.startAnimation(plugin, display, speed, size, direction);
        plugin.dataConfig.set(name + ".speed", speed);
        plugin.saveDataConfig();
    }

    public static void addCommandOfRI(RotatingItems plugin, CommandSender sender, String name, String clickCommand, String action, String executor) {
        List<String> existingValues = plugin.dataConfig.getStringList(name + ".commands");
        clickCommand = executor.toUpperCase() + "_COMMAND: " + clickCommand;
        if (action.equalsIgnoreCase("add")) {
            existingValues.add(clickCommand);
            plugin.dataConfig.set(name + ".commands", existingValues);
            Chat.sendMessageSender(sender, plugin.languageConfig.getString("admin.clickcommand.add.success").replace("%name%", name));
        } else {
            if (existingValues.contains(clickCommand)) {
                existingValues.remove(clickCommand);
                plugin.dataConfig.set(name + ".commands", existingValues);
                Chat.sendMessageSender(sender, plugin.languageConfig.getString("admin.clickcommand.remove.success").replace("%name%", name));
            } else {
                Chat.sendMessageSender(sender, plugin.languageConfig.getString("admin.clickcommand.remove.error"));
                return;
            }
        }
        plugin.saveDataConfig();
    }

    public static void deleteRI(RotatingItems plugin, String name) {
        UUID displayUUID = UUID.fromString(plugin.dataConfig.getString(name + ".display"));
        ItemDisplay display = (ItemDisplay) Bukkit.getEntity(displayUUID);

        plugin.dataConfig.set(name, null);
        plugin.saveDataConfig();

        Interaction hitBox = PersistentDataUtils.getHitBoxOfDisplay(plugin, display);

        display.remove();
        hitBox.remove();
    }

    public static void createRI(RotatingItems plugin, Location location, String name, float speed, ItemStack item, float size, String direction) {
        location.setYaw(0);
        location.setPitch(0);

        ItemDisplay entity = location.getWorld().spawn(location, ItemDisplay.class);
        entity.setItemStack(item);
        ItemDisplay display = ItemRotator.startAnimation(plugin, entity, speed, size, direction);

        PersistentDataUtils.setRINameOfItemDisplay(plugin, display, name);

        Interaction hitBox = HitBoxUtils.createHitBoxOfRI(item, location, size);

        ConfigUtils.createRIInConfig(plugin, display, hitBox, name, size, direction, speed, item);

        PersistentDataUtils.setHitBox_sRIName(plugin, hitBox, name);
        PersistentDataUtils.setRIsHitBox(plugin, display, hitBox);
    }
}
