package com.example.currencybot.entity;

import com.example.currencybot.botUser.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user", schema = "currencybot")
public class UserEntity {
    @Id
    @Column(name = "chatId")
    private long chatId;
    public UserEntity (User user, Update update){
        this.chatId= user.getChatId(update);
    }
}
