package net.multylands.rotatingitems.util;

import net.multylands.rotatingitems.RotatingItems;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class CorruptedElementsFixer {
    public static boolean isDisplayCorrupted_RemoveItInDataOnly(RotatingItems plugin, String name) {
        try {
            String displayUUID = plugin.dataConfig.getString(name + ".display");
            if (ifIsNullRemoveData(plugin, displayUUID, name)) {
                System.out.println(1);
                return true;
            }
            String hitBoxUUID = plugin.dataConfig.getString(name + ".hitbox");
            if (ifIsNullRemoveData(plugin, displayUUID, name)) {
                System.out.println(2);
                return true;
            }
            Location location = plugin.dataConfig.getLocation(name + ".location");
            if (ifIsNullRemoveData(plugin, location, name)) {
                System.out.println(9);
                return true;
            }
            float size = (float) plugin.dataConfig.getDouble(name + ".size");
            if (size == 0) {
                System.out.println(5);
                ConfigUtils.removeRIFromConfig(plugin, name);
                return true;
            }
            float speed = (float) plugin.dataConfig.getDouble(name + ".speed");
            if (speed == 0) {
                ConfigUtils.removeRIFromConfig(plugin, name);
                System.out.println(6);
                return true;
            }
            ItemStack item = plugin.dataConfig.getItemStack(name + ".item");
            if (ifIsNullRemoveData(plugin, item, name)) {
                System.out.println(7);
                return true;
            }
            String direction = plugin.dataConfig.getString(name + ".direction");
            if (ifIsNullRemoveData(plugin, direction, name)) {
                System.out.println(8);
                return true;
            }
            location.getChunk().load();
            ItemDisplay display = (ItemDisplay) Bukkit.getEntity(UUID.fromString(displayUUID));
            if (display == null) {
                ModifyRI.createRI(plugin, location, name, speed, item, size, direction);
                return true;
            }
            Entity hitBox = Bukkit.getEntity(UUID.fromString(hitBoxUUID));
            if (ifIsNullRemoveData(plugin, hitBox, name)) {
                display.remove();
                display.setPersistent(false);
                ModifyRI.createRI(plugin, location, name, speed, item, size, direction);
                return true;
            }

            return (plugin.dataConfig.getString(name + ".display") == null);
        } catch (Exception e) {
            plugin.dataConfig.set(name, null);
            plugin.saveDataConfig();
            return true;
        }
    }

    public static boolean ifIsNullRemoveData(RotatingItems plugin, Object object, String name) {
        if (object != null) {
            return false;
        }
        ConfigUtils.removeRIFromConfig(plugin, name);
        return true;
    }
}
