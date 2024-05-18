package net.multylands.rotatingitems.util;

import net.multylands.rotatingitems.RotatingItems;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class PersistentDataUtils {
    public static String RI_NAME_KEY = "ri_name";
    public static String HitBox_UUID_Key = "hitbox_uuid";
    public static String ANIMATION_TASK_ID_KEY = "animation_task_id";
    public static Interaction getHitBoxOfDisplay(RotatingItems plugin, ItemDisplay display) {
        NamespacedKey key = new NamespacedKey(plugin, HitBox_UUID_Key);
        String hitBoxUUID = display.getPersistentDataContainer().get(key, PersistentDataType.STRING);
        Interaction hitBox = (Interaction) Bukkit.getEntity(UUID.fromString(hitBoxUUID));
        return hitBox;
    }
    public static void setRIsHitBox(RotatingItems plugin, ItemDisplay display, Interaction hitBox) {
        NamespacedKey key = new NamespacedKey(plugin, HitBox_UUID_Key);
        display.getPersistentDataContainer().set(key, PersistentDataType.STRING, hitBox.getUniqueId().toString());
    }
    public static String getHitBox_sRIName(RotatingItems plugin, Interaction hitBox) {
        NamespacedKey key = new NamespacedKey(plugin, RI_NAME_KEY);
        return hitBox.getPersistentDataContainer().get(key, PersistentDataType.STRING);
    }
    public static void setHitBox_sRIName(RotatingItems plugin, Interaction hitBox, String name) {
        NamespacedKey key = new NamespacedKey(plugin, RI_NAME_KEY);
        hitBox.getPersistentDataContainer().set(key, PersistentDataType.STRING, name);
    }
    public static boolean isOurDisplayAccordingToPDC(RotatingItems plugin, Entity display) {
        NamespacedKey key = new NamespacedKey(plugin, HitBox_UUID_Key);
        String RIName = display.getPersistentDataContainer().get(key, PersistentDataType.STRING);
        return (RIName != null);
    }
    public static void setRIsAnimationTaskID(RotatingItems plugin, ItemDisplay display, int id) {
        NamespacedKey key = new NamespacedKey(plugin, ANIMATION_TASK_ID_KEY);
        display.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, id);
    }
    public static int getRIsAnimationTaskID(RotatingItems plugin, ItemDisplay display) {
        NamespacedKey key = new NamespacedKey(plugin, ANIMATION_TASK_ID_KEY);
        return display.getPersistentDataContainer().get(key, PersistentDataType.INTEGER);
    }
    public static void setRINameOfItemDisplay(RotatingItems plugin, ItemDisplay display, String name) {
        NamespacedKey key = new NamespacedKey(plugin, RI_NAME_KEY);
        display.getPersistentDataContainer().set(key, PersistentDataType.STRING, name);
    }
    public static String getRINameOfItemDisplay(RotatingItems plugin, ItemDisplay display) {
        NamespacedKey key = new NamespacedKey(plugin, RI_NAME_KEY);
        return display.getPersistentDataContainer().get(key, PersistentDataType.STRING);
    }
}
