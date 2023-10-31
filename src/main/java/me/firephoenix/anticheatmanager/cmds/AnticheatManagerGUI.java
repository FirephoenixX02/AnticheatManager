package me.firephoenix.anticheatmanager.cmds;

import me.firephoenix.anticheatmanager.AnticheatManager;
import me.firephoenix.anticheatmanager.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

/**
 * @author NieGestorben
 * Copyright© (c) 2023, All Rights Reserved.
 */
public class AnticheatManagerGUI implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String string, String[] args) {
        Player player = AnticheatManager.INSTANCE.getServer().getPlayer(commandSender.getName());
        if (player != null) {
            List<String> anticheatList = AnticheatManager.INSTANCE.getConfig().getStringList("name-list");
            Inventory inventory = Bukkit.createInventory(player, 27, "Anticheats");
            for (int i = 0; i < inventory.getSize(); i++) {
                inventory.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE).toItemStack());
            }
            for (int i = 0; i < anticheatList.size(); i++) {
                Material material = Material.valueOf(AnticheatManager.INSTANCE.getConfig().getString("anticheats." + anticheatList.get(i) + ".material"));
                String name = AnticheatManager.INSTANCE.getConfig().getString("anticheats." + anticheatList.get(i) + ".name");
                String version = AnticheatManager.INSTANCE.getConfig().getString("anticheats." + anticheatList.get(i) + ".version");
                String price = AnticheatManager.INSTANCE.getConfig().getString("anticheats." + anticheatList.get(i) + ".price");
                inventory.setItem(i, new ItemBuilder(material).setName(name).addLoreLine("Version: " + version).addLoreLine("Price: " + price).toItemStack());
            }
            player.openInventory(inventory);
            return true;
        }
        return false;
    }

}
