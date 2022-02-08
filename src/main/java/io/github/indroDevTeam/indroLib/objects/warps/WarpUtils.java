package io.github.indroDevTeam.indroLib.objects.warps;

import io.github.indroDevTeam.indroLib.datamanager.SQLUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class WarpUtils {
    /**
     * @param warpName name of warp
     * @param sqlUtils connection to the sql database
     * @return returns a warp object from the database
     */
    public static Warp getWarp(String warpName, SQLUtils sqlUtils) {
        return new Warp(
                warpName,
                Bukkit.getWorld(
                        (String) sqlUtils.getData("world", "warpId", warpName, "warpTable")
                ),
                (Double) sqlUtils.getData("x", "warpId", warpName, "warpTable"),
                (Double) sqlUtils.getData("y", "warpId", warpName, "warpTable"),
                (Double) sqlUtils.getData("z", "warpId", warpName, "warpTable"),
                (Float) sqlUtils.getData("pitch", "warpId", warpName, "warpTable"),
                (Float) sqlUtils.getData("yaw", "warpId", warpName, "warpTable")
        );
    }

    /**
     * @param warpName name of warp
     * @param location Location of new object
     * @param sqlUtils connection to the sql database
     * @return returns the Warp object that was just created and saves it to the database
     */
    public static Warp createWarp(String warpName, Location location, SQLUtils sqlUtils) {
        if (!warpExist(warpName, sqlUtils)) {
            sqlUtils.createRow("warpId", warpName, "warpTable");
            sqlUtils.setData(location.getWorld().getName(), "warpID", warpName, "world", "warpTable");
            sqlUtils.setData(location.getX(), "warpID", warpName, "x", "warpTable");
            sqlUtils.setData(location.getY(), "warpID", warpName, "y", "warpTable");
            sqlUtils.setData(location.getZ(), "warpID", warpName, "z", "warpTable");
            sqlUtils.setData(location.getYaw(), "warpID", warpName, "yaw", "warpTable");
            sqlUtils.setData(location.getPitch(), "warpID", warpName, "pitch", "warpTable");
        }

        return getWarp(warpName, sqlUtils);
    }

    /**
     * @param warpId   Unique id of the warp
     * @param sqlUtils connection to the sql database
     */
    public static void deleteWarp(String warpId, SQLUtils sqlUtils) {
        sqlUtils.deleteRow("warpId", warpId, "warpTable");
    }

    /**
     * @param warp     Warp object being deleted
     * @param sqlUtils connection to the sql database
     */
    public static void deleteWarp(Warp warp, SQLUtils sqlUtils) {
        sqlUtils.deleteRow("warpId", warp.getId(), "warpTable");
    }

    /**
     * @param warp     Warp object being checked for
     * @param sqlUtils connection to the sql database
     * @return true if its exists and false if it doesn't
     */
    public static boolean warpExist(Warp warp, SQLUtils sqlUtils) {
        return sqlUtils.rowExists("warpId", warp.getId(), "warpTable");
    }

    /**
     * @param warpName Unique id of the warp
     * @param sqlUtils connection to the sql database
     * @return true if its exists and false if it doesn't
     */
    public static boolean warpExist(String warpName, SQLUtils sqlUtils) {
        return sqlUtils.rowExists("warpId", warpName, "warpTable");
    }

    /**
     * @param player     Player being teleported
     * @param warp       Warp object that the player is being sent to
     * @param restricted set to false if they can teleport between worlds and true if they can't
     */
    public static void teleportWarp(Player player, Warp warp, boolean restricted) {
        if (restricted) {
            if (warp.getWorld() == player.getWorld()) {
                player.teleport(warp.getLocation());
            } else {
                player.sendMessage(ChatColor.RED + "You try with all your might to teleport! " +
                        "But you aren't quite strong enough to go between worlds!");
            }
        } else {
            player.teleport(warp.getLocation());
        }
    }

    /**
     * @param s connection to the sql database
     */
    public static void createWarpTable(SQLUtils s) {
        s.createTable(
                "warpTable",
                "warpId",
                "world VARCHAR(100)",
                "x DOUBLE",
                "y DOUBLE",
                "z DOUBLE",
                "yaw FLOAT",
                "pitch FLOAT"
        );
    }
}
