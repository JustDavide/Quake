package me.dovide.quake.utils;

import fr.mrmicky.fastboard.FastBoard;
import me.dovide.quake.QuakeMain;
import me.dovide.quake.game.GamePlayer;
import me.dovide.utils.Util;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ScoreManager {

    private final HashMap<Player, FastBoard> activeBoards;
    private final Config config;

    public ScoreManager(QuakeMain instance){
        activeBoards = new HashMap<>();
        this.config = instance.getConfig();
    }

    public void createScoreboard(List<GamePlayer> players, GamePlayer player) {
        FastBoard board = new FastBoard(player.getPlayer());

        board.updateTitle(config.getString("misc.score.title"));

        List<String> lines = new ArrayList<>();

        for (GamePlayer gp : players) {
            String name = gp.getPlayer().getName();
            int score = gp.getScore();

            lines.add(config.getString("misc.score.format")
                    .replace("%player%", name)
                    .replace("%score%", String.valueOf(score)));
        }

        board.updateLines(lines);
        activeBoards.put(player.getPlayer(), board);
    }

    public HashMap<Player, FastBoard> getActiveBoards(){
        return activeBoards;
    }






}
