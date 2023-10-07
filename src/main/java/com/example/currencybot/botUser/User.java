package com.example.currencybot.botUser;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface User {
long getChatId (Update update);
}
