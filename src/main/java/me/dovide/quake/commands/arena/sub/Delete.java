package me.dovide.quake.commands.arena.sub;

import me.dovide.quake.QuakeMain;
import me.dovide.quake.commands.SubCommand;
import me.dovide.quake.game.GameManager;
import me.dovide.quake.game.arena.Arena;
import me.dovide.quake.game.arena.ArenaManager;
import me.dovide.quake.utils.Config;
import org.bukkit.entity.Player;

public class Delete extends SubCommand {

    private final Config config;
    private final Config arenas;
    private final GameManager gameManager;
    private final ArenaManager arenaManager;
    private final QuakeMain instance;

    public Delete(QuakeMain instance){
        this.config = instance.getConfig();
        this.arenas = instance.getArenas();
        this.gameManager = instance.getGameManager();
        this.arenaManager = instance.getArenaManager();
        this.instance = instance;
    }


    @Override
    public String getName() {
        return "delete";
    }

    @Override
    public void execute(Player player, String[] args) {

        if(args.length != 2){
            player.sendMessage("Wrong Args");
            return;
        }

        if(!player.hasPermission(config.getString("perms.arena.delete"))){
            player.sendMessage("No perms");
            return;
        }

        String arenaID = args[1];

        if(arenas.get(arenaID) == null){
            player.sendMessage("Arena doesn't exist");
            return;
        }

        Arena arena = arenaManager.getArena(arenaID);

        arenaManager.getActiveArenas().remove(arena);
        gameManager.getGames().remove(arenaID);
        arenas.set(arenaID, null);
        instance.saveArenas();

        player.sendMessage("Arena removed.");

    }
}
