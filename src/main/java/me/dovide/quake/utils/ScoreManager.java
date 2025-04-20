package me.dovide.quake.utils;

import fr.mrmicky.fastboard.FastBoard;
import me.dovide.quake.game.GamePlayer;
import me.dovide.utils.Util;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ScoreManager {

    private final HashMap<Player, FastBoard> activeBoards;

    public ScoreManager(){
        activeBoards = new HashMap<>();
    }

    public void createScoreboard(List<GamePlayer> players, GamePlayer player) {
        FastBoard board = new FastBoard(player.getPlayer());

        board.updateTitle(Util.cc("&bQuake Game"));

        List<String> lines = new ArrayList<>();
        lines.add("");

        for (GamePlayer gp : players) {
            String name = gp.getPlayer().getName();
            int score = gp.getScore();

            lines.add(Util.cc("&f" + name + ": &e" + score));
        }

        board.updateLines(lines);
        activeBoards.put(player.getPlayer(), board);
    }

    public HashMap<Player, FastBoard> getActiveBoards(){
        return activeBoards;
    }






}
