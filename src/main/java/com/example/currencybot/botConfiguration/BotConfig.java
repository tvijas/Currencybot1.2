package com.example.currencybot.botConfiguration;

import lombok.Data;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@ConfigurationProperties(prefix = "bot")
public class BotConfig {
    @Value("${bot.name}")
    String botName;
    @Value("${bot.key}")
    String key;

}
