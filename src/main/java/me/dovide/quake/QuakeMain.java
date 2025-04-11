package me.dovide.quake;

import me.dovide.quake.commands.Gun;
import me.dovide.quake.listeners.GunClick;
import org.bukkit.plugin.java.JavaPlugin;

public final class QuakeMain extends JavaPlugin {

    private QuakeMain instance;

    @Override
    public void onEnable() {
        this.instance = this;

        getCommand("gun").setExecutor(new Gun(this));

        getServer().getPluginManager().registerEvents(new GunClick(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public QuakeMain getInstance(){
        return this.instance;
    }
}
