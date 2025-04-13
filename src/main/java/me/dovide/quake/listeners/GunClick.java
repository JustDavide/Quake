package me.dovide.quake.listeners;

import me.dovide.quake.QuakeMain;
import me.dovide.quake.db.Database;
import me.dovide.quake.game.GameInstance;
import me.dovide.quake.game.GameManager;
import me.dovide.quake.game.GamePlayer;
import me.dovide.quake.game.arena.Arena;
import me.dovide.quake.utils.CDManager;
import me.dovide.quake.utils.Config;
import me.dovide.quake.utils.Items;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Random;

public class GunClick implements Listener {

    private final Items items;
    private final Config config;
    private final CDManager cdManager;
    private final Database database;
    private final GameManager gameManager;

    public GunClick(QuakeMain instance){
        this.config = instance.getConfig();
        this.items = new Items();
        this.cdManager = new CDManager();
        this.database = new Database(instance);
        this.gameManager = instance.getGameManager();
    }

    @EventHandler
    public void GunShoot(PlayerInteractEvent e){

        if(!gameManager.isPlayerInGame(e.getPlayer())) // Check per evitare che persone al di fuori del game possano sparare
            return;

        HashMap<Player, Long> cooldown = cdManager.getCooldown();
        int cooldownTime = config.getInt("misc.cooldown");
        Player player = e.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if(!item.equals(items.getGun())) return;

        if(e.getAction() != Action.RIGHT_CLICK_AIR || e.getAction() != Action.RIGHT_CLICK_BLOCK){ // check per RMB
            e.setCancelled(true); // cancella qualasiasi azione se non per sparare
            return;
        }

        long currentTime = System.currentTimeMillis();

        if(cooldown.containsKey(player)){
            long lastFired = cooldown.get(player);
            if((currentTime - lastFired) < cooldownTime){
                float remaining = (float) (cooldownTime - (currentTime - lastFired)) / 1000;

                String formatted = String.format("%.02f", remaining);

                player.sendMessage("L'arma sta ricaricando.. Aspetta " + formatted + " secondi");
                return;
            }
        }

        cooldown.put(player, currentTime);
        fire(player);
    }

    private void fire(Player player){
        Location loc = player.getLocation();
        loc.add(0, player.getEyeHeight(),0);
        Vector direction = loc.getDirection().normalize();
        World world = player.getWorld();

        int range = config.getInt("misc.range");

        for(int i = 0; i < range; i++){
            loc.add(direction);

            if(loc.getBlock().getType().isSolid() && loc.getBlock().getType() != Material.GLASS)
                break;

            if(loc.getBlock().getType() == Material.GLASS) { // Aggiunta per distruggere il vetro se si spara [QOL]
                world.spawnParticle(Particle.BLOCK, loc, 30, 0.3, 0.3, 0.3, Material.GLASS.createBlockData());
                world.playSound(loc, Sound.BLOCK_GLASS_BREAK, 1.0f, 1.0f);
                loc.getBlock().breakNaturally();
            }


            world.spawnParticle(Particle.DUST, loc.clone(), 1, new Particle.DustOptions(Color.RED, 4));

            // Check per eliminare un giocatore
            for(Entity entity : world.getNearbyEntities(loc, 0.5, 0.5, 0.5)){
                if(entity instanceof Player && !entity.equals(player)){
                    // Respawn Mechanic
                    Arena arena = gameManager.getPlayersInGame().get(entity);
                    entity.teleport(arena.getSpawns().get(new Random().nextInt(arena.getSpawns().size()))); // Potrebbe essere fatto meglio TODO

                    player.sendMessage("Hai colpito: " + entity.getName());
                    entity.sendMessage("Sei stato colpito da: " + player.getName());
                    assignPoint(player);
                    return;
                }
            }

        }
    }

    private void assignPoint(Player player){
        String gameID = gameManager.getPlayersInGame().get(player).getID();
        GameInstance gameInstance = gameManager.getGame(gameID);

        GamePlayer gamePlayer = gameInstance.getPlayers().get(player.getUniqueId());
        gamePlayer.addScore();
    }

}
