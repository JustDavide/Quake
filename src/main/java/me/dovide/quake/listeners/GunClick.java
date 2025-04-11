package me.dovide.quake.listeners;

import me.dovide.quake.QuakeMain;
import me.dovide.quake.utils.CDManager;
import me.dovide.quake.utils.Config;
import me.dovide.quake.utils.Items;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class GunClick implements Listener {

    private final Items items;
    private final Config config;
    private final CDManager cdManager;

    public GunClick(QuakeMain instance){
        this.config = instance.getConfig();
        this.items = new Items();
        this.cdManager = new CDManager();
    }

    @EventHandler
    public void GunShoot(PlayerInteractEvent e){

        HashMap<Player, Long> cooldown = cdManager.getCooldown();
        int cooldownTime = config.getInt("misc.cooldown");
        Player player = e.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if(!item.equals(items.getGun())) return;

        if(!(e.getAction() == Action.RIGHT_CLICK_AIR)){
            e.setCancelled(true); // cancella qualasiasi azione se non per sparare
            return; // check per RMB
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

            world.spawnParticle(Particle.DUST, loc.clone(), 1, new Particle.DustOptions(Color.RED, 4));

            // Check per eliminare un giocatore
            for(Entity entity : world.getNearbyEntities(loc, 0.5, 0.5, 0.5)){
                if(entity instanceof Player && !entity.equals(player)){
                    // Respawn Mechanic
                    player.sendMessage("You hit " + entity.getName());
                    return;
                }
            }

        }
    }

}
