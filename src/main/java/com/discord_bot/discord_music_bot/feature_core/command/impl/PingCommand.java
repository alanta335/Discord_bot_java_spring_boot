package com.discord_bot.discord_music_bot.feature_core.command.impl;

import com.discord_bot.discord_music_bot.feature_core.command.Command;
import discord4j.core.object.entity.Message;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class PingCommand implements Command {
    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public List<String> getAliases() {
        return List.of("p");
    }

    @Override
    public String getDescription() {
        return "Replies with Pong!";
    }

    @Override
    public Mono<Void> execute(Message message, List<String> args) {
        return message.getChannel()
                .flatMap(ch -> ch.createMessage("Pong!"))
                .then();
    }
}

