package edu.ui.cruiseDetails;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CruiseDetailsPage {

    private JFrame detailsFrame;
    private JLabel cruiseLabel;
    private JTextArea detailsTextArea;
    private JButton selectRoomButton;

    public CruiseDetailsPage(String cruiseName) {
        prepareGUI(cruiseName);
    }

    private void prepareGUI(String cruiseName) {
        detailsFrame = new JFrame("Cruise Details: " + cruiseName);
        detailsFrame.setSize(800, 600);
        detailsFrame.setLayout(new BorderLayout());

        cruiseLabel = new JLabel("Cruise Details: " + cruiseName, JLabel.CENTER);
        detailsFrame.add(cruiseLabel, BorderLayout.NORTH);

        // Sample cruise details
        //TODO make controller to get cruise details from backend
        String sampleDetails = "Cruise Name: " + cruiseName + "\n" +
                "Departure Date: January 1, 2024\n" +
                "Travel Path: Caribbean, Mediterranean, Alaskan\n" +
                "Max Capacity: 200 passengers\n" +
                "Current Occupancy: 100 passengers\n";

        detailsTextArea = new JTextArea(sampleDetails);
        detailsTextArea.setEditable(false);
        JScrollPane textScrollPane = new JScrollPane(detailsTextArea);
        detailsFrame.add(textScrollPane, BorderLayout.CENTER);

        selectRoomButton = new JButton("Browse Rooms");
        selectRoomButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //TODO Add room selection functionality
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(selectRoomButton);

        detailsFrame.add(buttonPanel, BorderLayout.SOUTH);
        detailsFrame.setVisible(true);
    }
}
