package me.dovide.quake.commands.quake;

import me.dovide.quake.QuakeMain;
import me.dovide.quake.commands.SubCommand;
import me.dovide.quake.commands.quake.sub.Get;
import me.dovide.quake.commands.quake.sub.Join;
import me.dovide.quake.commands.quake.sub.Leave;
import me.dovide.quake.commands.quake.sub.Reload;
import me.dovide.quake.utils.LOCALE;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class QuakeCommand implements TabExecutor {

    private final HashMap<String, SubCommand> subCommands = new HashMap<>();
    private final QuakeMain instance;

    public QuakeCommand(QuakeMain instance){
        this.instance = instance;

        registerSubCommand(new Get(instance));
        registerSubCommand(new Reload(instance));
        registerSubCommand(new Join(instance));
        registerSubCommand(new Leave(instance));
    }

    private void registerSubCommand(SubCommand sub){
        subCommands.put(sub.getName(), sub);
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if(!(commandSender instanceof Player player)){
            commandSender.sendMessage(LOCALE.NOT_PLAYER.msg(instance));
            return true;
        }

        if(args.length == 0){
            player.sendMessage(LOCALE.WRONG_ARGS.msg(instance));
            return true;
        }

        String subName = args[0].toLowerCase();
        SubCommand sub = subCommands.get(subName);

        if(sub != null)
            sub.execute(player, args);
        else
            player.sendMessage(LOCALE.ARG_NOT_FOUND.msg(instance));

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
