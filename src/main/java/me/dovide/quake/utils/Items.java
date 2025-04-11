package me.dovide.quake.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Items {

    public ItemStack getGun(){

        ItemStack i = new ItemStack(Material.WOODEN_HOE);
        ItemMeta meta = i.getItemMeta();

        meta.setItemName("Quake Gun");


        i.setItemMeta(meta);

        return i;
    }

}
