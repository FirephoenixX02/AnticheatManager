package me.firephoenix.anticheatmanager;

import lombok.Getter;
import lombok.Setter;
import me.firephoenix.anticheatmanager.cmds.AnticheatManagerGUI;
import me.firephoenix.anticheatmanager.cmds.AnticheatSelector;
import me.firephoenix.anticheatmanager.listener.InvClick;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

@Getter
@Setter
public final class AnticheatManager extends JavaPlugin {

    public static AnticheatManager INSTANCE;

    public LuckPerms luckPermsAPI;

    @Override
    public void onEnable() {
        // Plugin startup logic
        setInstance(this);

        saveDefaultConfig();

        getCommand("anticheatselect").setExecutor(new AnticheatSelector());

        getCommand("anticheatgui").setExecutor(new AnticheatManagerGUI());

        getServer().getPluginManager().registerEvents(new InvClick(), this);

        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            luckPermsAPI = provider.getProvider();
        } else {
            getLogger().log(Level.SEVERE, "LuckPerms API is not available, disabling...");
            onDisable();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void setInstance(AnticheatManager INSTANCE) {
        AnticheatManager.INSTANCE = INSTANCE;
    }
}
