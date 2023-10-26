package edu.ui.cruiseDetails;

import edu.core.cruise.Cruise;
import edu.ui.roomDetails.BrowseRoomPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CruiseDetailsPage {

    private JFrame detailsFrame;
    private JLabel cruiseLabel;
    private JTextArea detailsTextArea;
    private JButton selectRoomButton;

    private Cruise cruiseDetails;

    public CruiseDetailsPage(Cruise cruiseDetails) {
        this.cruiseDetails = cruiseDetails;
        prepareGUI();
    }

    private void prepareGUI() {
        detailsFrame = new JFrame("Cruise Details: " + cruiseDetails.getName());
        detailsFrame.setSize(800, 600);
        detailsFrame.setLayout(new BorderLayout());

        cruiseLabel = new JLabel("Cruise Details: " + cruiseDetails.getName(), JLabel.CENTER);
        detailsFrame.add(cruiseLabel, BorderLayout.NORTH);

        // Fetching actual cruise details
        StringBuilder travelPath = new StringBuilder();
        for (int i = 0; i < cruiseDetails.getTravelPath().size(); i++) {
            travelPath.append(cruiseDetails.getTravelPath().get(i).getName());
            if (i < cruiseDetails.getTravelPath().size() - 1) {
                travelPath.append(", ");
            }
        }

        String actualDetails = "Cruise Name: " + cruiseDetails.getName() + "\n" +
                "Departure Date: " + cruiseDetails.getDeparture().toString() + "\n" +
                "Travel Path: " + travelPath.toString() + "\n" +
                "Max Capacity: " + cruiseDetails.getMaxCapacity() + " passengers\n" +
                // assuming you have a method getCurrentOccupancy() in Cruise class
                "Current Occupancy: " + cruiseDetails.getCurrentOccupancy() + " passengers\n";

        detailsTextArea = new JTextArea(actualDetails);
        detailsTextArea.setEditable(false);
        JScrollPane textScrollPane = new JScrollPane(detailsTextArea);
        detailsFrame.add(textScrollPane, BorderLayout.CENTER);

        selectRoomButton = new JButton("Browse Rooms");
        selectRoomButton.addActionListener(e -> new BrowseRoomPage(cruiseDetails.getName()));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(selectRoomButton);

        detailsFrame.add(buttonPanel, BorderLayout.SOUTH);
        detailsFrame.setVisible(true);
    }
}
