package me.dovide.quake.commands.arena.sub;

import me.dovide.quake.QuakeMain;
import me.dovide.quake.commands.SubCommand;
import me.dovide.quake.game.arena.Arena;
import me.dovide.quake.utils.Config;
import me.dovide.quake.utils.CreatorManager;
import me.dovide.quake.utils.LOCALE;
import org.bukkit.entity.Player;

public class SetMinPlayers extends SubCommand {

    private final Config config;
    private final CreatorManager creatorManager;
    private final QuakeMain instance;

    public SetMinPlayers(QuakeMain instance){
        this.config = instance.getConfig();
        this.creatorManager = instance.getCreatorManager();
        this.instance = instance;
    }

    @Override
    public String getName() {
        return "setminplayers";
    }

    @Override
    public void execute(Player player, String[] args) {
        if(args.length != 2){
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
        int minPlayers;

        try {
            minPlayers = Integer.parseInt(args[1]);
        }catch (NumberFormatException err){
            player.sendMessage(LOCALE.NOT_A_NUMBER.msg(instance));
            return;
        }

        arena.setMinPlayers(minPlayers);
        player.sendMessage(LOCALE.MIN_PLAYERS_SET.msg(instance));
    }
}
