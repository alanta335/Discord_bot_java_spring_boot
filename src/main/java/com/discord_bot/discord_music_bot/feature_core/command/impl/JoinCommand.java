package com.discord_bot.discord_music_bot.feature_core.command.impl;

import com.discord_bot.discord_music_bot.feature_core.command.Command;
import discord4j.core.object.VoiceState;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.spec.AudioChannelJoinSpec;
import discord4j.voice.AudioProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class JoinCommand implements Command {

    private final AudioProvider provider;

    @Override
    public String getName() {
        return "join";
    }

    @Override
    public Mono<Void> execute(Message message, List<String> args) {
        return message.getAuthorAsMember()
                .flatMap(Member::getVoiceState)
                .flatMap(VoiceState::getChannel)
                .flatMap(audioChannel -> {
                    AudioChannelJoinSpec spec = AudioChannelJoinSpec.builder()
                            .provider(provider)
                            .selfDeaf(true)
                            .build();
                    return audioChannel.join(spec).doOnError(t -> log.error("failed to join voice channel", t));
                })
                .then();
    }

    @Override
    public List<String> getAliases() {
        return List.of("j");
    }

    @Override
    public String getDescription() {
        return "Make bot join your voice channel";
    }
}

