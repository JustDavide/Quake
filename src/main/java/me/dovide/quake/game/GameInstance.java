package me.dovide.quake.game;

import me.dovide.quake.QuakeMain;
import me.dovide.quake.game.arena.Arena;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GameInstance {

    private final Arena arena;
    private final Map<UUID, GamePlayer> players = new HashMap<>();
    private final Map<Player, Location> prevPos = new HashMap<>();
    private final Map<Player, PlayerInventory> prevInv = new HashMap<>();
    private GameState state = GameState.WAITING;
    private final QuakeMain instance;

    private BukkitRunnable countdown;

    public GameInstance(Arena arena, QuakeMain instance){
        this.arena = arena;
        this.instance = instance;
    }

    public void playerJoin(Player player){
        players.put(player.getUniqueId(), new GamePlayer(player));
        prevPos.put(player, player.getLocation()); // Salvo la posizione nel mondo per poi riportarcelo
        prevInv.put(player, player.getInventory()); // Salvo anche l'inventario così posso aggiungere item senza problemi
        player.getInventory().clear();
        player.teleport(arena.getLobby());

        // Check per lo start del game
    }

    public void playerLeave(Player player){
        player.getInventory().clear();
        player.teleport(prevPos.get(player));
        player.getInventory().setContents(prevInv.get(player).getContents());

        players.remove(player.getUniqueId());
        prevPos.remove(player);
        prevInv.remove(player);

        if(players.size() < arena.getMinPlayers() && state == GameState.STARTING){
            // Cancella countdown
            state = GameState.WAITING;
        }
    }

    private void checkStart(){
        if (players.size() > arena.getMinPlayers() && state == GameState.WAITING){
            state = GameState.STARTING;
            // Inizia Countdown
        }
    }

    private void countdown(){
        countdown = new BukkitRunnable() {

            int timer = 10;

            @Override
            public void run() {
                if(players.size() < arena.getMinPlayers()){
                    cancel();
                    state = GameState.WAITING;
                    return;
                }

                if(timer <= 0){
                    // Inizio Game
                    cancel();
                    return;
                }

                players.values().forEach(p -> p.getPlayer().sendMessage("Il Gioco inizierà tra " + timer + " secondi"));
                timer--;


            }
        };

        countdown.runTaskTimer(instance, 0L, 20L);
    }

    private void cancelCountdown(){
        if(countdown != null){
            countdown.cancel();
        }
    }

    private void startGame(){
        state = GameState.PLAYING;

        List<Location> spawns = arena.getSpawns();
        int i = 0;

        for(GamePlayer gp : players.values()){
            gp.getPlayer().teleport(spawns.get(i++ % spawns.size())); // Fa in modo che ogni spawn viene utilizzato
        }
    }

    public GameState getState(){
        return state;
    }

    public Map<UUID, GamePlayer> getPlayers(){
        return players;
    }

    public Arena getArena(){
        return arena;
    }


}
