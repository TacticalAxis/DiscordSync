package io.github.tacticalaxis.discordsync.util;

import io.github.tacticalaxis.discordsync.discord.DiscordMessage;
import io.github.tacticalaxis.discordsync.discord.WebhookAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class MessageHandler implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        for (String hook : ConfigurationManager.getInstance().getMainConfiguration().getStringList("webhooks")) {
            if (hook.length() > 1) {
                try {
                    WebhookAPI webhook = new WebhookAPI(hook);
                    DiscordMessage dm = new DiscordMessage(event.getPlayer().getName(), event.getMessage().replace("@",""), "https://crafatar.com/avatars/" + event.getPlayer().getUniqueId().toString());
                    webhook.sendMessage(dm);
                } catch (Exception ignored) {}
            }
        }
    }
}