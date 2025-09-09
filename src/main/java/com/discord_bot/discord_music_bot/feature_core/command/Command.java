package com.discord_bot.discord_music_bot.feature_core.command;

import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

public interface Command {
    String getName();

    Mono<Void> execute(Message message);
}

