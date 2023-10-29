package edu.ui.cruiseDetails;

import edu.core.cruise.Cruise;
import edu.database.CruiseDatabase;
import edu.ui.landingPage.LandingPage;
import edu.ui.roomDetails.BrowseRoomPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * Controller for displaying and selecting a cruise on the ui
 *
 * This class allows a user to browse and select cruises
 *
 * @author Vincent Dinh
 * @version 1.0
 * @see Cruise, CruiseDatabase, CruiseDetailsPage
 */
public class SelectCruisePage {
    private JFrame cruiseFrame;
    private JLabel titleLabel;
    private JList<String> cruiseList;
    private JButton selectButton;
    private JButton backButton;
    private LandingPage landingPage;
    private JTextArea detailsTextArea;

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

        List<String> cruisesFromDatabase = SelectCruiseController.getCruiseNames();
        String[] cruiseArray = cruisesFromDatabase.toArray(new String[0]);
        cruiseList = new JList<>(cruiseArray);
        JScrollPane listScrollPane = new JScrollPane(cruiseList);
        cruiseFrame.add(listScrollPane, BorderLayout.CENTER);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));

        for (int i = 0; i < cruisesFromDatabase.size(); i++) {
            // get a cruise from the database list
            Cruise cruise = CruiseDatabase.getCruise(cruisesFromDatabase.get(i));

            StringBuilder travelPath = new StringBuilder();
            for (int j = 0; j < cruise.getTravelPath().size(); j++) {
                travelPath.append(cruise.getTravelPath().get(j).getName());
                if (j < cruise.getTravelPath().size() - 1) {
                    travelPath.append(", ");
                }
            }

            String actualDetails = "Cruise Name: " + cruise.getName() + "\n" +
                    "Departure Date: " + cruise.getDeparture().toString() + "\n" +
                    "Travel Path: " + travelPath.toString() + "\n" +
                    "Max Capacity: " + cruise.getMaxCapacity() + " passengers\n" +
                    "Current Occupancy: " + cruise.getCurrentOccupancy() + " passengers\n";

            JTextArea detailsTextArea = new JTextArea(actualDetails);
            detailsTextArea.setEditable(false);
            JScrollPane textScrollPane = new JScrollPane(detailsTextArea);
            detailsPanel.add(textScrollPane);

            JButton selectButton = new JButton("Select " + cruise.getName());
            selectButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            selectButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    handleCruiseSelection(cruise);
                }
            });
            detailsPanel.add(selectButton);
        }

        mainPanel.add(detailsPanel, BorderLayout.CENTER);

        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            cruiseFrame.dispose(); // Close the Select Cruise page
            landingPage.show(); // Go back to the landingPage
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        cruiseFrame.add(mainPanel);
        cruiseFrame.setVisible(true);
    }

    private void handleCruiseSelection(Cruise cruise) {
        String selectedCruiseName = cruise.getName();
        /*if (selectedCruiseName != null) {
            int dialogResult = JOptionPane.showConfirmDialog(cruiseFrame, "View details for " + selectedCruiseName + "?", "Confirmation", JOptionPane.OK_CANCEL_OPTION);
            if (dialogResult == JOptionPane.OK_OPTION) {

                Cruise selectedCruise = SelectCruiseController.getCruiseDetails(selectedCruiseName);
                new CruiseDetailsPage(selectedCruise);
            }
        } else {
            showMessage("Please select a cruise first.");
        }*/
        Cruise selectedCruise = SelectCruiseController.getCruiseDetails(selectedCruiseName);
        new BrowseRoomPage(selectedCruise.getName());
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
