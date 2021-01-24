package io.github.tacticalaxis.discordsync.discord;

import io.github.tacticalaxis.discordsync.util.ConfigurationManager;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.Objects;

public class ServerBot extends ListenerAdapter {

    public static BukkitRunnable botTask() {
        return new BukkitRunnable() {
            public void run() {
                main();
            }
        };
    }

    public static void main() {
        try {
            String token = ConfigurationManager.getInstance().getMainConfiguration().getString("bot-token");
            JDABuilder.createLight(token, GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES)
                    .disableCache(Arrays.asList(CacheFlag.values()))
                    .addEventListeners(new ServerBot())
                    .setActivity(Activity.playing(" on " + ConfigurationManager.getInstance().getMainConfiguration().getString("server-name")))
                    .build();
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Please check your token is correct! After setting the token, run /ds, then stop and start the server up again.");
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        if (!msg.getAuthor().isBot()) {
            String message = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ConfigurationManager.getInstance().getMainConfiguration().getString("server-message")));
            message = message.replace("%name%",msg.getAuthor().getName()).replace("%msg%", msg.getContentStripped());
            Bukkit.broadcastMessage(message);
        }
    }
}