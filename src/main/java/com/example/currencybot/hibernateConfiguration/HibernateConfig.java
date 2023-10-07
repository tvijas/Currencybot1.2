package com.example.currencybot.hibernateConfiguration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "db")
public class HibernateConfig {
    private String host;
    private String port;
    private String name;
    private String username;
    private String password;
}
