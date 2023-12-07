package edu.ui.adminCreateTravelAgent;

import edu.core.users.User;
import edu.ui.landingPage.LandingPage;

import javax.swing.*;
import java.awt.*;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * UI class for completing the creation or updating of a travel agent account.
 *
 * <p>
 * This class provides a graphical user interface to input and submit additional information
 * for finishing a travel agent account. It includes fields for email, first name,
 * and last name, as well as buttons to submit the information and navigate back to the landing page.
 * The class interacts with the {@link FinishTravelAgentController} for account updates.
 * </p>
 *
 * @version 1.0
 * @see FinishTravelAgentController
 * @author William Delano
 */
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

    /**
     * Constructor for the FinishTravelAgentPage class.
     *
     * @param prevPage The LandingPage instance to navigate back.
     * @param account  The User object representing the travel agent account.
     */
    public FinishTravelAgentPage(LandingPage prevPage, User account) {
        this.account = account;
        this.prevPage = prevPage;
        createGUI();
    }

    /**
     * Initializes the graphical user interface for completing the creation or updating
     * of a travel agent account. Sets up the frame, panels, labels, text fields, and buttons.
     */
    private void createGUI() {
        frame = new JFrame("Finish Profile");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

    /**
     * Displays the FinishTravelAgentPage frame.
     */
    public void show() {
        frame.setVisible(true);
    }

    /**
     * Updates the account information by creating or updating a travel agent account.
     *
     * @param account The User object representing the travel agent account.
     * @param email   The new email for the travel agent account.
     * @param first   The new first name for the travel agent account.
     * @param last    The new last name for the travel agent account.
     */
    private void updateAccount(User account, String email, String first, String last) {
        //update in db and the account details
        FinishTravelAgentController.updateAccount(account, email, first, last);
        account.setFirstName(first);
        account.setLastName(last);
        account.setEmail(email);
    }

    /**
     * Validates the user's decision to create or update a travel agent account.
     *
     * @param email    The new email for the travel agent account.
     * @param password The new password for the travel agent account.
     * @return true if the user confirms the decision, false otherwise.
     */
    public boolean validateDecision(String email, String password) {
        UIManager.put("OptionPane.yesButtonText", "Confirm");
        UIManager.put("OptionPane.noButtonText", "Cancel");

        //if both values to be changed are different
        int dialogResult = JOptionPane.showConfirmDialog(mainPanel, "You are creating a travel agent with email: "
                + email + " and password: " + password, "Review Changes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        return dialogResult == JOptionPane.YES_OPTION;
    }

    /**
     * Validates the format of the provided email address.
     *
     * @param email The email address to validate.
     * @return true if the email format is valid, false otherwise.
     */
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
