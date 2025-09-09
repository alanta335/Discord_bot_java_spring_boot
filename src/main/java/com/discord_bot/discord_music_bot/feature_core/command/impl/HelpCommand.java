package com.discord_bot.discord_music_bot.feature_core.command.impl;

import com.discord_bot.discord_music_bot.feature_core.command.Command;
import discord4j.core.object.entity.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HelpCommand implements Command {

    private final List<Command> commands;

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public List<String> getAliases() {
        return List.of("h");
    }

    @Override
    public String getDescription() {
        return "Lists all available commands.";
    }

    @Override
    public Mono<Void> execute(Message message, List<String> args) {
        StringBuilder sb = new StringBuilder("Available commands:\n");
        commands.forEach(cmd ->
                sb.append("!").append(cmd.getName())
                        .append(" - ").append(cmd.getDescription())
                        .append("\n")
        );
        return message.getChannel().flatMap(ch -> ch.createMessage(sb.toString())).then();
    }
}