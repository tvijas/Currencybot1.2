package com.example.currencybot.botUser;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
@Component ("userBean")
public class UserCreator implements User{
    @Override
    public long getChatId(Update update) {
        return update.getMessage().getChatId();
    }
}
