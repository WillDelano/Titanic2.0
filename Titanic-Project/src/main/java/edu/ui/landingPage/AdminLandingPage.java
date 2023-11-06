package edu.ui.landingPage;

import edu.core.users.Admin;
import edu.core.users.User;

import javax.swing.*;
import java.awt.*;

/**
 * UI display for the admin landing page
 *
 * This class creates admin landing page
 *
 * @author Gabriel Choi
 * @version 1.0
 * @see Admin
 */
public class AdminLandingPage extends LandingPage{
    private JFrame mainFrame;
    private JPanel headerPanel;
    private JLabel headerLabel;

    /**
     * Constructor for the admin landing page that creates the GUI
     *
     */
    public AdminLandingPage(){
        createGUI();
    }

    public void createGUI(){
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        mainFrame = new JFrame("Admin Application");
        mainFrame.setSize(1000, 700);
        mainFrame.setLayout(new BorderLayout());

        headerLabel = new JLabel("", JLabel.CENTER);
        headerPanel = new JPanel(new GridLayout(2, 4));

        JPanel topPanel = new JPanel(new BorderLayout());
        //topPanel.add(new JLabel("SPPP"), BorderLayout.CENTER);
        JPanel middlePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton accountManagementButton = new JButton("Manage Users");
        //browseCruisesButton.addActionListener(e -> navigateToSelectCruisePage());

        JButton myReservationsButton = new JButton("My Reservations");
        //myReservationsButton.addActionListener(e -> openMyReservationsPage());

        JButton supportButton = new JButton("Support");

        headerPanel.add(topPanel);
        headerPanel.add(new JPanel());
        headerPanel.add(middlePanel);

        headerPanel.add(accountManagementButton);
        headerPanel.add(myReservationsButton);
        headerPanel.add(supportButton, BorderLayout.EAST);

        mainFrame.add(headerLabel, BorderLayout.CENTER);
        mainFrame.add(headerPanel, BorderLayout.NORTH);
        mainFrame.setVisible(true);
    }

    public void showLandingPage(User account) {
        String name = account.getFirstName() + " " + account.getLastName();

        headerLabel.setText(String.format("<html>" +
                "<div style='text-align: center; font-size: 24px;'>Admin Dashboard</div>" +
                "<div style='text-align: center; font-size: 11px;'>Logged in as %s</div>" +
                "<div style='text-align: center;'>%s</div></html>", name, account.getEmail()));

        mainFrame.setVisible(true);
    }

    public static void main(String[] args){
        AdminLandingPage adminLandingPage = new AdminLandingPage();
        Admin admin = new Admin("admin", "admin", 1, "Gabe", "Choi", "gabechoi03@gmail.com");
        adminLandingPage.showLandingPage(admin);
    }
}
