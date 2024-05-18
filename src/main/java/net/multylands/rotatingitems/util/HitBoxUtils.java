package net.multylands.rotatingitems.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Interaction;
import org.bukkit.inventory.ItemStack;

public class HitBoxUtils {
    public static Interaction setHitBoxSizeAndLocation(ItemStack item, Interaction interaction, Location location, float size) {
        double newY;
        float height;
        float width;
        Material type = item.getType();
        String typeName = type.toString().toLowerCase();
        System.out.println("xdfence "+typeName);
        if (typeName.contains("fence")) {
            newY = location.getY() - 0.3 * size;
            System.out.println("fence");
            height = (float) 0.89 * size;
            width = (float) 1 * size;
        } else if (typeName.contains("skull") || typeName.contains("head")) {
            if (type.equals(Material.PIGLIN_HEAD)) {
                newY = location.getY() - 0.7 * size;
            } else {
                newY = location.getY() - 0.6 * size;
            }
            height = (float) 0.7 * size;
            width = (float) 0.6 * size;
        } else if (type.isBlock()) {
            height = (float) 1.2 * size;
            width = (float) 1.41 * size;
            newY = location.getY() - 0.6 * size;
        } else {
            newY = location.getY() - 0.5 * size;
            height = 1;
            width = 1;
        }
        location.setY(newY);
        interaction.setInteractionWidth(width);
        interaction.setInteractionHeight(height);
        interaction.teleport(location);
        return interaction;
    }

    public static Interaction createHitBoxOfRI(ItemStack item, Location location, float size) {
        Interaction interaction = (Interaction) location.getWorld().spawnEntity(location, EntityType.INTERACTION);
        interaction.setNoPhysics(true);
        interaction.setGravity(false);
        interaction.setInvulnerable(false);
        interaction.setSilent(true);
        setHitBoxSizeAndLocation(item, interaction, location, size);

        return interaction;
    }
}
