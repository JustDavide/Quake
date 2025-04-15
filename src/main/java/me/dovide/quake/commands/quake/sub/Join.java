package me.dovide.quake.commands.quake.sub;

import me.dovide.quake.QuakeMain;
import me.dovide.quake.commands.SubCommand;
import me.dovide.quake.game.GameManager;
import org.bukkit.entity.Player;

public class Join extends SubCommand {

    private final GameManager gameManager;

    public Join(QuakeMain instance){
        this.gameManager = instance.getGameManager();
    }

    @Override
    public String getName() {
        return "join";
    }

    @Override
    public void execute(Player player, String[] args) {
        if (args.length != 2){
            player.sendMessage("Wrong Args");
            return;
        }

        if(gameManager.isPlayerInGame(player)){
            player.sendMessage("Sei già in una partita. Usa /quake leave per uscire");
            return;
        }

        String id = args[1];
        gameManager.joinArena(id, player);
        player.sendMessage("Sei entrato nell'arena");
    }
}
