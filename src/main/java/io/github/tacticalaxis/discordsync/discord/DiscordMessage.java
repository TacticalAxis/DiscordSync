package io.github.tacticalaxis.discordsync.discord;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DiscordMessage {
    String username;
    String content;
    @SerializedName("avatar_url")
    String avatarUrl;
    @SerializedName("tts")
    boolean textToSpeech;

    public DiscordMessage() {

    }

    public DiscordMessage(String username, String content, String avatar_url) {
        this(username, content, avatar_url, false);
    }

    public DiscordMessage(String username, String content, String avatar_url, boolean tts) {
        setUsername(username);
        setContent(content);
        setAvatarUrl(avatar_url);
        setTextToSpeech(tts);
    }

    public void setUsername(String username) {
        if (username != null) {
            this.username = username.substring(0, Math.min(31, username.length()));
        } else {
            this.username = null;
        }
    }
}
