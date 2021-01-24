package io.github.tacticalaxis.discordsync.discord;

public class WebhookException extends RuntimeException {
	public WebhookException(String reason) {
		super(reason);
	}
}