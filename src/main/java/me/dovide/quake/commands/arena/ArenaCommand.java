package me.dovide.quake.commands.arena;

import me.dovide.quake.QuakeMain;
import me.dovide.quake.commands.SubCommand;
import me.dovide.quake.commands.arena.sub.*;
import me.dovide.quake.commands.quake.sub.Get;
import me.dovide.quake.commands.quake.sub.Join;
import me.dovide.quake.commands.quake.sub.Leave;
import me.dovide.quake.commands.quake.sub.Reload;
import me.dovide.quake.game.arena.Arena;
import me.dovide.quake.utils.Config;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ArenaCommand implements TabExecutor {

    private final HashMap<String, SubCommand> subCommands = new HashMap<>();
    private final Config config;

    public ArenaCommand(QuakeMain instance){
        this.config = instance.getConfig();

        registerSubCommand(new Create(instance));
        registerSubCommand(new AddSpawn(instance));
        registerSubCommand(new Finish(instance));
        registerSubCommand(new SetLobby(instance));
        registerSubCommand(new SetMaxPlayers(instance));
        registerSubCommand(new SetMinPlayers(instance));
        registerSubCommand(new Delete(instance));
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

        if(!player.hasPermission(config.getString("perms.arena.command"))){
            player.sendMessage("No Perms");
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
