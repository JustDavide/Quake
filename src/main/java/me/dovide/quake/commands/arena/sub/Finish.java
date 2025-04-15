package me.dovide.quake.commands.arena.sub;

import me.dovide.quake.QuakeMain;
import me.dovide.quake.commands.SubCommand;
import me.dovide.quake.game.arena.Arena;
import me.dovide.quake.game.arena.ArenaManager;
import me.dovide.quake.utils.Config;
import me.dovide.quake.utils.CreatorManager;
import org.bukkit.entity.Player;

public class Finish extends SubCommand {

    private final Config config;
    private final CreatorManager creatorManager;
    private final ArenaManager arenaManager;

    public Finish(QuakeMain instance){
        this.config = instance.getConfig();
        this.creatorManager = instance.getCreatorManager();
        this.arenaManager = instance.getArenaManager();
    }

    @Override
    public String getName() {
        return "finish";
    }

    @Override
    public void execute(Player player, String[] args) {

        if(args.length != 1){
            player.sendMessage("Wrong args");
            return;
        }

        if(player.hasPermission(config.getString("perms.arena.setup"))){
            player.sendMessage("No Perms");
            return;
        }

        if(!creatorManager.getActiveCreators().containsKey(player)){
            player.sendMessage("Not creating");
            return;
        }

        Arena arena = creatorManager.getActiveCreators().get(player);
        arenaManager.createArena(arena);

    }
}
