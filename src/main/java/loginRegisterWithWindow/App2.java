package loginRegisterWithWindow;

import org.example.service.UserService2;

import javax.mail.MessagingException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App2 {
    private static final UserService2 userService = new UserService2();
    private static JFrame mainFrame;

    public static void main(String[] args) {
        mainFrame = new JFrame("User Authentication");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(500, 400);
        mainFrame.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLoginDialog();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRegisterDialog();
            }
        });

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        mainFrame.add(buttonPanel, BorderLayout.CENTER);
        mainFrame.setVisible(true);
    }

    private static void showLoginDialog() {
        JDialog loginDialog = new JDialog(mainFrame, "Login", true);
        loginDialog.setLayout(new GridLayout(3, 2, 10, 10));
        loginDialog.setSize(300, 200);

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    boolean success = userService.login(emailField.getText(), new String(passwordField.getPassword()));
                    if (success) {
                        JOptionPane.showMessageDialog(loginDialog, "Login successful!");
                        loginDialog.dispose();
                        showUserMenu();
                    } else {
                        JOptionPane.showMessageDialog(loginDialog, "Invalid email or password", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (MessagingException ex) {
                    JOptionPane.showMessageDialog(loginDialog, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        loginDialog.add(emailLabel);
        loginDialog.add(emailField);
        loginDialog.add(passwordLabel);
        loginDialog.add(passwordField);
        loginDialog.add(new JLabel());
        loginDialog.add(submitButton);
        loginDialog.setVisible(true);
    }

    private static void showRegisterDialog() {
        JDialog registerDialog = new JDialog(mainFrame, "Register", true);
        registerDialog.setLayout(new GridLayout(3, 2, 10, 10));
        registerDialog.setSize(300, 200);

        JLabel nameLabel = new JLabel("Full Name:");
        JTextField nameField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    userService.register(nameField.getText(), emailField.getText());
                    JOptionPane.showMessageDialog(registerDialog, "Registration successful! Please check your email.");
                    registerDialog.dispose();
                } catch (MessagingException ex) {
                    JOptionPane.showMessageDialog(registerDialog, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        registerDialog.add(nameLabel);
        registerDialog.add(nameField);
        registerDialog.add(emailLabel);
        registerDialog.add(emailField);
        registerDialog.add(new JLabel());
        registerDialog.add(submitButton);
        registerDialog.setVisible(true);
    }

    private static void showUserMenu() {
        JFrame userFrame = new JFrame("User Menu");
        userFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        userFrame.setSize(300, 200);
        userFrame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton recoverButton = new JButton("Recover Password");
        JButton exitButton = new JButton("Exit");

        recoverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRecoverDialog(userFrame);
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userFrame.dispose();
            }
        });

        panel.add(recoverButton);
        panel.add(exitButton);
        userFrame.add(panel, BorderLayout.CENTER);
        userFrame.setVisible(true);
    }

    private static void showRecoverDialog(JFrame parent) {
        JDialog recoverDialog = new JDialog(parent, "Recover Password", true);
        recoverDialog.setLayout(new GridLayout(3, 2, 10, 10));
        recoverDialog.setSize(300, 200);

        JLabel codeLabel = new JLabel("Code from email:");
        JTextField codeField = new JTextField();
        JLabel newPassLabel = new JLabel("New Password:");
        JPasswordField newPassField = new JPasswordField();
        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean success = userService.recoverPassword(codeField.getText(), new String(newPassField.getPassword()));
                if (success) {
                    JOptionPane.showMessageDialog(recoverDialog, "Password recovered successfully!");
                    recoverDialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(recoverDialog, "Recovery failed", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        recoverDialog.add(codeLabel);
        recoverDialog.add(codeField);
        recoverDialog.add(newPassLabel);
        recoverDialog.add(newPassField);
        recoverDialog.add(new JLabel());
        recoverDialog.add(submitButton);
        recoverDialog.setVisible(true);
    }
}