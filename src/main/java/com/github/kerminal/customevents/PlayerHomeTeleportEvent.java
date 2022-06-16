package com.github.kerminal.customevents;

import com.github.kerminal.models.Home;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class PlayerHomeTeleportEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    @Getter @Setter
    private Player player;
    @Getter @Setter
    private Home home;
    private boolean cancelled;

    public PlayerHomeTeleportEvent(Player player, Home home) {
        this.player = player;
        this.home = home;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}