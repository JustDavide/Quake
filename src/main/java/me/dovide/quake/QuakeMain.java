package me.dovide.quake;

import me.dovide.quake.commands.arena.ArenaCommand;
import me.dovide.quake.commands.arena.sub.Create;
import me.dovide.quake.commands.quake.QuakeCommand;
import me.dovide.quake.db.Database;
import me.dovide.quake.game.GameManager;
import me.dovide.quake.game.arena.ArenaManager;
import me.dovide.quake.listeners.GunClick;
import me.dovide.quake.utils.CDManager;
import me.dovide.quake.utils.Config;
import me.dovide.quake.utils.CreatorManager;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public final class QuakeMain extends JavaPlugin {

    private Config config;
    private Config arenas;
    private ArenaManager arenaManager;
    private GameManager gameManager;
    private CreatorManager creatorManager;
    private CDManager cdManager;

    @Override
    public void onEnable() {

        this.config = createConfig("config.yml");
        this.arenas = createConfig("arenas.yml");

        Database database = new Database(this);

        try{
            database.getConnection();
        }catch (SQLException err){
            getServer().getLogger().severe("Impossibile Connettersi al Database. Disabilito il plugin");
            getServer().getPluginManager().disablePlugin(this);
            err.printStackTrace();
        }

        try{
            database.initDatabase();
        }catch(SQLException err){
            getServer().getLogger().severe("Impossibile Creare / Caricare le tabelle");
            getServer().getPluginManager().disablePlugin(this);
            err.printStackTrace();
        }

        this.arenaManager = new ArenaManager(this);
        arenaManager.initArenas();
        this.gameManager = new GameManager(this, arenaManager);

        this.cdManager = new CDManager();
        this.creatorManager = new CreatorManager();

        getCommand("quake").setExecutor(new QuakeCommand(this));
        getCommand("arena").setExecutor(new ArenaCommand(this));

        getServer().getPluginManager().registerEvents(new GunClick(this), this);
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

    @Override
    public Config getConfig(){
        return config;
    }

    public Config getArenas(){
        return arenas;
    }

    public void saveArenas(){
        try{
            arenas.save(new File(getDataFolder(), "arenas.yml"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void reloadConfig(){
        this.config = createConfig("config.yml");
    }

    public ArenaManager getArenaManager(){
        return arenaManager;
    }

    public GameManager getGameManager(){
        return gameManager;
    }

    public CDManager getCdManager(){
        return cdManager;
    }

    public CreatorManager getCreatorManager(){
        return creatorManager;
    }
}
