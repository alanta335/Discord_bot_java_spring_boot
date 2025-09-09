package com.discord_bot.discord_music_bot.feature_core.service.impl;

import org.springframework.stereotype.Service;

@Service
public class WeatherServiceImpl {
    public String getWeather(String city) {
        return "Sunny in " + city + " ☀️"; // (Mocked example)
    }
}
