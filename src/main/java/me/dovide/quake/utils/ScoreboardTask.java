package me.dovide.quake.utils;

import me.dovide.quake.QuakeMain;
import me.dovide.quake.game.GameInstance;
import me.dovide.quake.game.GamePlayer;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class ScoreboardTask extends BukkitRunnable {

    private final GameInstance gameInstance;
    private final ScoreManager scoreManager;

    public ScoreboardTask(QuakeMain instance, GameInstance gameInstance){
        this.gameInstance = gameInstance;
        this.scoreManager = instance.getScoreManager();
    }

    @Override
    public void run() {
        for(GamePlayer gamePlayer : gameInstance.getPlayers().values()){
            scoreManager.createScoreboard(new ArrayList<>(gameInstance.getPlayers().values()), gamePlayer);
        }
    }

    @Override
    public synchronized void cancel() throws IllegalStateException {
        super.cancel();
    }

    public ScoreManager getScoreManager(){
        return scoreManager;
    }
}
