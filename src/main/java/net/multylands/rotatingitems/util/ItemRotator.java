package net.multylands.rotatingitems.util;

import net.multylands.rotatingitems.RotatingItems;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.util.Transformation;
import org.joml.Math;

public class ItemRotator {

    public static ItemDisplay startAnimation(RotatingItems plugin, ItemDisplay display, float speedMultiplier, float size, String direction) {
        //must use joml math
        display.setInterpolationDelay(0);
        display.setInterpolationDuration(1);
        float stepSize = 2.87f * speedMultiplier; // Sync to Minecraft rotation animation speed.
        float radianOfStepSize = Math.toRadians(stepSize);
        int taskID = Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
            float angle = 0;

            @Override
            public void run() {
                Transformation transformation = display.getTransformation();
                transformation.getScale().set(size);
                if (direction.equalsIgnoreCase("clockwise")) {
                    transformation.getRightRotation().rotateY(-radianOfStepSize);
                } else {
                    transformation.getRightRotation().rotateY(radianOfStepSize);
                }
                transformation.getTranslation().set(0, (Math.cos(Math.toRadians(angle)) / 10)*size, 0);

                display.setTransformation(transformation);

                if ((angle += stepSize * 2) >= 360) {
                    angle %= 360;
                }
            }

        }, 2, 1).getTaskId();
        PersistentDataUtils.setRIsAnimationTaskID(plugin, display, taskID);
        return display;
    }
    public static void restartAnimation(RotatingItems plugin, ItemDisplay entity, String name) {
        float size = (float) plugin.dataConfig.getDouble(name + ".size");
        float speed = (float) plugin.dataConfig.getDouble(name + ".speed");
        String direction = plugin.dataConfig.getString(name + ".direction");
        Location location = plugin.dataConfig.getLocation(name + ".location");
        location.setYaw(0);
        location.setPitch(0);

        startAnimation(plugin, entity, speed, size, direction);
    }
    public static void stopAnimation(RotatingItems plugin, ItemDisplay display) {
        int taskID = PersistentDataUtils.getRIsAnimationTaskID(plugin, display);
        Bukkit.getScheduler().cancelTask(taskID);
    }
}
