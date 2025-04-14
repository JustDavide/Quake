package me.dovide.quake.commands.arena.sub;

import me.dovide.quake.QuakeMain;
import me.dovide.quake.commands.SubCommand;
import me.dovide.quake.game.arena.Arena;
import me.dovide.quake.utils.Config;
import me.dovide.quake.utils.CreatorManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SetLobby extends SubCommand {

    private final Config config;
    private final CreatorManager creatorManager;

    public SetLobby(QuakeMain instance){
        this.config = instance.getConfig();
        this.creatorManager = instance.getCreatorManager();
    }

    @Override
    public String getName() {
        return "setLobby";
    }

    @Override
    public void execute(Player player, String[] args) {

        if(args.length != 1){
            player.sendMessage("Wrong args");
            return;
        }

        if(!player.hasPermission(config.getString("perms.arena.setup"))){
            player.sendMessage("no perms");
            return;
        }

        if(!creatorManager.getActiveCreators().containsKey(player)){
            player.sendMessage("Not Creating");
            return;
        }

        Location loc = player.getLocation();
        Arena arena = creatorManager.getActiveCreators().get(player);

        arena.setLobby(loc);
        player.sendMessage("Lobby set. When finished use /arena finish");

    }
}
