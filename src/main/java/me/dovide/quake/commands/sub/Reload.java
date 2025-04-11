package me.dovide.quake.commands.sub;

import me.dovide.quake.QuakeMain;
import me.dovide.quake.commands.SubCommand;
import org.bukkit.entity.Player;

public class Reload extends SubCommand {

    private final QuakeMain instance;

    public Reload(QuakeMain instance){
        this.instance = instance;
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

        // Da implementare i permessi da config

        instance.reloadConfig();
        player.sendMessage("Config ricaricato.");

    }
}
