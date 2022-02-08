package io.github.indroDevTeam.indroLib;

import io.github.indroDevTeam.indroLib.datamanager.SQLConnector;
import io.github.indroDevTeam.indroLib.datamanager.SQLUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public SQLConnector sqlConnector = new SQLConnector(
            "Indrocraft",
            "indrocraft.hopto.org",
            "3306",
            "omen",
            "Minecraft$44",
            false,
            this
    );
    SQLUtils sqlUtils = new SQLUtils(sqlConnector);

    private Main instance;

    @Override
    public void onEnable() {
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
