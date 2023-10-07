package com.example.currencybot.hibernateConfiguration;

import com.example.currencybot.entity.UserEntity;

import jakarta.annotation.PostConstruct;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HibernateUtil {

    private static HibernateConfig hibernateConfig;
    private static SessionFactory sessionFactory;

    @Autowired
    public HibernateUtil(HibernateConfig hibernateConfig) {
        this.hibernateConfig = hibernateConfig;
    }

    @PostConstruct
    private static void initializeSessionFactory() {
        try {
            if (sessionFactory == null) { // Проверяем, что sessionFactory ещё не был инициализирован
                Configuration configuration = new Configuration();

                configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
                configuration.setProperty("hibernate.connection.url", "jdbc:mysql://"
                        + hibernateConfig.getHost() + ":" + hibernateConfig.getPort() + "/" + hibernateConfig.getName());
                configuration.setProperty("hibernate.connection.username", hibernateConfig.getUsername());
                configuration.setProperty("hibernate.connection.password", hibernateConfig.getPassword());
                configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
                configuration.setProperty("current_session_context_class", "thread");

                configuration.addAnnotatedClass(UserEntity.class);

                StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties())
                        .build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            }
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    // Метод для получения SessionFactory
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}