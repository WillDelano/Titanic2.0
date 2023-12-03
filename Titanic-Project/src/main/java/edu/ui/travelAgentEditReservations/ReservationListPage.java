package edu.ui.travelAgentEditReservations;

import edu.core.cruise.Cruise;
import edu.core.reservation.Reservation;
import edu.core.users.Guest;
import edu.exceptions.NoMatchingReservationException;
import edu.ui.reservationListInterface.ReservationListInterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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


    private void checkIn(JTable table) throws NoMatchingReservationException {
        Reservation r;
        int selectedRow;
        selectedRow = table.getSelectedRow();
        boolean checkedIn = false;

        //check if a row is selected
        if (selectedRow >= 0) {

            int modelRow = table.convertRowIndexToModel(selectedRow);
            DefaultTableModel model = (DefaultTableModel) table.getModel();

            //you can't cast the object to an int in case it's null, so you have to cast to string, then cast to int
            String row = (String) model.getValueAt(modelRow, 0);
            int intRow = Integer.parseInt(row);

            String userName = String.valueOf(model.getValueAt(intRow, 1));
            r = ReservationListPageController.getReservation(userName);
            checkedIn = ReservationListPageController.CheckInGuest(r);

        }
    }

    private void checkOut(JTable table) {
        Reservation r;
        int selectedRow;
        selectedRow = table.getSelectedRow();
        boolean checkedIn = false;

        if (selectedRow >= 0) {
            JFrame frame = new JFrame("Check Out Confirmation");
            JPanel panel = new JPanel();
            JButton addButton = new JButton("Add row");
            addButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            JLabel confirmation = new JLabel("Are you sure you want to check out:" + r.getUser());
            confirmation.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(confirmation);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            int modelRow = table.convertRowIndexToModel(selectedRow);
            DefaultTableModel model = (DefaultTableModel) table.getModel();

            //you can't cast the object to an int in case it's null, so you have to cast to string, then cast to int
            String row = (String) model.getValueAt(modelRow, 0);
            int intRow = Integer.parseInt(row);

            String userName = String.valueOf(model.getValueAt(intRow, 1));
            JButton checkOutButton = new JButton("Yes Check Out");
            checkOutButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ReservationListPageController.CheckOutGuest(r);
                    frame.dispose();
                }
            });
            panel.add(checkOutButton);

            JButton closeButton = new JButton("no");
            closeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                }
            });

            frame.add(panel);
            frame.add(closeButton);
            frame.setLocationRelativeTo(null);

            frame.setVisible(true);

        }
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