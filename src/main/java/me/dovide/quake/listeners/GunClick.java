package me.dovide.quake.listeners;

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

public class GunClick implements Listener {

    private final Items items;

    public GunClick(){
        this.items = new Items();
    }


    @EventHandler
    public void GunShoot(PlayerInteractEvent e){


        if(!(e.getAction() == Action.RIGHT_CLICK_AIR)) return; // check per RMB

        Player player = e.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if(!item.equals(items.getGun())) return;

        Location loc = player.getLocation();
        loc.add(0, player.getEyeHeight(),0);
        Vector direction = loc.getDirection().normalize();
        World world = player.getWorld();

        // Range delle particelle (e hit check) (dovrà essere messo nel config)
        int range = 45;

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
