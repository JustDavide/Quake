package me.dovide.quake.game;

import me.dovide.quake.QuakeMain;
import me.dovide.quake.db.Database;
import me.dovide.quake.db.obj.PlayerStats;
import me.dovide.quake.game.arena.Arena;
import me.dovide.quake.utils.Config;
import me.dovide.quake.utils.Items;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;
import java.util.*;

public class GameInstance {

    private final Arena arena;
    private final Map<UUID, GamePlayer> players = new HashMap<>();
    private final Map<Player, Location> prevPos = new HashMap<>();
    private final Map<Player, ItemStack[]> prevInv = new HashMap<>();
    private GameState state = GameState.WAITING;
    private final QuakeMain instance;
    private final Items items;
    private final Config config;
    private final GameManager gameManager;
    private final Database db;

    private BukkitRunnable countdown;

    public GameInstance(Arena arena, QuakeMain instance){
        this.config = instance.getConfig();
        this.gameManager = instance.getGameManager();
        this.db = new Database(instance);

        this.arena = arena;
        this.instance = instance;
        this.items = new Items();
    }

    public void playerJoin(Player player){
        players.put(player.getUniqueId(), new GamePlayer(player));
        prevPos.put(player, player.getLocation()); // Salvo la posizione nel mondo per poi riportarcelo
        prevInv.put(player, player.getInventory().getContents().clone()); // Salvo anche l'inventario così posso aggiungere item senza problemi

        player.getInventory().clear();
        player.teleport(arena.getLobby());

        checkStart();
    }

    public void playerLeave(Player player){
        player.getInventory().clear();
        player.teleport(prevPos.get(player));
        player.getInventory().setContents(prevInv.get(player));

        players.remove(player.getUniqueId());
        prevPos.remove(player);
        prevInv.remove(player);

        if(players.size() < arena.getMinPlayers() && state == GameState.STARTING){
            cancelCountdown();
            state = GameState.WAITING;
        }
    }

    private void checkStart(){
        if (players.size() >= arena.getMinPlayers() && state == GameState.WAITING){
            state = GameState.STARTING;
            startCountdown();
        }
    }

    private void startCountdown(){
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
                    startGame();
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
            gp.getPlayer().getInventory().addItem(items.getGun());
        }
    }

    public void assignPoint(Player player){
        GamePlayer gamePlayer = this.getPlayers().get(player.getUniqueId());
        gamePlayer.addScore();

        if(gamePlayer.getScore() >= config.getInt("misc.required_score")){
            state = GameState.ENDING;
            stopGame(player);
            state = GameState.WAITING;
        }
    }

    public void stopGame(Player winner) {
        try {
            for (GamePlayer player : new ArrayList<>(this.getPlayers().values())) {
                int score = player.getScore();

                gameManager.leaveArena(arena.ID, player.getPlayer());

                PlayerStats stats = db.getPlayerStats(player.getPlayer().getUniqueId());
                stats.setKills(stats.getKills()+score);
                db.registerPlayer(stats); // Da fixare duplicati
            }

            PlayerStats stats = db.getPlayerStats(winner.getUniqueId());
            stats.setWins(stats.getWins() + 1);
            db.registerPlayer(stats);
        } catch (SQLException err) {
            Bukkit.getLogger().severe("SQL ERROR");
            err.printStackTrace();
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
