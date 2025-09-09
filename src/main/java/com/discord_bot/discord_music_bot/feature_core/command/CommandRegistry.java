package com.discord_bot.discord_music_bot.feature_core.command;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CommandRegistry {
    private final Map<String, Command> commands = new HashMap<>();

    public CommandRegistry(List<Command> commandBeans) {
        for (Command cmd : commandBeans) {
            commands.put(cmd.getName().toLowerCase(), cmd);
            for (String alias : cmd.getAliases()) {
                commands.put(alias.toLowerCase(), cmd);
            }
        }
    }

    public Optional<Command> getCommand(String name) {
        return Optional.ofNullable(commands.get(name.toLowerCase()));
    }

    public Collection<Command> getAllCommands() {
        return commands.values();
    }
}
