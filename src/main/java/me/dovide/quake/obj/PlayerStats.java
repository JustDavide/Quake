package me.dovide.quake.obj;

import java.util.UUID;

public class PlayerStats {

    UUID playerUUID;
    int kills;
    int wins;

    public PlayerStats(UUID playerUUID, int kills, int wins){
        this.playerUUID = playerUUID;
        this.kills = kills;
        this.wins = wins;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public void setPlayerUUID(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }
}
