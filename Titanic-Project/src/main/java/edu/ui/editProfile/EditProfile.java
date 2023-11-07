package edu.ui.editProfile;

import edu.core.reservation.Room;
import edu.core.users.TravelAgent;
import edu.core.users.User;
import edu.databaseAccessors.RoomDatabase;
import edu.ui.editReservation.EditReservationController;
import edu.ui.landingPage.LandingPage;
import edu.ui.landingPage.TravelAgentLandingPage;

import edu.uniqueID.UniqueID;

import javax.swing.*;
import java.awt.*;

import java.io.IOException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private LandingPage previousPage;


    public EditProfile(User account, TravelAgentLandingPage prevPage) {
        this.previousPage = prevPage;
        this.account = account;
        createGUI();
    }

    private void createGUI() {
        frame = new JFrame("Edit Profile");
        frame.setSize(400, 300);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(6, 3, 15, 20));

        String firstName = account.getFirstName();
        String lastName = account.getLastName();
        titleLabel = new JLabel(firstName + " " + lastName, JLabel.CENTER);

        emailLabel = new JLabel("Email:");
        emailField = new JTextField();

        passwordLabel = new JLabel("Password:");
        passwordField = new JTextField();

        paymentLabel = new JLabel("Edit Payment Info:");
        paymentButton = new JButton("Edit");

        deleteAccountLabel = new JLabel("Delete account:");
        deleteAccountYes = new JRadioButton("Yes");
        deleteAccountNo = new JRadioButton("No");
        ButtonGroup deleteAccountGroup = new ButtonGroup();
        deleteAccountGroup.add(deleteAccountYes);
        deleteAccountGroup.add(deleteAccountNo);

        submitButton = new JButton("Submit");

        //first row
        mainPanel.add(new JLabel());
        mainPanel.add(titleLabel);
        mainPanel.add(new JLabel());

        //second row
        mainPanel.add(emailLabel);
        mainPanel.add(emailField);

        //prefill the correct account information to display
        String email = account.getEmail();
        emailField.setText(email);

        mainPanel.add(new JLabel());

        //third row
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordField);

        //prefill the correct account information to display
        String password = account.getPassword();
        passwordField.setText(password);

        mainPanel.add(new JLabel());

        //fourth row
        mainPanel.add(paymentLabel);
        mainPanel.add(paymentButton);
        mainPanel.add(new JLabel());

        //fifth row
        mainPanel.add(deleteAccountLabel);
        mainPanel.add(deleteAccountYes);
        mainPanel.add(deleteAccountNo);

        //sixth row
        mainPanel.add(new JLabel());
        mainPanel.add(submitButton);
        mainPanel.add(new JLabel());

        frame.add(mainPanel);
        frame.setVisible(true);

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
                        previousPage.show(); // Go back to the landing page
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
                        previousPage.show();
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void show() {
        frame.setVisible(true);
    }

    private void updateAccount(String email, String password) {
        EditProfileController.editAccount(account, email, password);
    }


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

    public boolean validateDecision(String email, String password) {
        UIManager.put("OptionPane.yesButtonText", "Confirm");
        UIManager.put("OptionPane.noButtonText", "Cancel");

        //if both values to be changed are different
        if (!Objects.equals(account.getEmail(), email) && !Objects.equals(account.getPassword(), password)) {
            int dialogResult = JOptionPane.showConfirmDialog(mainPanel, "You are changing your email to: "
                    + email + " and your password to " + password, "Review Changes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            return dialogResult == JOptionPane.YES_OPTION;
        }
        //if only the email was changed
        else if (!Objects.equals(account.getEmail(), email)) {
            int dialogResult = JOptionPane.showConfirmDialog(mainPanel, "You are changing your email to: "
                    + email,"Review Changes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            return dialogResult == JOptionPane.YES_OPTION;
        }
        //only the password was changed
        else {
            int dialogResult = JOptionPane.showConfirmDialog(mainPanel, "You are changing your password to: "
                    + password, "Review Changes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            return dialogResult == JOptionPane.YES_OPTION;
        }
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

    public boolean noChangesDecision() {
        UIManager.put("OptionPane.yesButtonText", "Yes, quit");
        UIManager.put("OptionPane.noButtonText", "No, continue");

        int dialogResult = JOptionPane.showConfirmDialog(mainPanel, "No changes have been made. Would you like to quit?",
                "Reservation is unchanged", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (dialogResult == JOptionPane.YES_OPTION) {
            frame.dispose(); //close this frame
            previousPage.show(); // Go back to the landingPage
            return true;
        }
        else {
            frame.dispose(); //close this instance of frame
            createGUI(); //restart the frame
            return false;
        }
    }
}
