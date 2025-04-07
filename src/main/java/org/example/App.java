package org.example;


import org.example.db.DB;
import org.example.service.UserService;

import javax.mail.MessagingException;

public class App {
    static UserService userService = new UserService();
    public static void main( String[] args ) throws MessagingException {
        boolean isExit = false;

        while (!isExit) {

            System.out.println("""
            1. login
            2. register

            0. exit
            """);


            switch (DB.scannerInt.nextInt()) {
                case 1:
                    userService.login();
                    break;
                case 2:
                    userService.register();
                    break;
                case 0:
                    isExit = true;
                    break;
                default:
                    System.out.println("Error 404");
                    break;
            }
        }
    }
}
