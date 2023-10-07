package com.example.currencybot.service;

import com.example.currencybot.NBPCurrencyExchangeRates.CurrencyCreator;
import com.example.currencybot.hibernateConfiguration.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.text.DecimalFormat;
import java.util.*;

public class DailyCurrencyUpdateTask extends TimerTask {
    private final TelegramBot bot;

    public DailyCurrencyUpdateTask(TelegramBot bot) {
        this.bot = bot;
    }

    @Override
    public void run() {

        CurrencyCreator currencyCreator = new CurrencyCreator();
        double zloty = currencyCreator.getZloty("https://api.nbp.pl/api/exchangerates/rates/a/uah/?format=json");
        zloty = 1f / zloty;

        DecimalFormat df = new DecimalFormat("#.####");
        String formattedZloty = df.format(zloty);

        bot.setCurrencyRatesList(
                String.valueOf(currencyCreator.getEuro("https://api.nbp.pl/api/exchangerates/rates/a/usd/?format=json")),
                String.valueOf(currencyCreator.getDollar("https://api.nbp.pl/api/exchangerates/rates/a/eur/?format=json")),
                formattedZloty
        );

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = null;
        try {
            // Остальной код остается неизменным
            session = sessionFactory.openSession();
            String hql = "SELECT u.chatId FROM UserEntity u";
            Query query = session.createQuery(hql);
            List<Long> results = query.list();

            for (Long chatId : results) {
                bot.startCommandReceived(chatId);
            }

        } catch (Exception ex) {
            if (session != null && session.isOpen()) {
                session.getTransaction().rollback();
            }
            ex.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }}}}


