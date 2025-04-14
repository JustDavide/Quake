package me.dovide.quake.commands.arena.sub;

import me.dovide.quake.QuakeMain;
import me.dovide.quake.commands.SubCommand;
import me.dovide.quake.game.arena.Arena;
import me.dovide.quake.utils.Config;
import me.dovide.quake.utils.CreatorManager;
import org.bukkit.entity.Player;

public class SetMaxPlayers extends SubCommand {

    private final Config config;
    private final CreatorManager creatorManager;

    public SetMaxPlayers(QuakeMain instance){
        this.config = instance.getConfig();
        this.creatorManager = instance.getCreatorManager();
    }

    @Override
    public String getName() {
        return "setMaxPlayers";
    }

    @Override
    public void execute(Player player, String[] args) {

        if(args.length != 2){
            player.sendMessage("Wrong Args");
            return;
        }

        if(!player.hasPermission(config.getString("perms.arena.setup"))){
            player.sendMessage("No Perms");
            return;
        }

        if(!creatorManager.getActiveCreators().containsKey(player)){
            player.sendMessage("Not Creating");
            return;
        }

        Arena arena = creatorManager.getActiveCreators().get(player);
        int maxPlayers;

        try {
            maxPlayers = Integer.parseInt(args[1]);
        }catch (NumberFormatException err){
            player.sendMessage("Arg not a number");
            return;
        }

        arena.setMaxPlayers(maxPlayers);
        player.sendMessage("Max Players set. When finished use /arena finish");

    }
}
