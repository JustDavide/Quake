package me.dovide.quake.commands.sub;

import me.dovide.quake.commands.SubCommand;
import me.dovide.quake.utils.Items;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Get extends SubCommand {

    private final Items items;

    public Get(){
        this.items = new Items();
    }


    @Override
    public String getName() {
        return "get";
    }

    @Override
    public void execute(Player player, String[] args) {

        if(args.length != 1){
            player.sendMessage("Wrong args");
            return;
        }

        player.getInventory().addItem(items.getGun());

    }
}
