package net.multylands.rotatingitems.util;

import net.multylands.rotatingitems.RotatingItems;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ConfigUtils {
    RotatingItems plugin;

    public ConfigUtils(RotatingItems plugin) {
        this.plugin = plugin;
    }

    public void addMissingKeysAndValues(FileConfiguration config, String fileName) {
        List<String> KeysAndValues = getKeysAndValues(fileName);
        for (String key : config.getKeys(true)) {
            KeysAndValues.remove(key);
        }
        if (KeysAndValues.isEmpty()) {
            return;
        }
        FileConfiguration defaultConfig;
        try {
            defaultConfig = getConfigFromResource(fileName);
            for (String actuallyMissingKey : KeysAndValues) {
                config.set(actuallyMissingKey, defaultConfig.get(actuallyMissingKey));
                File configFile = new File(plugin.getDataFolder(), fileName);
                config.save(configFile);
            }
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getKeysAndValues(String resourceName) {
        FileConfiguration config = new YamlConfiguration();
        try {
            config = getConfigFromResource(resourceName);
        } catch (InvalidConfigurationException | IOException e) {
            System.out.println(e);
        }
        return new ArrayList<>(config.getKeys(true));
    }

    public FileConfiguration getConfigFromResource(String resourceName) throws IOException, InvalidConfigurationException {
        YamlConfiguration config = new YamlConfiguration();
        InputStream stream = plugin.getResource(resourceName);
        Reader reader = new InputStreamReader(stream);
        config.load(reader);
        reader.close();
        stream.close();
        return config;
    }


    public static void removeRIFromConfig(RotatingItems plugin, String name) {
        plugin.dataConfig.set(name, null);
        plugin.saveDataConfig();
    }
    public static void createRIInConfig(RotatingItems plugin, ItemDisplay display, Interaction hitBox, String name, float size, String direction, float speed, ItemStack item) {
        Location location = display.getLocation();
        plugin.dataConfig.set(name + ".size", size);
        plugin.dataConfig.set(name + ".direction", direction);
        plugin.dataConfig.set(name + ".speed", speed);
        plugin.dataConfig.set(name + ".location", location);
        plugin.dataConfig.set(name + ".item", item);
        plugin.dataConfig.set(name + ".display", display.getUniqueId().toString());
        plugin.dataConfig.set(name + ".hitbox", hitBox.getUniqueId().toString());
        plugin.saveDataConfig();
    }
}
