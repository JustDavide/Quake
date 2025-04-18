package me.dovide.quake.commands.arena.sub;

import me.dovide.quake.QuakeMain;
import me.dovide.quake.commands.SubCommand;
import me.dovide.quake.utils.Config;
import me.dovide.quake.utils.CreatorManager;
import me.dovide.quake.utils.LOCALE;
import org.bukkit.entity.Player;

public class Cancel extends SubCommand {

    private final QuakeMain instance;
    private final CreatorManager creatorManager;
    private final Config config;

    public Cancel(QuakeMain instance){
        this.instance = instance;
        this.creatorManager = instance.getCreatorManager();
        this.config = instance.getConfig();
    }

    @Override
    public String getName() {
        return "cancel";
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

        creatorManager.getActiveCreators().remove(player);
        player.sendMessage(LOCALE.STOPPED_CREATING.msg(instance));

    }
}
