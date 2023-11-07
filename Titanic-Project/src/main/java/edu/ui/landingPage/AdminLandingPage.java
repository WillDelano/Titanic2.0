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
        headerPanel = new JPanel(new GridLayout(2, 2));

        JPanel topPanel = new JPanel(new BorderLayout());
        //topPanel.add(new JLabel("yuh", JLabel.CENTER), BorderLayout.CENTER);

        JButton accountManagementButton = new JButton("Manage Users");
        accountManagementButton.addActionListener(e -> navigateToManageUsers());

        JButton tbaButton = new JButton("TBA");
        //tbaButton.addActionListener(e -> openMyReservationsPage());

        headerPanel.add(topPanel);
        headerPanel.add(new JPanel());

        headerPanel.add(accountManagementButton);
        headerPanel.add(tbaButton);

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

    public void navigateToManageUsers(){
        mainFrame.setVisible(false);
    }

    public static void main(String[] args){
        AdminLandingPage adminLandingPage = new AdminLandingPage();
        Admin admin = new Admin("admin", "admin", 1, "Gabe", "Choi", "gabechoi03@gmail.com");
        adminLandingPage.showLandingPage(admin);
    }
}
