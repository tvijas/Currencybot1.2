package com.example.currencybot.service;

import com.example.currencybot.botUser.UserSave;
import com.example.currencybot.botConfiguration.BotConfig;
import com.example.currencybot.hibernateConfiguration.HibernateUtil;
import org.json.JSONException;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.*;

@Component
public class TelegramBot extends TelegramLongPollingBot {
    final BotConfig config;
    String[] currencyRatesList = {"","",""};
    public void setCurrencyRatesList(String euro, String dollar, String zloty) {
        this.currencyRatesList[0] = euro;
        this.currencyRatesList[1] = dollar;
        this.currencyRatesList[2] = zloty;
    }



    public TelegramBot(BotConfig config) {
        this.config = config;

        // Запускаем задачу, которая будет выполняться каждый день в заданное время
        Timer timer = new Timer();
        // Устанавливаем временную зону на GMT (или любую другую, подходящую для вас)
        TimeZone gmtTimeZone = TimeZone.getTimeZone("GMT");
        Calendar calendar = Calendar.getInstance(gmtTimeZone);
        calendar.set(Calendar.HOUR_OF_DAY, 11); // Устанавливаем часы (11:00 GMT)
        calendar.set(Calendar.MINUTE, 0);      // Устанавливаем минуты
        calendar.set(Calendar.SECOND, 0);      // Устанавливаем секунды
        Date scheduledTime = calendar.getTime();

        // Если заданное время уже прошло сегодня, то устанавливаем на завтра
        if (scheduledTime.before(new Date())) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            scheduledTime = calendar.getTime();
        }

        // Устанавливаем задачу для повторения каждый день в 11:00 по времени в GMT
        timer.scheduleAtFixedRate(new DailyCurrencyUpdateTask(this), scheduledTime, 24 * 60 * 60 * 1000);


    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            String massageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if (massageText.equals("/start")) {
                try {
                    UserSave.saveData(update);
                    startCommandReceivedHello(chatId);
                } catch (TelegramApiException | JSONException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void startCommandReceived(long chatId) throws TelegramApiException {

        String answer = "EURO     = " + currencyRatesList [0]
                    + "\nDOLLAR = " + currencyRatesList [1]
                    + "\nZLOTY    = " + currencyRatesList [2];
        sendMessage(chatId, answer);
    }
    public void startCommandReceivedHello(long chatId) throws TelegramApiException, JSONException, IOException {

        String answer = "Hello, It is currency rates bot." +
                " I provide daily updates on currency exchange rates at 11:00 AM (GMT)." +
                " I fetch my data from the National Polish Bank." +
                " When you receive my message, you will find three conversion rates:\n" +
                "\nThey converts:\n" +
                "\n1: Dollar to zloty" +
                "\n2: Euro   to zloty" +
                "\n3: Zloty   to hrywna";
        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String textToSend) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);
        execute(sendMessage);
    }

    @Override
    public String getBotToken() {
        return config.getKey();
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

}







