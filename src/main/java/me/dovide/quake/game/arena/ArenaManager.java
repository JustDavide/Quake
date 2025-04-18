package me.dovide.quake.game.arena;

import me.dovide.quake.QuakeMain;
import me.dovide.quake.game.GameManager;
import me.dovide.quake.game.GameState;
import me.dovide.quake.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;

public class ArenaManager {

    private final Config arenas;
    private final QuakeMain instance;
    private GameManager gameManager;
    public Map<Arena, GameState> activeArenas;

    public ArenaManager(QuakeMain instance){
        this.activeArenas = new HashMap<>();

        this.instance = instance;
        this.arenas = instance.getArenas();
    }

    public void createArena(Arena arena){
        String id = arena.getID();

        arenas.set(id + ".world", arena.getWorldUUID().toString()); // UUID del mondo per essere più accurati e per evitare problemi (cambio nome del mondo)
        arenas.set(id + ".max_players", arena.getMaxPlayers());
        arenas.set(id + ".min_players", arena.getMinPlayers());

        arenas.set(id + ".lobby.x", arena.getLobby().getX());
        arenas.set(id + ".lobby.y", arena.getLobby().getY());
        arenas.set(id + ".lobby.z", arena.getLobby().getZ());
        arenas.set(id + ".lobby.world", arena.getLobby().getWorld().getUID().toString());

        for (int i = 0; i < arena.getSpawns().size(); i++){
            Location loc = arena.getSpawns().get(i);

            arenas.set(id + ".spawns." + i + ".x", loc.getX());
            arenas.set(id + ".spawns." + i + ".y", loc.getY());
            arenas.set(id + ".spawns." + i + ".z", loc.getZ());
        }

        activeArenas.put(arena, GameState.WAITING);
        gameManager.updateCache();
        instance.saveArenas();
    }

    public Arena getArena(String id){

        if(arenas.get(id) == null)
            return null;


        Arena arena = new Arena();

        UUID worldUUID = UUID.fromString(arenas.getString(id + ".world"));
        int maxPlayers = arenas.getInt(id + ".max_players");
        int minPlayers = arenas.getInt(id + ".min_players");

        double lobbyX = arenas.getDouble(id + ".lobby.x");
        double lobbyY = arenas.getDouble(id + ".lobby.y");
        double lobbyZ = arenas.getDouble(id + ".lobby.z");
        String lobbyUUID = arenas.getString(id + ".lobby.world");
        World lobbyWorld = Bukkit.getWorld(UUID.fromString(lobbyUUID));

        List<Location> spawns = new ArrayList<>();

        for(String key : arenas.getConfigurationSection(id + ".spawns").getKeys(false)){ // Uso falso perchè voglio prendere solo il primo child
            double x = arenas.getDouble(id + ".spawns." + key + ".x");
            double y = arenas.getDouble(id + ".spawns." + key + ".y");
            double z = arenas.getDouble(id + ".spawns." + key + ".z");

            World world = Bukkit.getWorld(worldUUID);

            spawns.add(new Location(world, x, y, z));
        }


        arena.setID(id);
        arena.setWorldUUID(worldUUID);
        arena.setMaxPlayers(maxPlayers);
        arena.setLobby(new Location(lobbyWorld, lobbyX, lobbyY, lobbyZ));
        arena.setMinPlayers(minPlayers);
        arena.setSpawns(spawns);

        return arena;
    }

    public void initArenas(){ // Caricare tutte le arene dal config alla Map per lo start/riavvio del server
        ConfigurationSection section = arenas.getConfigurationSection(""); // root

        for(String id : section.getKeys(false)){
            activeArenas.put(getArena(id), GameState.WAITING);
        }
    }

    public Map<Arena, GameState> getActiveArenas(){
        return activeArenas;
    }

    public void setGameManager(GameManager gameManager){
        this.gameManager = gameManager;
    }

}
