package me.dovide.quake.utils;

import me.dovide.quake.QuakeMain;
import me.dovide.utils.Util;

public enum LOCALE {

    ARENA_CREATED("msg.arena.created"),
    SPAWN_ADDED("msg.arena.spawn_added"),
    ARENA_DELETED("msg.arena.deleted"),
    FINISHED("msg.arena.finished"),
    NOT_FINISHED("msg.arena.not_finished"),
    LOBBY_SET("msg.arena.lobby_set"),
    MAX_PLAYERS_SET("msg.arena.max_players_set"),
    MIN_PLAYERS_SET("msg.arena.min_players_set"),
    WRONG_ARGS("msg.errors.args"),
    NOT_PLAYER("msg.errors.not_players"),
    NOT_CREATING("msg.errors.not_creating"),
    NO_PERMS("msg.errors.no_perms"),
    STOPPED_CREATING("msg.arena.stopped_creating"),
    ARG_NOT_FOUND("msg.errors.arg_not_found"),
    ALREADY_CREATING("msg.errors.already_creating"),
    ARENA_NOT_FOUND("msg.errors.arena_not_found"),
    NOT_A_NUMBER("msg.errors.not_a_number"),
    ALREADY_PLAYING("msg.errors.already_playing"),
    NOT_PLAYING("msg.errors.not_playing"),
    YOU_LEFT("msg.arena.you_left"),
    LEFT("msg.arena.left"),
    RELOADED("msg.arena.reloaded"),
    JOINED("msg.arena.joined"),
    YOU_JOINED("msg.arena.you_joined"),
    STARTING("msg.arena.starting"),
    GAME_OVER("msg.arena.game_over"),
    RECHARGING("msg.errors.recharging"),
    HIT("msg.arena.hit"),
    GOT_HIT("msg.arena.got_hit"),
    FULL("msg.errors.full"),
    ALREADY_STARTED("msg.errors.already_started");


    private final String path;

    LOCALE(String path){
        this.path = path;
    }

    public String getPath(){
        return path;
    }

    public String msg(QuakeMain instance){
        String message = instance.getConfig().getString(path, "&cMessaggio non trovato. Ricarica il config o controlla " + path);

        return Util.cc(message);
    }

}
