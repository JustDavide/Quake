package me.dovide.quake.game;

import me.dovide.quake.QuakeMain;
import me.dovide.quake.game.arena.Arena;
import me.dovide.quake.game.arena.ArenaManager;
import me.dovide.quake.utils.LOCALE;
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
            player.sendMessage(LOCALE.ARENA_NOT_FOUND.msg(instance));
            return;
        }

        if (game.getPlayers().size() >= game.getArena().getMaxPlayers()) {
            player.sendMessage(LOCALE.FULL.msg(instance));
            return;
        }

        if(game.getState() == GameState.PLAYING){
            player.sendMessage(LOCALE.ALREADY_STARTED.msg(instance));
            return;
        }

        game.playerJoin(player);
        playersInGame.put(player, arenaManager.getArena(arenaId));
        player.sendMessage(LOCALE.YOU_JOINED.msg(instance));
    }

    public void leaveArena(String arenaId, Player player) {
        GameInstance game = getGame(arenaId);
        if (game == null) return;

        if (game.getState() == GameState.PLAYING && game.getPlayers().size() == 2) {
            playersInGame.remove(player);
            game.playerLeave(player);

            if(instance.getScoreManager().getActiveBoards().get(player) != null) {
                instance.getScoreManager().getActiveBoards().get(player).delete();
                instance.getScoreManager().getActiveBoards().remove(player);
            }

            game.stopGame(game.getPlayers().values().stream().findFirst().get().getPlayer());
            return;
        }

        playersInGame.remove(player);
        game.playerLeave(player);
        if(instance.getScoreManager().getActiveBoards().get(player) != null) {
            instance.getScoreManager().getActiveBoards().get(player).delete();
            instance.getScoreManager().getActiveBoards().remove(player);
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
