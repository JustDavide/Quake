package me.dovide.quake.commands.quake.sub;

import me.dovide.quake.QuakeMain;
import me.dovide.quake.commands.SubCommand;
import me.dovide.quake.game.GameManager;
import me.dovide.quake.utils.LOCALE;
import org.bukkit.entity.Player;

public class Leave extends SubCommand {

    private final GameManager gameManager;
    private final QuakeMain instance;

    public Leave(QuakeMain instance){
        this.gameManager = instance.getGameManager();
        this.instance = instance;
    }

    @Override
    public String getName() {
        return "leave";
    }

    @Override
    public void execute(Player player, String[] args) {
        if(args.length != 1){
            player.sendMessage(LOCALE.WRONG_ARGS.msg(instance));
            return;
        }

        if(!gameManager.isPlayerInGame(player)){
            player.sendMessage(LOCALE.NOT_PLAYING.msg(instance));
            return;
        }

        String arenaID = gameManager.getPlayersInGame().get(player).getID();
        gameManager.leaveArena(arenaID, player);
        player.sendMessage(LOCALE.YOU_LEFT.msg(instance));
    }
}
