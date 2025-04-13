package me.dovide.quake.utils;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CreatorManager {

    public CreatorManager(){
        this.activeCreators = new ArrayList<>();
    }

    private final List<Player> activeCreators;

    public List<Player> getCooldown(){
        return activeCreators;
    }


}
