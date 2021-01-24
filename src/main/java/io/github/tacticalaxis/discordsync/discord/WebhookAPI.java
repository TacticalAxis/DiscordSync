package io.github.tacticalaxis.discordsync.discord;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import org.bukkit.Bukkit;

public class WebhookAPI {
    public static final Gson gson = new Gson();
    public final String url;
    final boolean blockMainThread;

    public WebhookAPI(String url) {
        this(url, false);
    }

    public WebhookAPI(String url, boolean blocking) {
        this.url = url;
        this.blockMainThread = blocking;
    }

    public void sendMessage(final DiscordMessage dm) {
        Runnable r = new Runnable() {
            public void run() {
                try {
                    String strResponse = HttpRequest.post(url)
                            .acceptJson()
                            .contentType("application/json")
                            .header("User-Agent", "Mozilla/5.0 (X11; U; Linux i686) Gecko/20071127 Firefox/2.0.0.11")
                            .send(gson.toJson(dm))
                            .body();

                    if (!strResponse.isEmpty()) {
                        Response response = gson.fromJson(strResponse, Response.class);
                        try {
                            if (response.message.equals("You are being rate limited.")) {
                                try {
                                    Thread.sleep(response.retryAfter);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                sendMessage(dm);
                            } else {
                                throw new WebhookException(response.message);
                            }
                        } catch (Exception e) {
                            throw new WebhookException(strResponse);
                        }
                    }
                } catch (Exception e) {
                    Bukkit.getConsoleSender().sendMessage("Please check the webhook " + url + " exists!");
                }
            }
        };

        if (blockMainThread) {
            r.run();
        } else {
            Thread t = new Thread(r);
            t.start();
        }
    }
}
