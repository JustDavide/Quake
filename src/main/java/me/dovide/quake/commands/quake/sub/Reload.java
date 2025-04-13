package me.dovide.quake.commands.quake.sub;

import me.dovide.quake.QuakeMain;
import me.dovide.quake.commands.SubCommand;
import me.dovide.quake.utils.Config;
import org.bukkit.entity.Player;

public class Reload extends SubCommand {

    private final QuakeMain instance;
    private final Config config;

    public Reload(QuakeMain instance){
        this.instance = instance;
        this.config = instance.getConfig();
    }

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public void execute(Player player, String[] args) {

        if(args.length != 1){
            player.sendMessage("Args sbagliati");
            return;
        }

        if(player.hasPermission(config.getString("perms.quake.reload"))){
            player.sendMessage("No Perms");
            return;
        }

        instance.reloadConfig();
        player.sendMessage("Config ricaricato.");

    }
}
