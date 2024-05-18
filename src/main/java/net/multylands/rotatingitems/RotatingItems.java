package net.multylands.rotatingitems;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.multylands.rotatingitems.util.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class RotatingItems extends JavaPlugin {
    public String newVersion = null;

    public File dataFile;
    public String dataFileName = "data.yml";
    public FileConfiguration dataConfig;

    public String configFileName = "config.yml";
    public File configFile;

    public String languageFileName = "language.yml";
    public File languageFile;
    public FileConfiguration languageConfig;

    public static MiniMessage miniMessage;
    public static HashMap<String, CommandExecutor> commandExecutors = new HashMap<>();

    @Override
    public void onEnable() {
        miniMessage = MiniMessage.miniMessage();
        checkForUpdates();
        createConfigs();
        ServerUtils.implementBStats(this);
        ServerUtils.registerListeners(this);
        ServerUtils.registerCommands(this);
    }

    private void createConfigs() {
        try {
            ConfigUtils configUtils = new ConfigUtils(this);
            dataFile = new File(getDataFolder(), dataFileName);
            configFile = new File(getDataFolder(), configFileName);
            languageFile = new File(getDataFolder(), languageFileName);
            //we are checking if files exist to avoid console spamming. try it and see :)
            if (!languageFile.exists()) {
                saveResource(languageFileName, false);
            }
            if (!configFile.exists()) {
                saveDefaultConfig();
            }
            if (!dataFile.exists()) {
                saveResource(dataFileName, false);
            }
            dataConfig = new YamlConfiguration();
            languageConfig = new YamlConfiguration();

            dataConfig.load(dataFile);
            languageConfig.load(languageFile);
            getConfig().load(configFile);
            configUtils.addMissingKeysAndValues(getConfig(), configFileName);
            configUtils.addMissingKeysAndValues(dataConfig, dataFileName);
            configUtils.addMissingKeysAndValues(languageConfig, languageFileName);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void checkForUpdates() {
        new UpdateChecker(this, 114685).getVersion(version -> {
            if (!getDescription().getVersion().equals(version)) {
                newVersion = version;
                Chat.sendMessageSender(Bukkit.getConsoleSender(), languageConfig.getString("update-available").replace("%newversion%", version));
            }
        });
    }

    public void reloadLanguageConfig() {
        languageFile = new File(getDataFolder(), languageFileName);
        languageConfig = YamlConfiguration.loadConfiguration(languageFile);
    }
    public void reloadDataConfig() {
        dataFile = new File(getDataFolder(), dataFileName);
        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
    }
    public void saveDataConfig()  {
        dataFile = new File(getDataFolder(), dataFileName);
        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}