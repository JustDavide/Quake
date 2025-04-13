package me.dovide.quake.commands;

import me.dovide.quake.QuakeMain;
import me.dovide.quake.commands.sub.Get;
import me.dovide.quake.commands.sub.Reload;
import me.dovide.quake.utils.Config;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Quake implements TabExecutor {

    private final HashMap<String, SubCommand> subCommands = new HashMap<>();

    public Quake(QuakeMain instance){
        registerSubCommand(new Get(instance));
        registerSubCommand(new Reload(instance));
    }

    private void registerSubCommand(SubCommand sub){
        subCommands.put(sub.getName(), sub);
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if(!(commandSender instanceof Player player)){
            commandSender.sendMessage("Can't use this here");
            return true;
        }

        if(args.length == 0){
            player.sendMessage("Args wrong");
            return true;
        }


        String subName = args[0].toLowerCase();
        SubCommand sub = subCommands.get(subName);

        if(sub != null)
            sub.execute(player, args);
        else
            player.sendMessage("Sub doesn't exist");


        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return new ArrayList<>(subCommands.keySet()); // Suggerisce tutti i subcommands
        }
        return Collections.emptyList();
    }
}
