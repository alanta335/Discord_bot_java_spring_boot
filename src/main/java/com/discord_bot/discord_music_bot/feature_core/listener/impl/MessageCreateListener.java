package com.discord_bot.discord_music_bot.feature_core.listener.impl;

import com.discord_bot.discord_music_bot.feature_core.command.Command;
import com.discord_bot.discord_music_bot.feature_core.listener.EventListener;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class MessageCreateListener implements EventListener<MessageCreateEvent> {

    private final List<Command> commands;

    public MessageCreateListener(List<Command> commands) {
        this.commands = commands;
    }

    @Override
    public Class<MessageCreateEvent> getEventType() {
        return MessageCreateEvent.class;
    }

    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        Message message = event.getMessage();

        return Flux.fromIterable(commands)
                .filter(cmd -> message.getContent().equalsIgnoreCase("!" + cmd.getName()))
                .next()
                .flatMap(cmd -> cmd.execute(message))
                .then();
    }
}

