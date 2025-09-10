package com.discord_bot.discord_music_bot.config;

import com.discord_bot.discord_music_bot.feature_core.music.LavaPlayerAudioProvider;
import com.discord_bot.discord_music_bot.feature_core.music.TrackScheduler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.track.playback.NonAllocatingAudioFrameBuffer;
import discord4j.voice.AudioProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AudioConfig {
    @Bean
    public AudioPlayerManager audioPlayerManager() {
        DefaultAudioPlayerManager manager = new DefaultAudioPlayerManager();
        manager.getConfiguration().setFrameBufferFactory(NonAllocatingAudioFrameBuffer::new);
        AudioSourceManagers.registerRemoteSources(manager);
        AudioSourceManagers.registerLocalSource(manager);
        return manager;
    }

    @Bean
    public AudioPlayer audioPlayer(AudioPlayerManager manager) {
        return manager.createPlayer();
    }

    @Bean
    public AudioProvider audioProvider(AudioPlayer player) {
        return new LavaPlayerAudioProvider(player);
    }

    @Bean
    public TrackScheduler trackScheduler(AudioPlayer player) {
        return new TrackScheduler(player);
    }
}
