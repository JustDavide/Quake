package me.dovide.quake.commands.arena.sub;

import me.dovide.quake.QuakeMain;
import me.dovide.quake.commands.SubCommand;
import me.dovide.quake.commands.arena.ArenaCommand;
import me.dovide.quake.game.arena.Arena;
import me.dovide.quake.utils.Config;
import me.dovide.quake.utils.CreatorManager;
import org.bukkit.entity.Player;

public class Create extends SubCommand {

    private final QuakeMain instance;
    private final Config config;
    private final CreatorManager creatorManager;

    public Create(QuakeMain instance){
        this.instance = instance;
        this.config = instance.getConfig();
        this.creatorManager = instance.getCreatorManager();
    }


    @Override
    public String getName() {
        return "create";
    }

    @Override
    public void execute(Player player, String[] args) {

        if (args.length != 2){
            player.sendMessage("Wrong Args");
            return;
        }

        if(!player.hasPermission(config.getString("perms.arena.setup"))){
            player.sendMessage("No Perms");
            return;
        }

        if(creatorManager.getActiveCreators().containsKey(player)){
            player.sendMessage("Already creating");
            return;
        }

        String arenaID = args[1];
        Arena arena = new Arena();
        arena.setID(arenaID);

        creatorManager.getActiveCreators().put(player, arena);
        player.sendMessage("Arena in creation. Use /arena <sub-command> to edit it");
    }
}
