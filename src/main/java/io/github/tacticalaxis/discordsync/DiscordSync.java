package io.github.tacticalaxis.discordsync;

import io.github.tacticalaxis.discordsync.discord.ServerBot;
import io.github.tacticalaxis.discordsync.util.ConfigurationManager;
import io.github.tacticalaxis.discordsync.util.MessageHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class DiscordSync extends JavaPlugin implements CommandExecutor {

    private static DiscordSync instance;

    public static DiscordSync getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        ConfigurationManager.getInstance().setupConfiguration();
        getServer().getPluginManager().registerEvents(new MessageHandler(), this);

        try {
            ServerBot.botTask().runTask(this);
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Successfully enabled task!");
        } catch (Exception ignored) {}

        Objects.requireNonNull(getCommand("ds")).setExecutor(this);
    }

    @Override
    public void onDisable() {
        try {
            ServerBot.botTask().cancel();
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Successfully disabled task!");
        } catch (Exception ignored) {}
        instance = null;
    }

    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        ConfigurationManager.getInstance().reloadConfiguration();
        sender.sendMessage(ChatColor.GREEN + "DiscordSync config reloaded");
        return true;
    }

}