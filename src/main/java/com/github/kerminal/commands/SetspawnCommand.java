package com.github.kerminal.commands;


import com.github.kerminal.Kerminal;
import com.github.kerminal.utils.ConfigUtil;
import com.github.kerminal.utils.LocationUtils;
import lombok.AllArgsConstructor;
import me.saiintbrisson.minecraft.command.command.CommandInfo;
import me.saiintbrisson.minecraft.command.command.Context;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class SetspawnCommand {

    private Kerminal plugin;
    private ConfigUtil commands;

    public SetspawnCommand(Kerminal plugin) {
        this.plugin = plugin;
        this.commands = plugin.getCommands();
        if(!commands.getBoolean("Setspawn.enabled", true)) return;
        plugin.getBukkitFrame().registerCommand(
                CommandInfo.builder()
                        .name(commands.getString("Setspawn.command"))
                        .aliases(commands.getStringList("Setspawn.aliases").toArray(new String[0]))
                        .permission(commands.getString("Setspawn.permission"))
                        .async(commands.getBoolean("Setspawn.async"))
                        .build(),
                context -> {
                    onCommand(context);
                    return false;
                }
        );
    }

    public void onCommand(Context<CommandSender> context) {
        final ConfigUtil messages = plugin.getMessages();
        final ConfigUtil config = plugin.getConfig();
        Player player = (Player) context.getSender();

        LocationUtils.setLocationConfig(config, player.getLocation(), "Spawn");
        plugin.setSpawn(LocationUtils.getLocationFromConfig(config, "Spawn"));
        player.sendMessage("§aSpawn definido!");
    }

}
