package me.dovide.quake.commands.quake.sub;

import me.dovide.quake.QuakeMain;
import me.dovide.quake.commands.SubCommand;
import me.dovide.quake.game.GameManager;
import org.bukkit.entity.Player;

public class Leave extends SubCommand {

    private final GameManager gameManager;

    public Leave(QuakeMain instance){
        this.gameManager = instance.getGameManager();
    }

    @Override
    public String getName() {
        return "leave";
    }

    @Override
    public void execute(Player player, String[] args) {
        if(args.length != 1){
            player.sendMessage("Wrong Args");
            return;
        }

        if(!gameManager.isPlayerInGame(player)){
            player.sendMessage("Non sei in game. Usa /quake join per entrare");
            return;
        }

        String arenaID = gameManager.getPlayersInGame().get(player).getID();
        gameManager.leaveArena(arenaID, player);
        player.sendMessage("Hai lasciato l'arena");
    }
}
