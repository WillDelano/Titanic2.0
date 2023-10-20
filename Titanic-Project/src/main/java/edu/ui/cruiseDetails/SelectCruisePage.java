package edu.ui.cruiseDetails;

import edu.ui.landingPage.LandingPage;

import javax.swing.*;
import java.awt.*;

/**
 * Represents the page where users can select a cruise.
 *
 * This class creates the page for users to view and select available cruises.
 *
 * @author Vincent Dinh
 * @version 1.0
 */
public class SelectCruisePage {

    private JFrame cruiseFrame;
    private JLabel titleLabel;
    private JList<String> cruiseList;
    private JButton selectButton;
    private JButton backButton;

    private LandingPage landingPage;
    /**
     * Constructor for the SelectCruisePage that sets up the GUI
     */
    public SelectCruisePage(LandingPage landingPage) {
        this.landingPage = landingPage;
        prepareGUI();
    }

    /**
     * Sets up the GUI for the Select Cruise page
     */
    private void prepareGUI() {
        cruiseFrame = new JFrame("Select a Cruise");
        cruiseFrame.setSize(1000, 700);
        cruiseFrame.setLayout(new BorderLayout());

        titleLabel = new JLabel("Available Cruises", JLabel.CENTER);
        cruiseFrame.add(titleLabel, BorderLayout.NORTH);

        //TODO Replace with actual cruises
        String[] sampleCruises = {"Caribbean Cruise", "Mediterranean Cruise", "Alaskan Cruise"};
        cruiseList = new JList<>(sampleCruises);
        JScrollPane listScrollPane = new JScrollPane(cruiseList);
        cruiseFrame.add(listScrollPane, BorderLayout.CENTER);

        selectButton = new JButton("Select Cruise");
        selectButton.addActionListener(e -> {
            // logic to handle cruise selection
            String selectedCruise = cruiseList.getSelectedValue();
            if (selectedCruise != null) {
                // handle the cruise selection logic
                JOptionPane.showMessageDialog(cruiseFrame, "You selected: " + selectedCruise);
            } else {
                JOptionPane.showMessageDialog(cruiseFrame, "Please select a cruise first.");
            }
        });

        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            cruiseFrame.dispose();
            landingPage.show(); // close the Select Cruise page
            // return to the previous screen, in this case, the Landing Page
            // new edu.ui.landingPage.LandingPage().showLandingPage(yourUserObjectHere);
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(selectButton);
        buttonPanel.add(backButton);

        cruiseFrame.add(buttonPanel, BorderLayout.SOUTH);
        cruiseFrame.setVisible(true);
    }
}
