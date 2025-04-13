package org.example.service;

import standardRegisterAndLogin.db.CurrentUsers;
import standardRegisterAndLogin.db.DB;
import standardRegisterAndLogin.entity.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.UUID;

public class UserService2 {
    public boolean login(String email, String password) throws MessagingException {
        for (User testUser : DB.users) {
            if (testUser.getEmail().equals(email)) {
                if (testUser.getActive()) {
                    if (testUser.getPassword().equals(password)) {
                        CurrentUsers.currentUser = testUser;
                        return true;
                    }
                } else {
                    if (testUser.getPassword().equals(password)) {
                        CurrentUsers.currentUser = testUser;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void register(String fullname, String email) throws MessagingException {
        String generateNum = String.valueOf((int) (Math.random() * 10000) + 100000);
        User user = new User(UUID.randomUUID(), fullname, email, generateNum, false);
        sendEmailMessage(user);
        DB.users.add(user);
    }

    public void sendEmailMessage(User user) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "sandbox.smtp.mailtrap.io");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.tsl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        String username = "e10badb7f0a951";
        String password = "ef8c07d6c68daa";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setSubject("Murod dan xat");
        message.setText("""
                Assalamu Alaykum %s, saytimizga xush kelibsiz!
                Iltimos akkauntizni active qilish uchun shu kodni kiriting %s.
                """.formatted(user.getFullName(), user.getPassword()));

        message.setFrom(new InternetAddress(username));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
        Transport.send(message);
    }

    public boolean recoverPassword(String code, String newPassword) {
        if (CurrentUsers.currentUser != null) {
            CurrentUsers.currentUser.setPassword(newPassword);
            CurrentUsers.currentUser.setActive(true);
            return true;
        }
        return false;
    }
}