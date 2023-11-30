package edu.ui.travelAgentEditReservations;

import edu.core.cruise.Cruise;
import edu.core.users.Guest;
import edu.exceptions.UserNotFoundException;
import edu.ui.landingPage.TravelAgentLandingPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
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
public class GuestsWithReservationPage {
    private TravelAgentLandingPage landingPage;
    private JFrame mainFrame;
    private JLabel titleLabel;
    private JButton backButton;
    private JButton newReservation;

    public GuestsWithReservationPage(TravelAgentLandingPage landingPage) throws SQLException {
        this.landingPage = landingPage;
        prepareGUI();
    }

    private void prepareGUI() throws SQLException {
        mainFrame = new JFrame("Select a Cruise");
        mainFrame.setSize(1000, 700);
        mainFrame.setLayout(new BorderLayout());

        titleLabel = new JLabel("Guests with a Reservation", JLabel.CENTER);
        mainFrame.add(titleLabel, BorderLayout.NORTH);

        List<Guest> guestsWithReservations = null;

        try {
            guestsWithReservations = GuestsWithReservationController.getGuestsWithReservations();
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));

        for (int i = 0; i < guestsWithReservations.size(); i++) {
            // get a guest from the list
            Guest guest = guestsWithReservations.get(i);

            String username = guest.getUsername();
            String first = guest.getFirstName();
            String last = guest.getLastName();
            String numReservations = Integer.toString(guest.getReservations().size());

            String guestDetails = "Guest username: " + username + "\n" +
                    "First name: " + first + "\n" +
                    "Last name: " + last + "\n" +
                    "Reservations: " + numReservations +" \n";

            JTextArea detailsTextArea = new JTextArea(guestDetails);
            detailsTextArea.setEditable(false);
            JScrollPane textScrollPane = new JScrollPane(detailsTextArea);
            detailsPanel.add(textScrollPane);

            JButton selectButton = new JButton("Select " + guest.getUsername());
            selectButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            selectButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        handleReservationList(guest);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            detailsPanel.add(selectButton);
        }

        mainPanel.add(detailsPanel, BorderLayout.CENTER);

        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            mainFrame.dispose(); // Close the Edit Reservation page
            landingPage.show(); // Go back to the landingPage
        });

        newReservation = new JButton("Create a new reservation");
        newReservation.addActionListener(e -> createReservation());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(newReservation);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
    }

    private void handleReservationList(Guest guest) throws SQLException {
        mainFrame.setVisible(false);
        new ReservationListPage(this, guest);
    }

    private void createReservation() {
        //new TravelAgentCreateReservationPage(this);
    }

    public void show() {
        mainFrame.setVisible(true);
    }
}
