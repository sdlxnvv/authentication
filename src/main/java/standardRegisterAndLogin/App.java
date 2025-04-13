package standardRegisterAndLogin;


import standardRegisterAndLogin.db.DB;
import standardRegisterAndLogin.service.UserService;

import javax.mail.MessagingException;

public class App {
    static UserService userService = new UserService();

    public static void main(String[] args) throws MessagingException {
        boolean isExit = false;

        while (!isExit) {

            System.out.print("""
                    1. login
                    2. register
                    
                    0. exit
                    =>\s""");


            switch (DB.scannerInt.nextInt()) {
                case 1 -> userService.login();
                case 2 -> userService.register();
                case 0 -> isExit = true;
                default ->{
                    System.out.println("Wrong option");
                    return;
                }
            }
        }
    }
}
