package edu.ui.cruiseDetails;

import edu.core.cruise.Cruise;
import edu.ui.landingPage.LandingPage;

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
        List<String> cruisesFromDatabase = SelectCruiseController.getCruiseNames();
        String[] cruiseArray = cruisesFromDatabase.toArray(new String[0]);
        cruiseList = new JList<>(cruiseArray);
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
        String selectedCruiseName = cruiseList.getSelectedValue();
        if (selectedCruiseName != null) {
            int dialogResult = JOptionPane.showConfirmDialog(cruiseFrame, "View details for " + selectedCruiseName + "?", "Confirmation", JOptionPane.OK_CANCEL_OPTION);
            if (dialogResult == JOptionPane.OK_OPTION) {

                Cruise selectedCruise = SelectCruiseController.getCruiseDetails(selectedCruiseName);
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
