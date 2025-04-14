package me.dovide.quake.utils;

import me.dovide.quake.game.arena.Arena;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class CreatorManager {

    public CreatorManager(){
        this.activeCreators = new HashMap<>();
    }

    private final HashMap<Player, Arena> activeCreators;

    public HashMap<Player, Arena> getCooldown(){
        return activeCreators;
    }


}
