package com.example.currencybot.botUser;

import com.example.currencybot.entity.UserEntity;
import com.example.currencybot.hibernateConfiguration.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.telegram.telegrambots.meta.api.objects.Update;

public class UserSave {
    public static  void saveData(Update update) {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
            UserEntity user = new UserEntity(context.getBean("userBean", User.class), update);

            // Проверяем, существует ли запись с таким chatId
            Long chatIdToCheck = user.getChatId(); // Получаем chatId из объекта user
            UserEntity existingUser = session.get(UserEntity.class, chatIdToCheck);

            if (existingUser == null) {
                session.save(user);
            }

            session.getTransaction().commit();
        } catch (Exception ex) {
            if (session != null && session.isOpen()) {
                session.getTransaction().rollback();
            }
            ex.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }

        }

    }

}
