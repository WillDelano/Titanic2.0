package edu.ui.landingPage;

import edu.core.users.CurrentGuest;
import edu.core.users.Guest;
import edu.core.users.User;
import edu.ui.cruiseDetails.SelectCruisePage;
import edu.uniqueID.UniqueID;

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
 */
public class LandingPage {

    private JFrame mainFrame;
    private JPanel headerPanel;
    private JLabel headerLabel;

    /**
     * Constructor for the landing page that creates the GUI
     *
     */
    public LandingPage() {
        prepareGUI();
    }


    /**
     * Calls the function to display the landing page and passes the logged-in user's account
     *
     */
    public static void main(String[] args) {
        Guest account;

        /*
        Something like:

        LoginPage loginPage = new LoginPage()
        user = loginPage.showLoginPage;

        And the login would return the user that was logged in to
         */

        account = new Guest("username", "password", 0, "John", "Doe", 0, "john_doe1@baylor.edu");
        CurrentGuest.setCurrentGuest(account);

        LandingPage landingPage = new LandingPage();
        landingPage.showLandingPage(account);
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

        JButton browseCruisesButton = new JButton("Browse cruises");
        browseCruisesButton.addActionListener(e -> navigateToSelectCruisePage());
        JButton myReservationsButton = new JButton("My Reservations");
        myReservationsButton.addActionListener(e -> openMyReservationsPage());

        JButton supportButton = new JButton("Support");

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
    private void showLandingPage(User account) {
        String name = account.getFirstName() + " " + account.getLastName();

        headerLabel.setText(String.format("<html>" +
                "<div style='text-align: center; font-size: 24px;'>Cruise Reservation</div>" +
                "<div style='text-align: center; font-size: 11px;'>Logged in as %s</div>" +
                "<div style='text-align: center;'>%s</div></html>", name, account.getEmail()));

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
    private void navigateToSelectCruisePage() {
        mainFrame.setVisible(false);   // hide the current landing page
        new SelectCruisePage(this);       // navigate to SelectCruisePage
    }

    private void openMyReservationsPage() {
        new edu.ui.reservationDetails.MyReservationsPage().show();
    }


    public void show() {
        mainFrame.setVisible(true);
    }
}
