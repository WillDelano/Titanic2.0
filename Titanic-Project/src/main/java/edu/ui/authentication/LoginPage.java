package edu.ui.authentication;

import javax.swing.*;
import java.awt.*;

import edu.authentication.Authentication;
import edu.database.AccountDatabase;
import edu.ui.landingPage.LandingPage;

public class LoginPage {
    private JFrame mainFrame;
    private JTextField usernameField;
    private JTextField passwordField;

    public LoginPage(){createGUI();}

    private void createGUI(){
        mainFrame = new JFrame("Login Page");
        mainFrame.setSize(1000, 700);
        mainFrame.setLayout(null);

        JPanel headerPanel = new JPanel();
        JLabel headerLabel = new JLabel("Login Page", JLabel.CENTER);
        headerPanel.add(headerLabel);
        headerPanel.setBounds(400, 0, 200, 30);

        JPanel loginPanel = new JPanel();
        loginPanel.setBounds(400,300,200,100);
        loginPanel.setLayout(new GridLayout(3, 2));

        JPanel registerPanel = new JPanel();
        registerPanel.setBounds(400, 500, 200, 100);
        registerPanel.setLayout(new GridLayout(2,1));
        JLabel registerLabel = new JLabel("Don't have an account?", JLabel.CENTER);
        JButton registerButton = new JButton("Create an account");
        registerPanel.add(registerLabel);
        registerPanel.add(registerButton);

        JLabel usernameLabel = new JLabel("Username: ");
        usernameField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Password: ");
        passwordField = new JPasswordField(20);

        JButton loginButton = new JButton("Login");

        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(new JLabel());
        loginPanel.add(loginButton);

        mainFrame.add(headerPanel);
        mainFrame.add(loginPanel);
        mainFrame.add(registerPanel);

        loginButton.addActionListener(e -> loginToSystem());
        registerButton.addActionListener(e -> registerAccount());

        mainFrame.setVisible(true);
    }

    private void loginToSystem(){
        String username = usernameField.getText();
        String password = passwordField.getText();

        Authentication a = new Authentication();
        AccountDatabase d = new AccountDatabase();

        System.out.println("1");

        if(a.login(username, password)){
            System.out.println("2");
            mainFrame.setVisible(false);
            LandingPage landingPage = new LandingPage();
            landingPage.showLandingPage(d.getUser(username));
        }

    }

    private void registerAccount(){
        mainFrame.setVisible(false);
        RegisterPage registerPage = new RegisterPage();
    }

    public static void main(String[] args) {
        new LoginPage();
    }
}

