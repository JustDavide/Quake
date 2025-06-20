package me.dovide.quake.commands.quake.sub;

import me.dovide.quake.QuakeMain;
import me.dovide.quake.commands.SubCommand;
import me.dovide.quake.utils.Config;
import me.dovide.quake.utils.Items;
import me.dovide.quake.utils.LOCALE;
import org.bukkit.entity.Player;

public class Get extends SubCommand {

    private final Items items;
    private final Config config;
    private final QuakeMain instance;

    public Get(QuakeMain instance){
        this.items = new Items();
        this.config = instance.getConfig();
        this.instance = instance;
    }


    @Override
    public String getName() {
        return "get";
    }

    @Override
    public void execute(Player player, String[] args) {

        if(args.length != 1){
            player.sendMessage(LOCALE.WRONG_ARGS.msg(instance));
            return;
        }

        if(!player.hasPermission(config.getString("perms.quake.get"))){
            player.sendMessage(LOCALE.NO_PERMS.msg(instance));
            return;
        }

        player.getInventory().addItem(items.getGun());

    }
}
