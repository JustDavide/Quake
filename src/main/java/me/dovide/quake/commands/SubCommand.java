package me.dovide.quake.commands;

import org.bukkit.entity.Player;

public abstract class SubCommand {

    public abstract String getName();
    public abstract void execute(Player player, String[] args);

}
