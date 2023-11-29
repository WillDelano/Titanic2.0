package edu.ui.cruiseDetails;

import edu.core.cruise.Cruise;
import edu.databaseAccessors.CruiseDatabase;
import edu.ui.landingPage.LandingPage;
import edu.ui.roomDetails.BrowseRoomPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        List<Cruise> cruisesFromDatabase = CruiseDatabase.getAllCruises();
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Cruise cruise : cruisesFromDatabase) {
            if (!model.contains(cruise.getName())) {
                model.addElement(cruise.getName());
            }
        }
        cruiseList = new JList<>(model);
        JScrollPane listScrollPane = new JScrollPane(cruiseList);
        cruiseFrame.add(listScrollPane, BorderLayout.CENTER);


        selectButton = new JButton("View Details");
        selectButton.addActionListener(this::handleCruiseSelection);
        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            cruiseFrame.dispose();
            if (landingPage != null) landingPage.show();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(selectButton);
        buttonPanel.add(backButton);

        cruiseFrame.add(buttonPanel, BorderLayout.SOUTH);
        cruiseFrame.setVisible(true);
    }


    private void handleCruiseSelection(ActionEvent e) {
        String selectedCruiseName = cruiseList.getSelectedValue();
        if (selectedCruiseName != null) {
            Cruise selectedCruise = CruiseDatabase.getCruise(selectedCruiseName);
            if (selectedCruise != null) {
                new BrowseRoomPage(selectedCruise.getName()).show(); // Assuming BrowseRoomPage takes a Cruise name
            } else {
                JOptionPane.showMessageDialog(cruiseFrame, "Cruise details not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(cruiseFrame, "Please select a cruise first.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
    private void showMessage(String message) {
        JOptionPane.showMessageDialog(cruiseFrame, message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SelectCruisePage(null));
    }
}
