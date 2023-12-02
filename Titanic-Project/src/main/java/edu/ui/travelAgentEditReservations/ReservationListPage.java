package edu.ui.travelAgentEditReservations;

import edu.core.cruise.Cruise;
import edu.core.reservation.Reservation;
import edu.core.users.Guest;
import edu.ui.reservationListInterface.ReservationListInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Set;

/**
 * Controller for displaying and selecting a cruise on the ui
 *
 * This class allows a user to browse and select cruises
 *
 * @author Vincent Dinh
 * @version 1.0
 * @see Cruise , CruiseDatabase, CruiseDetailsPage
 */
public class ReservationListPage implements ReservationListInterface {
    private JFrame mainFrame;
    private JLabel titleLabel;
    private JButton selectButton;
    private JButton backButton;
    private GuestsWithReservationPage previousPage;
    private Guest guest;

    public ReservationListPage(GuestsWithReservationPage previousPage, Guest guest) {
        this.previousPage = previousPage;
        this.guest = guest;
        prepareGUI();
    }

    private void prepareGUI() {
        mainFrame = new JFrame("Select a Cruise");
        mainFrame.setSize(1000, 700);
        mainFrame.setLayout(new BorderLayout());

        titleLabel = new JLabel(guest.getUsername() + "'s Reservations", JLabel.CENTER);
        mainFrame.add(titleLabel, BorderLayout.NORTH);

        Set<Reservation> listOfReservations = null;
        try {
            listOfReservations = ReservationListPageController.getReservationList(guest);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));

        for (Reservation reservation : listOfReservations) {
            String startDate = String.valueOf(reservation.getStartDate());
            String endDate = String.valueOf(reservation.getEndDate());
            String roomNumber = String.valueOf(reservation.getRoom().getRoomNumber());
            String startCountry = reservation.getStartCountry().getName();
            String endCountry = reservation.getEndCountry().getName();
            String totalDays = String.valueOf(reservation.getDays());

            String guestDetails = "Reservation ID: " + reservation.getId() + "\n" +
                    "Check-in Date: " + startDate + "\n" +
                    "Check-out Date: " + endDate + "\n" +
                    "Room Number: " + roomNumber + "\n" +
                    "Start Country: " + startCountry + "\n" +
                    "End Country: " + endCountry + "\n" +
                    "Total Trip Duration (In Days): " + totalDays + "\n";

            JTextArea detailsTextArea = new JTextArea(guestDetails);
            detailsTextArea.setEditable(false);
            JScrollPane textScrollPane = new JScrollPane(detailsTextArea);
            detailsPanel.add(textScrollPane);

            JButton selectButton = new JButton("Select Reservation ID: " + reservation.getId());
            selectButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            //Check In Button and Check Out Button


            selectButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    handleReservationSelection(reservation);
                }
            });
            detailsPanel.add(selectButton);
        }

        mainPanel.add(detailsPanel, BorderLayout.CENTER);

        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            mainFrame.dispose(); // Close the Edit Reservation page
            previousPage.show(); // Go back to the landingPage
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
    }

    private void handleReservationSelection(Reservation reservation) {
        mainFrame.setVisible(false);
        new EditReservationPage(this, reservation.getRoom().getCruise(), reservation);
    }



    private void refreshData() {

    }

    public void show() {
        mainFrame.setVisible(true);

        refreshData();
    }
}