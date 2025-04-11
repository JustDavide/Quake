package me.dovide.quake;

import me.dovide.quake.commands.Gun;
import me.dovide.quake.listeners.GunClick;
import me.dovide.quake.utils.Config;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class QuakeMain extends JavaPlugin {

    private Config config;

    @Override
    public void onEnable() {
        this.config = createConfig("config.yml");

        getCommand("gun").setExecutor(new Gun(this));

        getServer().getPluginManager().registerEvents(new GunClick(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public Config createConfig(String name) {
        File fc = new File(getDataFolder(), name);
        if (!fc.exists()) {
            fc.getParentFile().mkdir();
            saveResource(name, false);
        }
        Config config = new Config();
        try {
            config.load(fc);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }

        return config;
    }

    public Config getConfig(){
        return config;
    }
}
