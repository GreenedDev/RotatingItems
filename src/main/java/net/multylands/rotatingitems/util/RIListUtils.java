package net.multylands.rotatingitems.util;

import net.multylands.rotatingitems.RotatingItems;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class RIListUtils {
    public static void stopAllAnimations(RotatingItems plugin) {
        for (Entity entity : removeCorruptedDisplaysFromLoadedChunksAndGetList(plugin)) {
            ItemDisplay display = (ItemDisplay) entity;
            ItemRotator.stopAnimation(plugin, display);
        }
    }

    public static List<Entity> removeCorruptedDisplaysFromLoadedChunksAndGetList(RotatingItems plugin) {
        List<Entity> result = new ArrayList<>();
        for (World world : Bukkit.getWorlds()) {
            for (Chunk chunk : world.getLoadedChunks()) {
                for (Entity entity : chunk.getEntities()) {
                    if (!(entity instanceof ItemDisplay)) {
                        continue;
                    }
                    if (!PersistentDataUtils.isOurDisplayAccordingToPDC(plugin, entity)) {
                        continue;
                    }
                    ItemDisplay display = (ItemDisplay) entity;
                    String name = PersistentDataUtils.getRINameOfItemDisplay(plugin, display);
                    if (CorruptedElementsFixer.isDisplayCorrupted_RemoveItInDataOnly(plugin, name)) {
                        Interaction hitBox = PersistentDataUtils.getHitBoxOfDisplay(plugin, display);
                        display.remove();
                        display.setPersistent(false);
                        hitBox.remove();
                        hitBox.setPersistent(false);
                        continue;
                    }
                    result.add(entity);
                }
            }
        }
        return result;
    }

    public static void restartAnimationInLoadedChunks(RotatingItems plugin) {
        for (Entity entity : removeCorruptedDisplaysFromLoadedChunksAndGetList(plugin)) {
            ItemDisplay display = (ItemDisplay) entity;
            String name = PersistentDataUtils.getRINameOfItemDisplay(plugin, display);
            ItemStack item = plugin.dataConfig.getItemStack(name + ".item");
            display.setItemStack(item);
            System.out.println("sssssssssssssssssssssssssssssssssssssssssssss1 " + name);
            ItemRotator.restartAnimation(plugin, display, name);
        }
    }

    public static Set<String> getListOfRINamesFromDisk(RotatingItems plugin) {
        Set<String> result = new HashSet<>();
        for (String name : plugin.dataConfig.getKeys(false)) {
            result.add(name);
        }
        return result;
    }
}
