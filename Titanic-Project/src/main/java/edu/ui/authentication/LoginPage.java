package edu.ui.authentication;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import edu.authentication.Authentication;
import edu.databaseAccessors.AccountDatabase;
import edu.ui.landingPage.GuestLandingPage;
import edu.ui.landingPage.TravelAgentLandingPage;

/**
 * UI display for the login page
 *
 * This class creates the login page and allows access to the RegisterPage
 *
 * @author Gabriel Choi
 * @version 1.0
 * @see RegisterPage
 */
public class LoginPage {
    private JFrame mainFrame;
    private JTextField usernameField;
    private JTextField passwordField;

    /**
     * This is the constructor for the Login page. It calls createGUI.
     *
     */
    public LoginPage(){createGUI();}

    /**
     * This creates the GUI for the Login Page.
     *
     */
    private void createGUI(){
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    /**
     * This logs the Guest into the system.
     *
     */
    private void loginToSystem() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        //Takes user to landing page if login successful
        if(LoginPageController.loginUser(username, password)){
            mainFrame.dispose();
        }
        //Outputs error message if login fails
        else{
            JOptionPane.showMessageDialog(mainFrame, "Username or Password Incorrect", "Oops!", JOptionPane.WARNING_MESSAGE);
            usernameField.setText("");
            passwordField.setText("");
        }

    }

    /**
     * This calls the Register Page.
     *
     */
    private void registerAccount(){
        mainFrame.setVisible(false);
        RegisterPage registerPage = new RegisterPage();
    }

    /**
     * This sends the User to the Login Page.
     *
     * @param args  The console arguments.
     *
     */
    public static void main(String[] args) {
        new LoginPage();
    }
}

