package com.discord_bot.discord_music_bot.feature_core.music;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TrackScheduler implements AudioLoadResultHandler {
    private final AudioPlayer player;

    @Override
    public void trackLoaded(AudioTrack track) {
        player.playTrack(track);
    }

    @Override
    public void playlistLoaded(AudioPlaylist playlist) {
        log.info("Loaded playlist {}", playlist.getName());
    }

    @Override
    public void noMatches() {
        log.error("No matches found!");
    }

    @Override
    public void loadFailed(final FriendlyException exception) {
        log.error(exception.getMessage(), exception);
    }
}
