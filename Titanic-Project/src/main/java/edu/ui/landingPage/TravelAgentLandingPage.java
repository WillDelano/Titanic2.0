package edu.ui.landingPage;

import edu.core.reservation.Room;
import edu.core.users.User;
import edu.databaseAccessors.AccountDatabase;
import edu.databaseAccessors.RoomDatabase;
import edu.exceptions.UserNotFoundException;
import edu.ui.authentication.LoginPage;
import edu.ui.travelAgentAddRoom.AddRoomPage;
import edu.ui.adminCreateTravelAgent.FinishTravelAgentPage;
import edu.ui.editProfile.EditProfile;
import edu.ui.travelAgentEditReservations.GuestsWithReservationPage;
import edu.ui.travelAgentEditRooms.EditRoomPage;
import edu.ui.travelAgentEditRooms.ViewAllRoomsPage;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

/**
 * Creates the landing page
 *
 * This class creates the landing page for the cruise reservation application
 *
 * @author William Delano
 * @version 1.0
 * @see LandingPageController
 */
public class TravelAgentLandingPage extends LandingPage {
    private JFrame mainFrame;
    private JPanel headerPanel;
    private JLabel headerLabel;
    private User account;
    private JPanel logoutPanel;
    private JLabel logoutLabel;
    private Room room;
    private boolean created;

    /**
     * Constructor for the landing page that creates the GUI
     *
     */
    public TravelAgentLandingPage(User account) {
        this.account = account;
        this.created = false;
        prepareGUI();
    }

    /**
     * Creates the GUI for the landing page
     *
     */
    private void prepareGUI() {
        mainFrame = new JFrame("Cruise Reservation Application");
        mainFrame.setSize(1000, 700);
        mainFrame.setLayout(new BorderLayout());

        headerLabel = new JLabel("", JLabel.CENTER);
        headerPanel = new JPanel(new GridLayout(2, 5));

        logoutLabel = new JLabel("", JLabel.CENTER);
        logoutPanel = new JPanel(new GridLayout(1, 5));

        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel middlePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton browseCruisesButton = new JButton("Edit Reservations");
        browseCruisesButton.addActionListener(e -> {
            try {
                navigateToEditReservations();
            } catch (UserNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        JButton myReservationsButton = new JButton("Add Rooms");
        myReservationsButton.addActionListener(e -> navigateToAddRooms());

        JButton editRoomButton = new JButton("Edit Rooms");
        editRoomButton.addActionListener(e -> navigateToModifyRooms());

        JButton editProfile = new JButton("Edit Profile");
        editProfile.addActionListener(e -> navigateToEditProfile());

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> logout());

        headerPanel.add(browseCruisesButton);
        headerPanel.add(myReservationsButton);
        headerPanel.add(editRoomButton);
        headerPanel.add(editProfile, BorderLayout.EAST);

        headerPanel.add(topPanel);
        headerPanel.add(new JPanel());
        headerPanel.add(middlePanel);

        logoutPanel.add(new JPanel());
        logoutPanel.add(new JPanel());
        logoutPanel.add(logoutButton);

        mainFrame.add(headerLabel, BorderLayout.CENTER);
        mainFrame.add(headerPanel, BorderLayout.NORTH);
        mainFrame.add(logoutLabel, BorderLayout.CENTER);
        mainFrame.add(logoutPanel, BorderLayout.SOUTH);
    }

    private void logout() {
        mainFrame.dispose();
        new LoginPage();
    }

    /**
     * Sets the landing page to visible and displays the center text with a user's name and email
     *
     * @param account The user who is logged in
     */
    public void showLandingPage(User account) throws UserNotFoundException {
        this.account = account;

        String name = account.getFirstName() + " " + account.getLastName();
        int count = AccountDatabase.getUserCount();

        logoutLabel.setText(String.format("<html>" +
                "<div style='text-align: center; font-size: 24px;'>Cruise Reservation</div>" +
                "<div style='text-align: center; font-size: 11px;'>Logged in as travel agent %s</div>" +
                "<div style='text-align: center;'>%s</div></html>", name, "Total Account Population is " + count));

        /* If the travel agent has no name, it is because their account was just
        created by an admin and needs to be finished */
        if (Objects.equals(AccountDatabase.getUser(account.getUsername()).getFirstName(), "") && Objects.equals(account.getFirstName(), "")) {
            finishAccountCreation(account);
        }
        else {
            mainFrame.setVisible(true);
        }
    }

    /**
     * Finishes the account creation of a travel agent
     *
     * @param account The account to finish creating
     */
    private void finishAccountCreation(User account) {
        new FinishTravelAgentPage(this, account);
    }

    /**
     * Loads an image from the internet
     *
     * @param path URL to get the image from
     */
    private ImageIcon createImageIcon(String path) {
        try {
            URL imgURL = new URL(path);
            if (imgURL != null) {
                return new ImageIcon(new ImageIcon(imgURL).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
            } else {
                System.err.println("Couldn't find file: " + path);
                return null;
            }
        } catch (MalformedURLException e) {
            return null;
        }
    }
    private void navigateToEditReservations() throws UserNotFoundException {
        mainFrame.setVisible(false);   // hide the current landing page
        new GuestsWithReservationPage(this);
    }

    private void navigateToAddRooms() {
        //does not pass the landing page instance because it is a pop-up
        new AddRoomPage();
    }

    private void navigateToModifyRooms() {
        //get specified room in room database then modify it if necessary
        mainFrame.setVisible(false);
        RoomDatabase roomList = new RoomDatabase();

        new ViewAllRoomsPage(this);
    }

    private void navigateToEditProfile() {
        mainFrame.setVisible(false);   // hide the current landing page
        new EditProfile(account, this, null, true);
    }

    public void invalidDecision(){
        JOptionPane.showMessageDialog(mainFrame
                , "Invalid Input for Room Modification", "Error!", JOptionPane.OK_OPTION);
    }

    public void show() {
        mainFrame.setVisible(true);
    }
}