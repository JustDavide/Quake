package me.dovide.quake.game;

import me.dovide.quake.QuakeMain;
import me.dovide.quake.game.arena.Arena;
import me.dovide.quake.game.arena.ArenaManager;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class GameManager {

    private final Map<Player, Arena> playersInGame = new HashMap<>();
    private final Map<String, GameInstance> games = new HashMap<>();
    private final ArenaManager arenaManager;
    private final QuakeMain instance;

    public GameManager(QuakeMain instance, ArenaManager arenaManager){
        this.instance = instance;
        this.arenaManager = arenaManager;
    }

    public GameInstance getGame(String id){
        return games.get(id);
    }

    public boolean isPlayerInGame(Player player){
        return playersInGame.containsKey(player);
    }

    public void joinArena(String arenaId, Player player) {
        GameInstance game = getGame(arenaId);
        if (game == null) {
            player.sendMessage("Arena non esiste");
            return;
        }

        if (game.getPlayers().size() >= game.getArena().getMaxPlayers()) {
            player.sendMessage("Arena piena");
            return;
        }
        game.playerJoin(player);
        playersInGame.put(player, arenaManager.getArena(arenaId));
        player.sendMessage("Sei entrato nell'arena");
    }

    public void leaveArena(String arenaId, Player player) {
        GameInstance game = getGame(arenaId);
        if (game != null) {
            playersInGame.remove(player);
            game.playerLeave(player);
        }
    }

    public Map<Player, Arena> getPlayersInGame(){
        return playersInGame;
    }

    public void updateCache(){
        for (Arena arena : arenaManager.getActiveArenas().keySet()){
            games.put(arena.getID(), new GameInstance(arena, instance));
        }
    }

    public Map<String, GameInstance> getGames(){
        return games;
    }

}
