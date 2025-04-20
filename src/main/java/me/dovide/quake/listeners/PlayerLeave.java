package me.dovide.quake.listeners;

import me.dovide.quake.QuakeMain;
import me.dovide.quake.game.GameManager;
import me.dovide.quake.game.arena.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeave implements Listener {

    private final GameManager gameManager;

    public PlayerLeave(QuakeMain instance){
        this.gameManager = instance.getGameManager();
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e){
        Player player = e.getPlayer();

        if(!gameManager.isPlayerInGame(player)){
            return;
        }

        Arena arena = gameManager.getPlayersInGame().get(player);
        gameManager.leaveArena(arena.getID(), player);
    }

}
