package edu.ui.landingPage;

import edu.core.users.CurrentGuest;
import edu.core.users.Guest;
import edu.core.users.User;
import edu.ui.authentication.LoginPage;
import edu.ui.guestSelectCruise.SelectCruisePage;

import javax.swing.*;
import java.awt.*;

/**
 * Creates the landing page
 *
 * This class creates the landing page for the cruise reservation application
 *
 * @author William Delano
 * @version 1.0
 * @see LandingPageController
 */
public class GuestLandingPage extends LandingPage {

    private JFrame mainFrame;
    private JPanel headerPanel;
    private JLabel headerLabel;

    /**
     * Constructor for the landing page that creates the GUI
     *
     */
    public GuestLandingPage() {
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

        JButton browseCruisesButton = new JButton("Browse Cruises");
        browseCruisesButton.addActionListener(e -> navigateToSelectCruisePage());

        JButton myReservationsButton = new JButton("My Reservations");
        myReservationsButton.addActionListener(e -> openMyReservationsPage());

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> logout());

        headerPanel.add(browseCruisesButton);
        headerPanel.add(myReservationsButton);
        headerPanel.add(logoutButton, BorderLayout.EAST);

        headerPanel.add(topPanel);
        headerPanel.add(new JPanel());
        headerPanel.add(middlePanel);

        mainFrame.add(headerLabel, BorderLayout.CENTER);
        mainFrame.add(headerPanel, BorderLayout.NORTH);

        mainFrame.setVisible(true);
    }

    private void logout() {
        mainFrame.dispose();
        CurrentGuest.logoutCurrentGuest();
        new LoginPage();
    }

    /**
     * Sets the landing page to visible and displays the center text with a user's name and email
     *
     * @param account The user who is logged in
     */
    public void showLandingPage(User account) {
        String name = account.getFirstName() + " " + account.getLastName();

        headerLabel.setText(String.format("<html>" +
                "<div style='text-align: center; font-size: 24px;'>Cruise Reservation</div>" +
                "<div style='text-align: center; font-size: 11px;'>Logged in as %s</div>" +
                "<div style='text-align: center;'>%s</div></html>", name, account.getEmail()));

        mainFrame.setVisible(true);
    }

    private void navigateToSelectCruisePage() {
        mainFrame.setVisible(false);   // hide the current landing page
        new SelectCruisePage(this);       // navigate to SelectCruisePage
    }

    private void openMyReservationsPage() {
        new edu.ui.guestReservationList.MyReservationsPage().show();
    }

    public void show() {
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new GuestLandingPage();
        Guest g = new Guest("wdelano", "baylor", "Will", "Delano", 0, "wdelano2002@gmail.com");

        CurrentGuest.setCurrentGuest(g);
    }
}
