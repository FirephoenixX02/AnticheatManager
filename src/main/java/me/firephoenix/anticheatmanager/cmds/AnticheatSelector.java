package me.firephoenix.anticheatmanager.cmds;

import me.firephoenix.anticheatmanager.AnticheatManager;
import net.luckperms.api.model.data.NodeMap;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeBuilder;
import net.luckperms.api.node.NodeEqualityPredicate;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;

/**
 * @author NieGestorben
 * Copyright© (c) 2023, All Rights Reserved.
 */
public class AnticheatSelector implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String string, String[] args) {
        Player player = AnticheatManager.INSTANCE.getServer().getPlayer(commandSender.getName());
        if (player == null) return false;
        List<String> anticheatList = AnticheatManager.INSTANCE.getConfig().getStringList("name-list");
        if (args.length != 1) {
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUsage: acsel <name>"));
            return true;
        }
        if (anticheatList.isEmpty()) {
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cNo Anticheats configured!"));
            return true;
        }
        if (anticheatList.contains(args[0])) {
            anticheatList.remove(args[0]);

            String bypassPermission = AnticheatManager.INSTANCE.getConfig().getString("anticheats." + args[0] + ".bypass-permission");

            // Manage permissions via luckperms, because that is what I use and this will only be a private plugin
            if (AnticheatManager.INSTANCE.getLuckPermsAPI().getUserManager().getUser(player.getUniqueId()) == null) {
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cCouldn't manage permissions via the LuckPerms API!"));
                return true;
            }
            Node permissionNode = Node.builder(bypassPermission).negated(true).build();
            NodeMap permissions = AnticheatManager.INSTANCE.getLuckPermsAPI().getUserManager().getUser(player.getUniqueId()).data();

            String anticheatname = AnticheatManager.INSTANCE.getConfig().getString("anticheats." + args[0] + ".name");
            String anticheatVersion = AnticheatManager.INSTANCE.getConfig().getString("anticheats." + args[0] + ".version");

            //Remove all other anticheat bypass permissions

            anticheatList.forEach(anticheat -> {
                String permission = AnticheatManager.INSTANCE.getConfig().getString("anticheats." + anticheat + ".bypass-permission");
                Node permissionNode2 = Node.builder(permission).negated(true).build();
                permissions.remove(permissionNode2);
            });

            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cChanged Anticheat to " + anticheatname + " V" + anticheatVersion));

            //Add bypass permission for selected anticheat

            permissions.add(permissionNode);

            AnticheatManager.INSTANCE.getLuckPermsAPI().getUserManager().saveUser(Objects.requireNonNull(AnticheatManager.INSTANCE.getLuckPermsAPI().getUserManager().getUser(player.getUniqueId())));
            return true;
        } else {
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cCouldn't find an anticheat with the name: " + args[0] + "."));
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "List of all anticheats: " + anticheatList));
            return true;
        }
    }

}
