package com.github.idimabr.listeners;

import com.github.idimabr.Kerminal;
import com.github.idimabr.utils.ConfigUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;

import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
public class GameMechanicsListener implements Listener {

    private Kerminal plugin;

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        final Player player = e.getEntity().getPlayer();
        e.setDeathMessage(null);

        if (!player.hasPermission("kerminal.keepxp")) return;
        e.setKeepLevel(true);
        e.setDroppedExp(0);
    }

    @EventHandler
    public void onKick(PlayerKickEvent e) {
        e.setLeaveMessage("");
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage(null);
    }

    @EventHandler
    public void onMotd(ServerListPingEvent e){
        final ConfigUtil config = plugin.getConfig();
        e.setMaxPlayers(config.getInt("Motd.Maxplayers"));

        List<String> motdList = config.getStringList("Motd." + (Bukkit.hasWhitelist() ? "Maintenance" : "Normal"))
                .stream()
                .map($ -> $.replace("&", "§"))
                .collect(Collectors.toList());

        e.setMotd(motdList.get(RandomUtils.nextInt(motdList.size())));
    }

    @EventHandler
    public void onKickWhitelist(PlayerLoginEvent e) {
        final ConfigUtil messages = plugin.getMessages();
        if (e.getResult() != PlayerLoginEvent.Result.KICK_WHITELIST) return;

        e.setKickMessage(
                StringUtils.join(
                        messages.getStringList("Kick.Maintenance")
                                .stream()
                                .map($ -> $.replace("&","§"))
                                .collect(Collectors.toList())
                        , "\n")
        );
    }
}
