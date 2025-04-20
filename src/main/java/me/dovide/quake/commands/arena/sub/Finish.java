package me.dovide.quake.commands.arena.sub;

import me.dovide.quake.QuakeMain;
import me.dovide.quake.commands.SubCommand;
import me.dovide.quake.game.arena.Arena;
import me.dovide.quake.game.arena.ArenaManager;
import me.dovide.quake.utils.Config;
import me.dovide.quake.utils.CreatorManager;
import me.dovide.quake.utils.LOCALE;
import org.bukkit.entity.Player;

public class Finish extends SubCommand {

    private final Config config;
    private final CreatorManager creatorManager;
    private final ArenaManager arenaManager;
    private final QuakeMain instance;

    public Finish(QuakeMain instance){
        this.config = instance.getConfig();
        this.creatorManager = instance.getCreatorManager();
        this.arenaManager = instance.getArenaManager();
        this.instance = instance;
    }

    @Override
    public String getName() {
        return "finish";
    }

    @Override
    public void execute(Player player, String[] args) {

        if(args.length != 1){
            player.sendMessage(LOCALE.WRONG_ARGS.msg(instance));
            return;
        }

        if(!player.hasPermission(config.getString("perms.arena.setup"))){
            player.sendMessage(LOCALE.NO_PERMS.msg(instance));
            return;
        }

        if(!creatorManager.getActiveCreators().containsKey(player)){
            player.sendMessage(LOCALE.NOT_CREATING.msg(instance));
            return;
        }

        Arena arena = creatorManager.getActiveCreators().get(player);

        if(arena.isAnyNull()){
            player.sendMessage(LOCALE.NOT_FINISHED.msg(instance));
            return;
        }

        arenaManager.createArena(arena);
        player.sendMessage(LOCALE.FINISHED.msg(instance));
    }
}
