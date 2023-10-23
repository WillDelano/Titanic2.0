package edu.ui.cruiseDetails;

import edu.ui.landingPage.LandingPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SelectCruisePage {

    private JFrame cruiseFrame;
    private JLabel titleLabel;
    private JList<String> cruiseList;
    private JButton selectButton;
    private JButton backButton;

    private LandingPage landingPage;

    public SelectCruisePage(LandingPage landingPage) {
        this.landingPage = landingPage;
        prepareGUI();
    }

    private void prepareGUI() {
        cruiseFrame = new JFrame("Select a Cruise");
        cruiseFrame.setSize(1000, 700);
        cruiseFrame.setLayout(new BorderLayout());

        titleLabel = new JLabel("Available Cruises", JLabel.CENTER);
        cruiseFrame.add(titleLabel, BorderLayout.NORTH);

        // TODO Replace with actual cruises
        String[] sampleCruises = {"Caribbean Cruise", "Mediterranean Cruise", "Alaskan Cruise"};
        cruiseList = new JList<>(sampleCruises);
        JScrollPane listScrollPane = new JScrollPane(cruiseList);
        cruiseFrame.add(listScrollPane, BorderLayout.CENTER);

        selectButton = new JButton("Select Cruise");
        selectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleCruiseSelection();
            }
        });

        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            cruiseFrame.dispose(); // Close the Select Cruise page
            landingPage.show(); // Go back to the landingPage
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(selectButton);
        buttonPanel.add(backButton);

        cruiseFrame.add(buttonPanel, BorderLayout.SOUTH);
        cruiseFrame.setVisible(true);
    }

    private void handleCruiseSelection() {
        String selectedCruise = cruiseList.getSelectedValue();
        if (selectedCruise != null) {
            int dialogResult = JOptionPane.showConfirmDialog(cruiseFrame, "View details for " + selectedCruise + "?", "Confirmation", JOptionPane.OK_CANCEL_OPTION);
            if (dialogResult == JOptionPane.OK_OPTION) {
                // if "OK" is clicked, open the CruiseDetailsPage
                new CruiseDetailsPage(selectedCruise);
            }
        } else {
            showMessage("Please select a cruise first.");
        }
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(cruiseFrame, message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SelectCruisePage(null); // Pass your LandingPage instance here
        });
    }
}
