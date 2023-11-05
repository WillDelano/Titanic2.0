package edu.ui.landingPage;

import edu.core.users.TravelAgent;
import edu.core.users.User;
import edu.databaseAccessors.AccountDatabase;
import edu.ui.addRoom.AddRoomPage;
import edu.ui.editProfile.EditProfile;
import edu.ui.editReservation.GuestsWithReservationPage;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

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

    /**
     * Constructor for the landing page that creates the GUI
     *
     */
    public TravelAgentLandingPage() {
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
        headerPanel = new JPanel(new GridLayout(2, 4));

        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel middlePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton browseCruisesButton = new JButton("Edit Reservations");
        browseCruisesButton.addActionListener(e -> navigateToEditReservations());

        JButton myReservationsButton = new JButton("Add Rooms");
        myReservationsButton.addActionListener(e -> navigateToAddRooms());

        JButton supportButton = new JButton("Edit Profile");
        supportButton.addActionListener(e -> navigateToEditProfile());

        headerPanel.add(browseCruisesButton);
        headerPanel.add(myReservationsButton);
        headerPanel.add(supportButton, BorderLayout.EAST);

        headerPanel.add(topPanel);
        headerPanel.add(new JPanel());
        headerPanel.add(middlePanel);

        mainFrame.add(headerLabel, BorderLayout.CENTER);
        mainFrame.add(headerPanel, BorderLayout.NORTH);
        mainFrame.setVisible(true);
    }

    /**
     * Sets the landing page to visible and displays the center text with a user's name and email
     *
     * @param account The user who is logged in
     */
    public void showLandingPage(User account) {
        this.account = account;

        String name = account.getFirstName() + " " + account.getLastName();
        int count = AccountDatabase.getUserCount();

        headerLabel.setText(String.format("<html>" +
                "<div style='text-align: center; font-size: 24px;'>Cruise Reservation</div>" +
                "<div style='text-align: center; font-size: 11px;'>Logged in as travel agent %s</div>" +
                "<div style='text-align: center;'>%s</div></html>", name, "Total Account Population is " + count));

        mainFrame.setVisible(true);
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
    private void navigateToEditReservations() {
        mainFrame.setVisible(false);   // hide the current landing page
        new GuestsWithReservationPage(this);
    }

    private void navigateToAddRooms() {
        //does not pass the landing page instance because it is a pop-up
        new AddRoomPage();
    }

    private void navigateToEditProfile() {
        mainFrame.setVisible(false);   // hide the current landing page
        new EditProfile(account, this);
    }

    public void show() {
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new TravelAgentLandingPage();
    }
}
