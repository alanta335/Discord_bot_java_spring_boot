package com.discord_bot.discord_music_bot.feature_core.listener;

import discord4j.core.event.domain.Event;
import reactor.core.publisher.Mono;

public interface EventListener<T extends Event> {
    Class<T> getEventType();
    Mono<Void> execute(T event);

    default Mono<Void> handleError(Throwable error) {
        error.printStackTrace();
        return Mono.empty();
    }
}

