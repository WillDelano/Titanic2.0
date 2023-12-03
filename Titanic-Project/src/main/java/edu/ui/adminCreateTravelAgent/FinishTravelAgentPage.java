package edu.ui.adminCreateTravelAgent;

import edu.core.users.User;
import edu.ui.landingPage.LandingPage;

import javax.swing.*;
import java.awt.*;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FinishTravelAgentPage {
    private JFrame frame;
    private JLabel titleLabel;
    private JPanel mainPanel;
    private JLabel deleteAccountLabel;
    private JRadioButton deleteAccountYes;
    private JRadioButton deleteAccountNo;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JTextField passwordField;
    private JButton submitButton;
    private JTextField emailField;
    private JLabel paymentLabel;
    private JButton paymentButton;
    private User account;
    private JLabel usernameLabel;
    private JTextField usernameField;
    private JLabel firstNameLabel;
    private JTextField firstNameField;
    private JLabel lastNameLabel;
    private JTextField lastNameField;
    LandingPage prevPage;

    public FinishTravelAgentPage(LandingPage prevPage, User account) {
        this.account = account;
        this.prevPage = prevPage;

        createGUI();
    }

    private void createGUI() {
        frame = new JFrame("Finish Profile");
        frame.setSize(600, 400);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(7, 3, 15, 20));

        String firstName = account.getFirstName();
        String lastName = account.getLastName();
        titleLabel = new JLabel(firstName + " " + lastName, JLabel.CENTER);

        emailLabel = new JLabel("Email:");
        emailField = new JTextField();

        passwordLabel = new JLabel("Password:");
        passwordField = new JTextField();
        passwordField.setEditable(false);

        usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        usernameField.setEditable(false);

        firstNameLabel = new JLabel("Firstname:");
        firstNameField = new JTextField();

        lastNameLabel = new JLabel("Lastname:");
        lastNameField = new JTextField();

        submitButton = new JButton("Submit");

        //first row
        mainPanel.add(new JLabel());
        mainPanel.add(titleLabel);
        mainPanel.add(new JLabel());

        //second row
        mainPanel.add(firstNameLabel);
        mainPanel.add(firstNameField);
        mainPanel.add(new JLabel());

        //third row
        mainPanel.add(lastNameLabel);
        mainPanel.add(lastNameField);
        mainPanel.add(new JLabel());

        //fourth row
        mainPanel.add(usernameLabel);
        mainPanel.add(usernameField);

        //prefill the correct account information to display
        String username = account.getUsername();
        usernameField.setText(username);

        mainPanel.add(new JLabel());

        //fifth row
        mainPanel.add(emailLabel);
        mainPanel.add(emailField);
        mainPanel.add(new JLabel());

        //sixth row
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordField);

        //prefill the correct account information to display
        String password = account.getPassword();
        passwordField.setText(password);

        mainPanel.add(new JLabel());

        //seventh row
        mainPanel.add(new JLabel());
        mainPanel.add(submitButton);
        mainPanel.add(new JLabel());

        frame.add(mainPanel);
        frame.setVisible(true);

        submitButton.addActionListener(e -> {
            //get the email
            String newEmail = (String) emailField.getText();

            //get the first name
            String newFirst = (String) firstNameField.getText();

            //get the last name
            String newLast = (String) lastNameField.getText();

            //if one or more changes need to be made, remind the user
            if (Objects.equals(newFirst, "") || Objects.equals(newLast, "") || Objects.equals(newEmail, "")) {
                JOptionPane.showMessageDialog(frame, "No changes have been made", "Fill all options", JOptionPane.WARNING_MESSAGE);
                frame.dispose();
                createGUI();
            }
            else {
                //finish creating the account
                updateAccount(account, newEmail, newFirst, newLast);
                frame.dispose();
                prevPage.show();
            }
        });
    }

    public void show() {
        frame.setVisible(true);
    }

    private void updateAccount(User account, String email, String first, String last) {
        FinishTravelAgentController.updateAccount(account, email, first, last);
    }

    public boolean validateDecision(String email, String password) {
        UIManager.put("OptionPane.yesButtonText", "Confirm");
        UIManager.put("OptionPane.noButtonText", "Cancel");

        //if both values to be changed are different
        int dialogResult = JOptionPane.showConfirmDialog(mainPanel, "You are creating a travel agent with email: "
                + email + " and password: " + password, "Review Changes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        return dialogResult == JOptionPane.YES_OPTION;
    }

    public boolean validateEmail(String email) {
        //Checking email format
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            JOptionPane.showMessageDialog(frame, "Invalid email format", "Oops!", JOptionPane.WARNING_MESSAGE);
        }

        return matcher.matches();
    }
}
