package me.dovide.quake.commands.quake.sub;

import me.dovide.quake.QuakeMain;
import me.dovide.quake.commands.SubCommand;
import me.dovide.quake.game.GameManager;
import me.dovide.quake.utils.LOCALE;
import org.bukkit.entity.Player;

public class Join extends SubCommand {

    private final GameManager gameManager;
    private final QuakeMain instance;

    public Join(QuakeMain instance){
        this.gameManager = instance.getGameManager();
        this.instance = instance;
    }

    @Override
    public String getName() {
        return "join";
    }

    @Override
    public void execute(Player player, String[] args) {
        if (args.length != 2){
            player.sendMessage(LOCALE.WRONG_ARGS.msg(instance));
            return;
        }

        if(gameManager.isPlayerInGame(player)){
            player.sendMessage(LOCALE.ALREADY_PLAYING.msg(instance));
            return;
        }

        String id = args[1];
        gameManager.joinArena(id, player);
    }
}
