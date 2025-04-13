package org.example.service;

import org.example.db.CurrentUsers;
import org.example.db.DB;
import org.example.entity.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.UUID;

public class UserService {
    public void login() throws MessagingException {
        System.out.print("Enter the email: ");
        String email = DB.scannerStr.nextLine();

        for (User testUser : DB.users) {
            if (testUser.getEmail().equals(email)) {
                if (testUser.getActive()) {
                    System.out.print("Enter the password");
                    String password = DB.scannerStr.nextLine();
                    if (testUser.getPassword().equals(password)) {
                        System.out.println("...");
                    }
                } else {
                    System.out.print("Email ga jonatgan parolni kiriting: ");
                    String password = DB.scannerStr.nextLine();
                    if (testUser.getPassword().equals(password)) {
                        System.out.print("Yangi parol ornating: ");
                        String helper = DB.scannerStr.nextLine();
                        testUser.setPassword(helper);
                        testUser.setActive(true);
                        CurrentUsers.currentUser = testUser;
                        otherMenu();
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


    /* SENDING EMAIL MESSAGE */
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
        System.out.println("Xabar junatildi");
    }


    /* RECOVERING */
    public void recoverPassword() throws MessagingException {
        if (CurrentUsers.currentUser == null) {
            System.out.println("Iltimos emailingizni tekshiring");
        } else {

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

            String generateNum = String.valueOf((int) (Math.random() * 10000) + 100000);

            message.setSubject("Murod.uz da parolni tiklash uchun xat");
            message.setText("""
                    Assalamu Alaykum %s, saytimizda yana sizni ko'rishdan hursandmiz!
                    Iltimos akkauntizni parolini sbros qilish uchun shu kodni kiriting %s.
                    """.formatted(CurrentUsers.currentUser.getFullName(), generateNum));

            message.setFrom(new InternetAddress(username));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(CurrentUsers.currentUser.getEmail()));

            Transport.send(message);
            System.out.println("Xabar junatildi");
            System.out.print("Emailga yuborilgan passwordni kiriting: ");
            String revocer = DB.scannerStr.nextLine();
            if (revocer.equals(generateNum)) {
                System.out.print("Yangi parolni kiriting: ");
                String helper = DB.scannerStr.nextLine();
                CurrentUsers.currentUser.setPassword(helper);
                CurrentUsers.currentUser.setActive(true);
                System.out.println("Done");
            }
        }
    }


    /* OTHER MENUS */
    private void otherMenu() throws MessagingException {
        while (true) {
            System.out.println("""
                    1. recover password
                    
                    0. exit
                    """);
            switch (DB.scannerInt.nextInt()) {
                case 1 -> recoverPassword();
                case 0 -> System.exit(0);
            }
        }
    }
}