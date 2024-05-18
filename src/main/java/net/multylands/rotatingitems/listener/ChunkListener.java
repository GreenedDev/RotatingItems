package net.multylands.rotatingitems.listener;

import net.multylands.rotatingitems.RotatingItems;
import net.multylands.rotatingitems.util.CorruptedElementsFixer;
import net.multylands.rotatingitems.util.ItemRotator;
import net.multylands.rotatingitems.util.PersistentDataUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

public class ChunkListener implements Listener {
    RotatingItems plugin;

    public ChunkListener(RotatingItems plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        for (Entity entity : event.getChunk().getEntities()) {
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
            System.out.println(":::::::::::::::::::::::::::::::::::::load " + name);
            ItemRotator.restartAnimation(plugin, display, name);
        }
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent event) {
        for (Entity entity : event.getChunk().getEntities()) {

            if (!(entity instanceof ItemDisplay)) {
                System.out.println(14353);
                continue;
            }
            System.out.println(100353);
            if (!PersistentDataUtils.isOurDisplayAccordingToPDC(plugin, entity)) {
                System.out.println(12313);
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
            System.out.println(":::::::::::::::::::::::::::::::::::::unload " + PersistentDataUtils.getRINameOfItemDisplay(plugin, display));
            ItemRotator.stopAnimation(plugin, display);
        }
    }
}
