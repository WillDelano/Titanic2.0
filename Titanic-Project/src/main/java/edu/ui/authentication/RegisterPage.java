package edu.ui.authentication;

import javax.swing.*;
import java.awt.*;
import edu.uniqueID.UniqueID;

import edu.databaseAccessors.AccountDatabase;
import java.util.regex.Pattern;
import java.util.regex.*;
import edu.core.users.*;

/**
 * UI display for the registration page
 *
 * This class creates the registration page and allows a user to register for an account
 *
 * @author Gabriel Choi
 * @version 1.2
 * @see LoginPage
 */
public class RegisterPage {
    private JFrame mainFrame;
    private JTextField usernameField;
    private JTextField passwordField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;

    /**
     * This is the constructor for the Register page. It calls createGUI.
     *
     */
    public RegisterPage() { createGUI(); }

    /**
     * This creates the GUI for the Register Page.
     *
     */
    public void createGUI(){
        mainFrame = new JFrame("Create an Account Page");
        mainFrame.setSize(1000, 700);
        mainFrame.setLayout(new BorderLayout());

        JLabel registerLabel = new JLabel("Create an Account", JLabel.CENTER);
        mainFrame.add(registerLabel, BorderLayout.PAGE_START);

        JPanel informationPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10, 0, 0, 0);

        //adding text fields for account information
        c.gridx = 0;
        c.gridy = 0;
        informationPanel.add(new JLabel("First Name: ", JLabel.CENTER), c);
        c.gridx = 1;
        firstNameField = new JTextField(20);
        informationPanel.add(firstNameField, c);

        c.gridx = 0;
        c.gridy = 1;
        informationPanel.add(new JLabel("Last Name: ", JLabel.CENTER), c);
        c.gridx = 1;
        lastNameField = new JTextField(20);
        informationPanel.add(lastNameField, c);

        c.gridx = 0;
        c.gridy = 2;
        informationPanel.add(new JLabel("Email: ", JLabel.CENTER), c);
        c.gridx = 1;
        emailField = new JTextField(20);
        informationPanel.add(emailField, c);

        c.gridx = 0;
        c.gridy = 3;
        informationPanel.add(new JLabel("Username: ", JLabel.CENTER), c);
        c.gridx = 1;
        usernameField = new JTextField(20);
        informationPanel.add(usernameField, c);

        c.gridx = 0;
        c.gridy = 4;
        informationPanel.add(new JLabel("Password: ", JLabel.CENTER), c);
        c.gridx = 1;
        passwordField = new JTextField(20);
        informationPanel.add(passwordField, c);

        JButton registerButton = new JButton("Submit");
        registerButton.addActionListener(e -> registerAccount());
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(20, 0, 0, 0);
        informationPanel.add(registerButton, c);

        c.gridy = 6;
        informationPanel.add(new JLabel(), c);

        JButton backButton = new JButton("Back");
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(20,0,0,0);
        backButton.addActionListener(e -> {
            mainFrame.dispose();
            LoginPage loginPage = new LoginPage();
        });
        informationPanel.add(backButton, c);

        mainFrame.add(new JLabel(), BorderLayout.LINE_START);
        mainFrame.add(informationPanel, BorderLayout.CENTER);
        mainFrame.add(new JLabel(), BorderLayout.LINE_END);

        mainFrame.setVisible(true);
    }

    /**
     * This checks if the username exists or not in the database.
     *
     * @return Returns true if the account does not exist.
     */
    public boolean validateUsername() {
        String username = usernameField.getText();

        if (RegisterPageController.accountExists(username)) {
            JOptionPane.showMessageDialog(mainFrame, "A user with that name already exists", "Sorry!", JOptionPane.WARNING_MESSAGE);
            return true;
        }
        return false;
    }

    /**
     * This registers the Guest into the system.
     *
     */
    public void registerAccount() {
        //if username exists, start over
        if (validateUsername()) {
            mainFrame.dispose();
            createGUI();
            return;
        }

        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        //please remove this and add to the Guest. The Guest should generate after authenticating the
        //the account. Right now we are passing in an id which seems weird. Either
        int id = new UniqueID().getId();
        Guest g = new Guest(username, password, id, firstName, lastName, 100 , email);

        //Checking email format
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if(!matcher.matches()){
            JOptionPane.showMessageDialog(mainFrame, "Invalid email format", "Oops!", JOptionPane.WARNING_MESSAGE);
            return;
        }

        RegisterPageController.createAccount(username, password, firstName, lastName, email);


        mainFrame.setVisible(false);
        LoginPage loginPage = new LoginPage();
    }
}
