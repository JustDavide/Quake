package me.dovide.quake.game.arena;

import org.bukkit.Location;

import java.util.List;
import java.util.UUID;

public class Arena {

    public String ID;
    public UUID worldUUID;
    public List<Location> spawns;
    public Location lobby;
    public Integer maxPlayers;
    public Integer minPlayers;

    public Arena(String ID, UUID worldUUID, List<Location> spawns, Location lobby, int maxPlayers, int minPlayers) {
        this.ID = ID;
        this.worldUUID = worldUUID;
        this.spawns = spawns;
        this.lobby = lobby;
        this.maxPlayers = maxPlayers;
        this.minPlayers = minPlayers;
    }

    public Arena() {
    }

    public Location getLobby() {
        return lobby;
    }

    public void setLobby(Location lobby) {
        this.lobby = lobby;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public UUID getWorldUUID() {
        return worldUUID;
    }

    public void setWorldUUID(UUID worldUUID) {
        this.worldUUID = worldUUID;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public List<Location> getSpawns() {
        return spawns;
    }

    public void setSpawns(List<Location> spawns) {
        this.spawns = spawns;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    public boolean isAnyNull(){
        return this.ID == null || this.worldUUID == null || this.spawns == null || this.lobby == null || this.maxPlayers == null || this.minPlayers == null;
    }

}
