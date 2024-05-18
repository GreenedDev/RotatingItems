package net.multylands.rotatingitems.listener;

import net.multylands.rotatingitems.RotatingItems;
import net.multylands.rotatingitems.util.PersistentDataUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.List;
import java.util.logging.Level;

public class RIHitBox implements Listener {
    RotatingItems plugin;
    public RIHitBox(RotatingItems plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onClick(PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof Interaction)) {
            return;
        }
        if (event.getHand() == EquipmentSlot.OFF_HAND) {
            return;
        }
        Player clicker = event.getPlayer();
        Interaction hitBox = (Interaction) event.getRightClicked();
        String RIName = PersistentDataUtils.getHitBox_sRIName(plugin, hitBox);
        if (RIName == null) {
            return;
        }
        List<String> commands = plugin.dataConfig.getStringList(RIName+".commands");
        for (String command : commands) {
            command = command.replace("%player%", clicker.getName());
            if (command.startsWith("PLAYER_COMMAND: ")) {
                command = command.replace("PLAYER_COMMAND: ", "");
                Bukkit.dispatchCommand(clicker, command);
            } else if (command.startsWith("CONSOLE_COMMAND: ")) {
                command = command.replace("CONSOLE_COMMAND: ", "");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
            } else {
                Bukkit.getLogger().log(Level.SEVERE, "command should be starting with PLAYER_COMMAND: or CONSOLE_COMMAND: ");
            }
        }
    }
}
