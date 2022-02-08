package io.github.indroDevTeam.indroLib;

import io.github.indroDevTeam.indroLib.datamanager.SQLUtils;
import io.github.indroDevTeam.indroLib.objects.homes.HomeUtils;
import io.github.indroDevTeam.indroLib.objects.ranks.RankUtils;
import io.github.indroDevTeam.indroLib.objects.warps.WarpUtils;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public class MiscUtils {
    /**
     * @param s SQL connection
     * @return true if setup was successful and false if an error occurred
     * @apiNote This method fully sets up all the tables used for this plugin
     */
    public static boolean setupDatabase(SQLUtils s) {
        try {
            HomeUtils.createHomeTable(s);
            RankUtils.createRankTable(s);
            WarpUtils.createWarpTable(s);
            MiscUtils.createMainTable(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void createMainTable(SQLUtils s) {
        s.createTable("players", "UUID",
                "ign VARCHAR(100)",
                "nameColour VARCHAR(100)",
                "rank VARCHAR(100)"
        );
    }

    /**
     * @param player        Target player
     * @param enablePassive true = enabled, false = disabled
     * @param s             sql connection
     */
    public void setPassive(Player player, boolean enablePassive, SQLUtils s) {
        passiveSetup(s);
        if (enablePassive) {
            s.setData(1, "UUID", player.getUniqueId().toString(), "passive", "players");
        } else {
            s.setData(0, "UUID", player.getUniqueId().toString(), "passive", "players");
        }
    }

    /**
     * @param player Target player
     * @param s      sql connection
     * @return true if they are in passive mode and false if they aren't
     */
    public boolean getPassive(Player player, SQLUtils s) {
        int passive = (int) s.getData(
                "passive", "UUID", player.getUniqueId().toString(), "players"
        );
        return passive == 1;
    }

    /**
     * @param player Target player
     * @param s      sql connection
     */
    public void togglePassive(Player player, SQLUtils s) {
        boolean passive = getPassive(player, s);
        setPassive(player, !passive, s);
    }

    /**
     * @param s sql connection
     */
    private void passiveSetup(SQLUtils s) {
        boolean columnExist = s.columnExists("passive", "players");
        if (!columnExist) {
            s.createColumn("passive", "INT", "players");
        }
    }

}
