package com.discord_bot.discord_music_bot.feature_core.command;

import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

import java.util.List;

public interface Command {
    String getName();

    default List<String> getAliases() {
        return List.of();
    }

    String getDescription();

    Mono<Void> execute(Message message, List<String> args);
}

