package edu.ui.editProfile;

import edu.core.users.User;
import edu.databaseAccessors.AccountDatabase;
import edu.ui.authentication.LoginPage;
import edu.ui.landingPage.LandingPage;

import edu.ui.adminResetPasswords.ResetPasswordListPage;

import javax.swing.*;
import java.awt.*;

import java.io.IOException;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The EditProfile class represents a graphical user interface (GUI) for editing user profiles in the reservation system.
 * It allows users to update their email and password, and provides an option to delete their account.
 * The class includes methods for creating the GUI, handling user inputs, updating account information, and managing account deletion.
 *
 * @version 1.0
 * @author William Delano
 */

public class EditProfile {
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
    private User account;
    private JTextField emailField;
    private JLabel paymentLabel;
    private JButton paymentButton;

    /* Two previous pages are used to represent the two potential previous pages that can reach this page*/
    private LandingPage previousLandingPage;
    private ResetPasswordListPage prevListPage;
    private JButton backButton;
    boolean comingFromTravelAgentPage;

    /**
     * Constructs an instance of the EditProfile class with the specified user account and previous pages.
     *
     * @param account           The user account to be edited.
     * @param prevLandingPage   The landing page to return to if coming from a regular user page.
     * @param prevListPage      The list page to return to if coming from an admin page.
     * @param ta                A boolean indicating whether the user is coming from a travel agent page.
     */
    public EditProfile(User account, LandingPage prevLandingPage, ResetPasswordListPage prevListPage, boolean ta) {
        this.previousLandingPage = prevLandingPage;
        this.prevListPage = prevListPage;
        this.account = account;
        this.comingFromTravelAgentPage = ta;

        createGUI();
    }

    /**
     * Creates the graphical user interface (GUI) for the EditProfile class.
     * Sets up the frame, panels, labels, and buttons to allow users to edit their profile information.
     */
    private void createGUI() {
        frame = new JFrame("Edit Profile");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(6, 3, 15, 20));

        String firstName = account.getFirstName();
        String lastName = account.getLastName();
        titleLabel = new JLabel(firstName + " " + lastName, JLabel.CENTER);

        emailLabel = new JLabel("Email:");
        emailField = new JTextField();

        passwordLabel = new JLabel("Password:");
        passwordField = new JTextField();

        deleteAccountLabel = new JLabel("Delete account:");
        deleteAccountYes = new JRadioButton("Yes");
        deleteAccountNo = new JRadioButton("No");
        ButtonGroup deleteAccountGroup = new ButtonGroup();
        deleteAccountGroup.add(deleteAccountYes);
        deleteAccountGroup.add(deleteAccountNo);

        submitButton = new JButton("Submit");

        backButton = new JButton("Back");

        //first row
        mainPanel.add(new JLabel());
        mainPanel.add(titleLabel);
        mainPanel.add(new JLabel());

        //second row
        mainPanel.add(emailLabel);
        mainPanel.add(emailField);

        //prefill the correct account information to display
        String email = "Failed to find email in database.";
        try {
            email = EditProfileController.getAccountEmail(account);
            System.out.println(email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        emailField.setText(email);
        mainPanel.add(new JLabel());

        //third row
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordField);

        //prefill the correct account information to display
        String password = account.getPassword();
        try {
            password = AccountDatabase.getUser(account.getUsername()).getPassword();
        } catch (Exception e) {
            e.printStackTrace();
        }
        passwordField.setText(password);

        mainPanel.add(new JLabel());

        //fourth row
        mainPanel.add(deleteAccountLabel);
        mainPanel.add(deleteAccountYes);
        mainPanel.add(deleteAccountNo);

        //fifth row
        mainPanel.add(new JLabel());
        mainPanel.add(submitButton);
        mainPanel.add(new JLabel());

        //sixth row
        mainPanel.add(new JLabel());
        mainPanel.add(backButton);
        mainPanel.add(new JLabel());

        frame.add(mainPanel);
        frame.setVisible(true);

        backButton.addActionListener(e -> {
            frame.dispose();

            if (comingFromTravelAgentPage) {
                previousLandingPage.show(); // Go back to the landing page
            }
            else {
                prevListPage.show(); // Go back to list page
            }
        });

        submitButton.addActionListener(e -> {
            //get the new email
            String newEmail = (String) emailField.getText();

            //get the new password
            String newPassword = (String) passwordField.getText();

            //get delete account choice
            boolean deleteAccount = deleteAccountYes.isSelected();

            //if the choice was not to delete the account
            if (!deleteAccount) {

                //if email was not valid, start over
                if (!validateEmail(newEmail)) {
                    frame.dispose();
                    createGUI();
                    return;
                }

                //if no changes were made, remind the user
                if (Objects.equals(newEmail, account.getEmail()) && Objects.equals(newPassword, account.getPassword())) {
                    noChangesDecision();
                }
                //if there is a non-duplicate value, update the account
                else {

                    //if the user confirms their decision, update and go back to landing page
                    if (validateDecision(newEmail, newPassword)) {
                        updateAccount(newEmail, newPassword);
                        frame.dispose();

                        if (comingFromTravelAgentPage) {
                            previousLandingPage.show(); // Go back to the landing page
                        }
                        else {
                            prevListPage.show(); // Go back to list page
                        }
                    }
                    //otherwise restart the edit frame
                    else {
                        frame.dispose();
                        createGUI();
                    }
                }
            }
            //ask for confirmation before deleting the account
            else {

                //if they decided not to delete their account, restart
                try {
                    if (!deleteAccount()) {
                        frame.dispose();
                        createGUI();
                    }
                    //if they did delete, return to landing page
                    else {
                        frame.dispose();

                        if (comingFromTravelAgentPage) {
                            new LoginPage(); // Go back to the landing page
                        }
                        else {
                            prevListPage.show(); // Go back to list page
                        }
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    /**
     * Displays the EditProfile frame.
     */
    public void show() {
        frame.setVisible(true);
    }

    /**
     * Updates the user account with the new email and password values.
     *
     * @param email    The new email value.
     * @param password The new password value.
     */
    private void updateAccount(String email, String password) {
        EditProfileController.editAccount(account, email, password);
    }

    /**
     * Initiates the account deletion process, prompting the user for confirmation.
     *
     * @return True if the user confirmed and the account was deleted, false otherwise.
     * @throws IOException If an I/O error occurs during account deletion.
     */
    public boolean deleteAccount() throws IOException {

        UIManager.put("OptionPane.yesButtonText", "Confirm");
        UIManager.put("OptionPane.noButtonText", "Cancel");

        int dialogResult = JOptionPane.showConfirmDialog(mainPanel, "Are you sure you want to delete your account?",
                "This action cannot be undone!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (dialogResult == JOptionPane.YES_OPTION) {
            EditProfileController.deleteAccount(account);
            return true;
        }
        return false;
    }

    /**
     * Validates the user's decision to update their account with the specified email and password.
     *
     * @param email    The new email value.
     * @param password The new password value.
     * @return True if the user confirmed the decision, false otherwise.
     */
    public boolean validateDecision(String email, String password) {
        UIManager.put("OptionPane.yesButtonText", "Confirm");
        UIManager.put("OptionPane.noButtonText", "Cancel");

        //if both values to be changed are different
        if (!Objects.equals(account.getEmail(), email) && !Objects.equals(account.getPassword(), password)) {
            int dialogResult = JOptionPane.showConfirmDialog(mainPanel, "You are changing email to: "
                    + email + " and password to " + password, "Review Changes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            return dialogResult == JOptionPane.YES_OPTION;
        }
        //if only the email was changed
        else if (!Objects.equals(account.getEmail(), email)) {
            int dialogResult = JOptionPane.showConfirmDialog(mainPanel, "You are changing email to: "
                    + email,"Review Changes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            return dialogResult == JOptionPane.YES_OPTION;
        }
        //only the password was changed
        else {
            int dialogResult = JOptionPane.showConfirmDialog(mainPanel, "You are changing password to: "
                    + password, "Review Changes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            return dialogResult == JOptionPane.YES_OPTION;
        }
    }

    /**
     * Validates the email format using regular expressions.
     *
     * @param email The email to be validated.
     * @return True if the email format is valid, false otherwise.
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

    /**
     * Displays a confirmation dialog when no changes have been made, allowing the user to quit or continue.
     *
     * @return True if the user chose to quit, false if they chose to continue.
     */
    public boolean noChangesDecision() {
        UIManager.put("OptionPane.yesButtonText", "Yes, quit");
        UIManager.put("OptionPane.noButtonText", "No, continue");

        int dialogResult = JOptionPane.showConfirmDialog(mainPanel, "No changes have been made. Would you like to quit?",
                "Alert", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (dialogResult == JOptionPane.YES_OPTION) {
            frame.dispose(); //close this frame

            if (comingFromTravelAgentPage) {
                previousLandingPage.show(); // Go back to the landing page
            }
            else {
                prevListPage.show(); // Go back to list page
            }
            return true;
        }
        else {
            frame.dispose(); //close this instance of frame
            createGUI(); //restart the frame
            return false;
        }
    }
}
