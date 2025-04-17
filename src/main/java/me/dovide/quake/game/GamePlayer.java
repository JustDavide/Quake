package me.dovide.quake.game;

import org.bukkit.entity.Player;

public class GamePlayer {

    private final Player player;
    private int score;

    public GamePlayer(Player player){
        this.player = player;
        this.score = 0;
    }

    public Player getPlayer(){
        return player;
    }

    public int getScore(){
        return score;
    }

    public void addScore(){
        this.score += 1;
    }


}
