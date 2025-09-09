package com.discord_bot.discord_music_bot.feature_core.listener.impl;

import com.discord_bot.discord_music_bot.feature_core.command.CommandRegistry;
import com.discord_bot.discord_music_bot.feature_core.listener.EventListener;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Service
public class MessageCreateListener implements EventListener<MessageCreateEvent> {
    private final CommandRegistry registry;

    public MessageCreateListener(CommandRegistry registry) {
        this.registry = registry;
    }

    @Override
    public Class<MessageCreateEvent> getEventType() {
        return MessageCreateEvent.class;
    }

    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        Message message = event.getMessage();

        // Ignore bots
        if (message.getAuthor().map(user -> user.isBot()).orElse(false)) {
            return Mono.empty();
        }

        String content = message.getContent().trim();
        if (!content.startsWith("!")) {
            return Mono.empty(); // Not a command
        }

        // Parse command and args
        String[] split = content.substring(1).split("\\s+");
        String commandName = split[0];
        List<String> args = split.length > 1 ? Arrays.asList(Arrays.copyOfRange(split, 1, split.length)) : List.of();

        return registry.getCommand(commandName)
                .map(cmd -> cmd.execute(message, args))
                .orElse(Mono.empty());
    }
}

