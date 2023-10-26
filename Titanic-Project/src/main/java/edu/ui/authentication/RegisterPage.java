package edu.ui.authentication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.authentication.Authentication;
import edu.core.cruise.Cruise;
import edu.core.users.User;
import edu.database.AccountDatabase;
import edu.ui.cruiseDetails.CruiseDetailsPage;
import edu.ui.cruiseDetails.SelectCruiseController;
import edu.ui.landingPage.LandingPage;

/**
 * UI display for the registration page
 *
 * This class creates the registration page and allows a user to register for an account
 *
 * @author Gabriel Choi
 * @version 1.0
 * @see LoginPage
 */
public class RegisterPage {
    private JFrame mainFrame;
    private JTextField usernameField;
    private JTextField passwordField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;


    public RegisterPage() { createGUI(); }

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

        mainFrame.add(new JLabel(), BorderLayout.LINE_START);
        mainFrame.add(informationPanel, BorderLayout.CENTER);
        mainFrame.add(new JLabel(), BorderLayout.LINE_END);

        mainFrame.setVisible(true);
    }

    public boolean validateUsername() {
        AccountDatabase d = new AccountDatabase();
        String username = usernameField.getText();

        if (AccountDatabase.accountExists(username)) {
            JOptionPane.showMessageDialog(mainFrame, "A user with that name already exists", "Sorry!", JOptionPane.WARNING_MESSAGE);

            return true;
        }
        return false;
    }

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

        AccountDatabase d = new AccountDatabase();
        Authentication a = new Authentication();

        a.createAccount(username, password, firstName, lastName, email);

        mainFrame.setVisible(false);
        LoginPage loginPage = new LoginPage();
    }
}
