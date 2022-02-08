package io.github.indroDevTeam.indroLib.objects.ranks;

import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RankEvent extends Event implements Cancellable {

    private final Player player;
    private final Rank rank;
    private final Advancement advancement;
    private boolean isCanceled;
    public boolean hasAdvancement;
    private static final HandlerList HANDLER_LIST = new HandlerList();

    public RankEvent(Player player, Rank rank, Advancement advancement) {
        this.player = player;
        this.rank = rank;
        this.advancement = advancement;
        this.isCanceled = false;
        this.hasAdvancement = true;
    }

    public RankEvent(Player player, Rank rank, String advancement) {
        this.player = player;
        this.rank = rank;
        this.advancement = RankUtils.getAdvancement("minecraft:" + advancement);
        this.isCanceled = false;
        this.hasAdvancement = true;
    }

    public RankEvent(Player player, Rank rank) {
        this.player = player;
        this.rank = rank;
        this.advancement = null;
        this.hasAdvancement = false;
        this.isCanceled = false;
    }

    @Override
    public boolean isCancelled() {
        return this.isCanceled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.isCanceled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    public Player getPlayer() {
        return player;
    }

    public Advancement getAdvancement() {
        return advancement;
    }

    public Rank getRank() {
        return rank;
    }
}
