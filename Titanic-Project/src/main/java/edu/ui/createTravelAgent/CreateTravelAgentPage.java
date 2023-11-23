package edu.ui.createTravelAgent;

import edu.core.reservation.Room;
import edu.core.users.TravelAgent;
import edu.core.users.User;
import edu.databaseAccessors.RoomDatabase;
import edu.ui.editReservation.EditReservationController;
import edu.ui.editReservation.ReservationListPage;
import edu.ui.landingPage.LandingPage;
import edu.ui.landingPage.TravelAgentLandingPage;

import edu.ui.resetPassword.ResetPasswordListPage;
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

public class CreateTravelAgentPage {
    private JFrame frame;
    private JLabel titleLabel;
    private JPanel mainPanel;
    private JLabel deleteAccountLabel;
    private JRadioButton deleteAccountYes;
    private JRadioButton deleteAccountNo;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JTextField passwordField;
    private JButton submitButton;
    private JTextField usernameField;
    private JLabel paymentLabel;
    private JButton paymentButton;
    private LandingPage prevPage;
    private JButton backButton;

    public CreateTravelAgentPage(LandingPage prevPage) {
        this.prevPage = prevPage;

        createGUI();
    }

    private void createGUI() {
        frame = new JFrame("Edit Profile");
        frame.setSize(650, 300);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 3, 15, 20));

        titleLabel = new JLabel("New Travel Agent", JLabel.CENTER);

        usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();

        passwordLabel = new JLabel("Password:");
        passwordField = new JTextField();

        paymentLabel = new JLabel("Edit Payment Info:");
        paymentButton = new JButton("Edit");

        submitButton = new JButton("Submit");

        backButton = new JButton("Back");

        //first row
        mainPanel.add(new JLabel());
        mainPanel.add(titleLabel);
        mainPanel.add(new JLabel());

        //second row
        mainPanel.add(usernameLabel);
        mainPanel.add(usernameField);
        mainPanel.add(new JLabel());

        //third row
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordField);
        mainPanel.add(new JLabel());

        //fourth row
        mainPanel.add(new JLabel());
        mainPanel.add(submitButton);
        mainPanel.add(new JLabel());

        //fifth row
        mainPanel.add(new JLabel());
        mainPanel.add(backButton);
        mainPanel.add(new JLabel());

        frame.add(mainPanel);
        frame.setVisible(true);

        backButton.addActionListener(e -> {
            frame.dispose();
            prevPage.show();
        });

        submitButton.addActionListener(e -> {
            //get the new username
            String newUsername = (String) usernameField.getText();

            //get the new password
            String newPassword = (String) passwordField.getText();

            //if there is an empty field, remind the user
            if (Objects.equals(newUsername, "") || Objects.equals(newPassword, "")) {
                noChangesDecision(newUsername, newPassword);
            }
            else {
                //if that username is already in use, start over
                if (CreateTravelAgentController.checkForDuplicate(newUsername)) {
                    JOptionPane.showMessageDialog(frame, "That username is already in use", "Sorry!", JOptionPane.WARNING_MESSAGE);
                    frame.dispose();
                    createGUI();
                    return;
                }

                //if the user confirms their decision, update and go back to landing page
                if (validateDecision(newUsername, newPassword)) {
                    updateAccount(newUsername, newPassword);
                    frame.dispose();
                    prevPage.show();
                }
                //otherwise restart the edit frame
                else {
                    frame.dispose();
                    createGUI();
                }
            }
        });
    }

    public void show() {
        frame.setVisible(true);
    }

    private void updateAccount(String username, String password) {
        CreateTravelAgentController.createAccount(username, password);
    }

    public boolean validateDecision(String username, String password) {
        UIManager.put("OptionPane.yesButtonText", "Confirm");
        UIManager.put("OptionPane.noButtonText", "Cancel");

        //if both values to be changed are different
        int dialogResult = JOptionPane.showConfirmDialog(mainPanel, "You are creating a travel agent with username: "
                + username + " and password: " + password, "Review Changes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        return dialogResult == JOptionPane.YES_OPTION;
    }

    public boolean noChangesDecision(String username, String password) {
        UIManager.put("OptionPane.yesButtonText", "Yes, quit");
        UIManager.put("OptionPane.noButtonText", "No, continue");
        int dialogResult;

        if (Objects.equals(username, "") && Objects.equals(password, "")) {
            dialogResult = JOptionPane.showConfirmDialog(mainPanel, "Both entries are empty. Would you like to quit?",
                    "No account will be created", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        }
        else if (Objects.equals(username, "")) {
            dialogResult = JOptionPane.showConfirmDialog(mainPanel, "A username has not been entered. Would you like to quit?",
                    "No account will be created", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        }
        else {
            dialogResult = JOptionPane.showConfirmDialog(mainPanel, "A password has not been entered. Would you like to quit?",
                    "No account will be created", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        }

        if (dialogResult == JOptionPane.YES_OPTION) {
            frame.dispose(); //close this frame
            prevPage.show();

            return true;
        }
        else {
            frame.dispose(); //close this instance of frame
            createGUI(); //restart the frame

            return false;
        }
    }
}
