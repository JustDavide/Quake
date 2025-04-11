package me.dovide.quake.utils;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class CDManager {

    public CDManager(){
        this.cooldown = new HashMap<>();
    }

    private final HashMap<Player, Long> cooldown;

    public HashMap<Player, Long> getCooldown(){
        return cooldown;
    }



}
