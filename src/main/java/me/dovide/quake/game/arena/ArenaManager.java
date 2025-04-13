package me.dovide.quake.game.arena;

import me.dovide.quake.QuakeMain;
import me.dovide.quake.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;

public class ArenaManager {

    private final Config arenas;
    private final QuakeMain instance;

    public ArenaManager(QuakeMain instance){
        this.activeArenas = new HashMap<>();

        this.instance = instance;
        this.arenas = instance.getArenas();
    }

    public Map<Arena, GameState> activeArenas;

    public void createArena(String id, World world, Location lobby, List<Location> spawns, int maxPlayers, int minPlayers){
       arenas.set(id + ".world", world.getUID().toString()); // UUID del mondo per essere più accurati e per evitare problemi (cambio nome del mondo)
       arenas.set(id + ".max_players", maxPlayers);
       arenas.set(id + ".min_players", minPlayers);

       arenas.set(id + ".lobby.x", lobby.getX());
       arenas.set(id + ".lobby.y", lobby.getY());
       arenas.set(id + ".lobby.z", lobby.getZ());
       arenas.set(id + ".lobby.world", lobby.getWorld().getUID().toString());

       for (int i = 0; i < spawns.size(); i++){
           Location loc = spawns.get(i);

           arenas.set(id + ".spawns." + i + ".x", loc.getX());
           arenas.set(id + ".spawns." + i + ".y", loc.getY());
           arenas.set(id + ".spawns." + i + ".z", loc.getZ());
       }

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

}
