package com.example.currencybot;

import com.example.currencybot.botConfiguration.BotConfig;
import com.example.currencybot.hibernateConfiguration.HibernateConfig;
import com.example.currencybot.hibernateConfiguration.HibernateUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties({BotConfig.class, HibernateConfig.class})
public class CurrencyBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurrencyBotApplication.class, args);
    }

}
