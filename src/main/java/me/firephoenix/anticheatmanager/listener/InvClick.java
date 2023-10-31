package me.firephoenix.anticheatmanager.listener;

import me.firephoenix.anticheatmanager.AnticheatManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * @author NieGestorben
 * Copyright© (c) 2023, All Rights Reserved.
 */
public class InvClick implements Listener {

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getCurrentItem() == null) {
            return;
        }
        AnticheatManager.INSTANCE.getConfig().getStringList("name-list").forEach(name -> {
            Material material = Material.valueOf(AnticheatManager.INSTANCE.getConfig().getString("anticheats." + name + ".material"));
            String acName = AnticheatManager.INSTANCE.getConfig().getString("anticheats." + name + ".name");
            if (e.getCurrentItem().getType() == material && e.getCurrentItem().getItemMeta().getDisplayName().equals(acName)) {
                player.performCommand("acsel " + name);
            }
        });
    }

}
