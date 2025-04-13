package me.dovide.quake.game;

import me.dovide.quake.QuakeMain;
import me.dovide.quake.game.arena.Arena;
import me.dovide.quake.game.arena.ArenaManager;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class GameManager {

    private final QuakeMain instance;
    private final Map<String, GameInstance> games = new HashMap<>();

    public GameManager(QuakeMain instance, ArenaManager arenaManager){
        this.instance = instance;
        for (Arena arena : arenaManager.getActiveArenas().keySet()){
            games.put(arena.getID(), new GameInstance(arena, instance));
        }
    }

    public GameInstance getGame(String id){
        return games.get(id);
    }

    public void joinArena(String arenaId, Player player) {
        GameInstance game = getGame(arenaId);
        if (game == null) {
            player.sendMessage("This arena doesn't exist.");
            return;
        }
        if (game.getPlayers().size() >= game.getArena().getMaxPlayers()) {
            player.sendMessage("Arena is full.");
            return;
        }
        game.playerJoin(player);
    }

    public void leaveArena(String arenaId, Player player) {
        GameInstance game = getGame(arenaId);
        if (game != null) {
            game.playerLeave(player);
        }
    }

}
