package me.dovide.quake.commands.arena.sub;

import me.dovide.quake.QuakeMain;
import me.dovide.quake.commands.SubCommand;
import me.dovide.quake.game.arena.Arena;
import me.dovide.quake.utils.Config;
import me.dovide.quake.utils.CreatorManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AddSpawn extends SubCommand {

    private final Config config;
    private final CreatorManager creatorManager;

    public AddSpawn(QuakeMain instance){
        this.config = instance.getConfig();
        this.creatorManager = instance.getCreatorManager();
    }


    @Override
    public String getName() {
        return "addspawn";
    }

    @Override
    public void execute(Player player, String[] args) {

        if(args.length != 1){
            player.sendMessage("Wrong args");
            return;
        }

        if(!player.hasPermission(config.getString("perms.arena.setup"))){
            player.sendMessage("No perms");
            return;
        }

        if(!creatorManager.getActiveCreators().containsKey(player)){
            player.sendMessage("not creating");
            return;
        }

        Arena arena = creatorManager.getActiveCreators().get(player);

        List<Location> locations;

        if(arena.getSpawns() == null || arena.getSpawns().isEmpty())
            locations = new ArrayList<>();
        else
            locations = arena.getSpawns();

        locations.add(player.getLocation());
        arena.setWorldUUID(player.getWorld().getUID());
        arena.setSpawns(locations);
        player.sendMessage("Spawn added");
    }
}
