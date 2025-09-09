package com.discord_bot.discord_music_bot.config;

import com.discord_bot.discord_music_bot.feature_core.listener.EventListener;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.Event;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class BotConfig {

    @Value("${discord.token}")
    private String token;

    @Bean
    public GatewayDiscordClient gatewayDiscordClient(List<EventListener<? extends Event>> listeners) {
        GatewayDiscordClient client = DiscordClientBuilder.create(token)
                .build()
                .login()
                .block();

        listeners.forEach(listener -> {
            assert client != null;
            registerListener(client, listener);
        });
        return client;
    }

    private <T extends Event> void registerListener(GatewayDiscordClient client, EventListener<T> listener) {
        client.on(listener.getEventType())
                .flatMap(listener::execute)
                .onErrorResume(listener::handleError)
                .subscribe();
    }
}

