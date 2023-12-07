package edu.ui.landingPage;

import edu.core.reservation.Room;
import edu.core.users.User;
import edu.databaseAccessors.AccountDatabase;
import edu.ui.adminCreateTravelAgent.CreateTravelAgentPage;
import edu.ui.adminResetPasswords.ResetPasswordListPage;
import edu.ui.authentication.LoginPage;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Creates the admin landing page
 *
 * This class creates the admin landing page for the cruise reservation application
 *
 * @author William Delano
 * @version 1.0
 * @see LandingPage
 */
public class AdminLandingPage extends LandingPage {

    private JFrame mainFrame;
    private JPanel headerPanel;
    private JPanel logoutPanel;
    private JLabel logoutLabel;
    private JLabel headerLabel;
    private User account;

    private Room room;

    /**
     * Constructor for the landing page that creates the GUI
     */
    public AdminLandingPage() {
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
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        headerLabel = new JLabel("", JLabel.CENTER);
        headerPanel = new JPanel(new GridLayout(2, 5));

        logoutLabel = new JLabel("", JLabel.CENTER);
        logoutPanel = new JPanel(new GridLayout(1, 5));

        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel middlePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton resetPasswordButton = new JButton("Edit an Account");
        resetPasswordButton.addActionListener(e -> navigateToResetPassword());

        JButton addTravelAgentButton = new JButton("Add Travel Agent");
        addTravelAgentButton.addActionListener(e -> navigateToNewTravelAgent());

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> logout());

        headerPanel.add(resetPasswordButton);
        headerPanel.add(new JPanel());
        headerPanel.add(addTravelAgentButton);

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

        mainFrame.setVisible(true);
    }

    /**
     * Logs out the current user and navigates to the login page.
     */
    private void logout() {
        mainFrame.dispose();
        new LoginPage();
    }

    /**
     * Sets the landing page to visible and displays the center text with a user's name and email
     *
     * @param account The user who is logged in
     */
    public void showLandingPage(User account) {
        this.account = account;

        String name = account.getFirstName();
        int count = AccountDatabase.getUserCount();

        this.logoutLabel.setText(String.format("<html>" +
                "<div style='text-align: center; font-size: 24px;'>Cruise Reservation</div>" +
                "<div style='text-align: center; font-size: 11px;'>Logged in as %s</div>" +
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

    /**
     * Navigates to the reset password page.
     */
    private void navigateToResetPassword() {
        mainFrame.setVisible(false);   // hide the current landing page
        new ResetPasswordListPage(this);
    }

    /**
     * Navigates to the new travel agent page.
     */
    private void navigateToNewTravelAgent() {
        mainFrame.setVisible(false);   // hide the current landing page
        new CreateTravelAgentPage(this);
    }

    /**
     * Refreshes the data and displays the landing page.
     */
    public void show() {
        //refresh the data on show
        String name = account.getFirstName();
        int count = AccountDatabase.getUserCount();

        this.logoutLabel.setText(String.format("<html>" +
                "<div style='text-align: center; font-size: 24px;'>Cruise Reservation</div>" +
                "<div style='text-align: center; font-size: 11px;'>Logged in as %s</div>" +
                "<div style='text-align: center;'>%s</div></html>", name, "Total Account Population is " + count));

        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new AdminLandingPage();
    }
}
