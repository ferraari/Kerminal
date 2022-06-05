package com.github.idimabr;

import com.github.idimabr.commands.GamemodeCommand;
import com.github.idimabr.commands.HelpCommand;
import com.github.idimabr.commands.TeleportCommand;
import com.github.idimabr.commands.TpaCommand;
import com.github.idimabr.listeners.ConfigurableCommandsHandler;
import com.github.idimabr.listeners.GameMechanicsListener;
import com.github.idimabr.listeners.InventoryListener;
import com.github.idimabr.listeners.RegenerationListener;
import com.github.idimabr.tasks.RegenerationTask;
import com.github.idimabr.utils.ConfigUtil;
import me.saiintbrisson.bukkit.command.BukkitFrame;
import me.saiintbrisson.minecraft.command.message.MessageHolder;
import me.saiintbrisson.minecraft.command.message.MessageType;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Kerminal extends JavaPlugin {

    private BukkitFrame bukkitFrame;
    @lombok.Getter
    private ConfigUtil config;
    @lombok.Getter
    private ConfigUtil configurableCommands;
    @lombok.Getter
    private ConfigUtil messages;


    public void onLoad() {
        saveDefaultConfig();
        loadConfigs();
    }

    @Override
    public void onEnable() {
        registerListeners();
        registerCommands();
        loadTicksWorld();
        loadRegenSystem();
    }

    @Override
    public void onDisable() {
    }

    private void loadConfigs(){
        config = new ConfigUtil(this, "config");
        configurableCommands = new ConfigUtil(this,"configurableCommands");
        messages = new ConfigUtil(this,"messages");

    }

    private void registerCommands() {
        bukkitFrame = new BukkitFrame(this);
        MessageHolder messageHolder = bukkitFrame.getMessageHolder();
        messageHolder.setMessage(
                MessageType.NO_PERMISSION, "§cVocê não tem permissão para executar este comando!"
        );
        messageHolder.setMessage(
                MessageType.INCORRECT_TARGET, "§cVocê não pode executar este comando!"
        );
        messageHolder.setMessage(
                MessageType.ERROR, "§cOcorreu um erro ao executar este comando!"
        );
        bukkitFrame.registerCommands(
                new TpaCommand(this),
                new GamemodeCommand(this),
                new HelpCommand(this),
                new TeleportCommand(this)
        );
    }
    private void registerListeners() {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(
                new InventoryListener(this), this
        );
        pluginManager.registerEvents(
                new GameMechanicsListener(this), this
        );
        pluginManager.registerEvents(
                new ConfigurableCommandsHandler(this), this
        );
    }

    private void loadRegenSystem() {
        if(config.getBoolean("Regeneration.Enabled")){
            final long delay = config.getLong("Regeneration.Delay");
            new RegenerationTask().runTaskTimerAsynchronously(this, delay, delay);
            PluginManager pluginManager = getServer().getPluginManager();
            pluginManager.registerEvents(
                    new RegenerationListener(), this
            );
            getLogger().info("Regeneration System: ENABLED");
        }else{
            getLogger().info("Regeneration System: DISABLED");
        }
    }

    private void loadTicksWorld(){
        for (World world : Bukkit.getWorlds()) {
            if(!config.isSet("Worlds." + world.getName())) continue;

            final int ticks = config.getInt("Worlds." + world.getName() + ".Tick");
            world.setGameRuleValue("randomTickSpeed", String.valueOf(ticks));
        }
    }
}
