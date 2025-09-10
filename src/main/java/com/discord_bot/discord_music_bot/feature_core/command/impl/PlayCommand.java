package com.discord_bot.discord_music_bot.feature_core.command.impl;

import com.discord_bot.discord_music_bot.feature_core.command.Command;
import com.discord_bot.discord_music_bot.feature_core.music.TrackScheduler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import discord4j.core.object.entity.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayCommand implements Command {

    private final AudioPlayerManager manager;
    private final TrackScheduler scheduler;

    @Override
    public String getName() {
        return "play";
    }

    @Override
    public Mono<Void> execute(Message message, List<String> args) {
        if (args.isEmpty()) {
            return message.getChannel()
                    .flatMap(ch -> ch.createMessage("Please provide the file path or URL."))
                    .then();
        }

        String input = args.getFirst();
        String source = input.startsWith("http") ? input : input;

        manager.loadItem(source, scheduler);

        return message.getChannel()
                .flatMap(ch -> ch.createMessage("Queued: " + input))
                .then();
    }

    @Override
    public List<String> getAliases() {
        return List.of("pl");
    }

    @Override
    public String getDescription() {
        return "Play a song in voice channel";
    }
}
