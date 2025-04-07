package org.example.service;

import org.example.db.DB;
import org.example.entity.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.UUID;

public class UserService {

    public void login() {
        System.out.print("Enter the email: ");
        String email = DB.scannerStr.nextLine();
        User user = new User();

        for (User testUser : DB.users) {
            if (testUser.getEmail().equals(email)) {
                if (testUser.getActive()) {
                    System.out.print("Enter the password");
                    String password = DB.scannerStr.nextLine();
                    if (testUser.getPassword().equals(password)) {
                        System.out.println("...");
                    }
                } else {
                    System.out.println("Email ga jonatgan parolni kiriting!");
                    String password = DB.scannerStr.nextLine();
                    if (testUser.getPassword().equals(password)) {
                        System.out.println("Yangi parol ornating");
                        String helper = DB.scannerStr.nextLine();
                        testUser.setPassword(helper);
                        testUser.setActive(true);
                    }
                }
            }
        }

    }

    public void register() throws MessagingException {
        System.out.print("Enter the fullname: ");
        String fullname = DB.scannerStr.nextLine();
        System.out.print("Enter the email: ");
        String email = DB.scannerStr.nextLine();

        String generateNum = String.valueOf((int) (Math.random() * 10000) + 100000);

        User user = new User(UUID.randomUUID(), fullname, email, generateNum, false);

        sendEmailMessage(user);

        DB.users.add(user);
        System.out.println("Iltimos emailingizni tekshiring");
    }

    public void sendEmailMessage(User user) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        String username = "murodbee07@gmail.com";
        String password = "irbxhfbnzkonchjt";

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
                Iltimos akkauntizni active qilish uchun shu kodni kiriting %s
                """.formatted(user.getFullName(), user.getPassword()));

        message.setFrom(new InternetAddress(username));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));

        Transport.send(message);
        System.out.println("Xabar junatildi");
    }
}
