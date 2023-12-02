package edu.ui.guestCreateReservation;

import edu.core.cruise.Country;
import edu.core.cruise.Cruise;
import edu.core.reservation.Reservation;
import edu.core.reservation.Room;
import edu.core.users.CurrentGuest;
import edu.core.users.User;
import edu.databaseAccessors.CruiseDatabase;
import edu.exceptions.CouldNotCreateReservationException;
import edu.exceptions.UserNotFoundException;
import edu.ui.guestBrowseRooms.BrowseRoomPage;
import edu.ui.travelAgentEditReservations.GuestsWithReservationPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GuestCreateReservationPage {
    private JFrame frame;
    private JLabel titleLabel;
    private JPanel mainPanel;
    private JLabel checkoutLabel;
    private JLabel roomNumberLabel;
    private JTextField checkoutField;
    private JTextField roomNumberField;
    private JButton submitButton;
    private JButton backButton;
    private BrowseRoomPage previousPage;
    JComboBox<String> checkoutDropdown;
    JComboBox<String> roomNumberDropdown;
    JComboBox<String> cruiseDropdown;
    JTextField checkinField;
    JLabel checkinLabel;
    JLabel usernameLabel;
    JTextField usernameField;
    Room roomToReserve;
    boolean test = false;

    public GuestCreateReservationPage(BrowseRoomPage previousPage, Room room) {
        this.previousPage = previousPage;
        this.checkoutDropdown = new JComboBox<>();
        this.roomNumberDropdown = new JComboBox<>();
        this.cruiseDropdown = new JComboBox<>();
        this.checkinField = new JTextField();
        this.roomToReserve = room;

        createGUI();
    }

    private void createGUI() {
        frame = new JFrame("New Reservation");
        frame.setSize(1000, 600);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(7, 3, 15, 20));

        titleLabel = new JLabel("Create Reservation", JLabel.CENTER);

        usernameLabel = new JLabel("Guest username:");
        usernameField = new JTextField();
        usernameField.setEditable(false);

        JLabel cruiseLabel = new JLabel("Cruises:");
        JTextField cruiseField = new JTextField();
        cruiseField.setEditable(false);

        checkinLabel = new JLabel("Check-in Date:");
        checkinField = new JTextField();
        //a guest can only check-in the day the cruise is leaving, so it cannot be changed
        checkinField.setEditable(false);

        checkoutLabel = new JLabel("Check-out Dates:");
        checkoutField = new JTextField();

        roomNumberLabel = new JLabel("Room:");
        roomNumberField = new JTextField();
        roomNumberField.setEditable(false);

        submitButton = new JButton("Submit");
        backButton = new JButton("Back");

        //first row
        mainPanel.add(new JLabel());
        mainPanel.add(titleLabel);
        mainPanel.add(new JLabel());

        //second row
        mainPanel.add(usernameLabel);
        mainPanel.add(usernameField);
        usernameField.setText(CurrentGuest.getCurrentGuest().getUsername());
        mainPanel.add(new JLabel());

        //third row
        mainPanel.add(cruiseLabel);
        mainPanel.add(cruiseField);
        cruiseField.setText(roomToReserve.getCruise());
        mainPanel.add(new JLabel());

        //fourth row
        mainPanel.add(checkinLabel);
        mainPanel.add(checkinField);
        checkinField.setText(GuestCreateReservationPageController.getDeparture(roomToReserve.getCruise()));
        mainPanel.add(new JLabel());

        //fifth row
        mainPanel.add(checkoutLabel);
        mainPanel.add(checkoutDropdown);
        mainPanel.add(new JLabel());

        //sixth row
        mainPanel.add(roomNumberLabel);
        mainPanel.add(roomNumberField);
        roomNumberField.setText("Room " + roomToReserve.getRoomNumber());
        mainPanel.add(new JLabel());

        //seventh row
        mainPanel.add(backButton);
        mainPanel.add(new JLabel());
        mainPanel.add(submitButton);

        frame.add(mainPanel);
        frame.setVisible(true);

        backButton.addActionListener(e -> {
            frame.dispose(); // Close the Edit Reservation page
        });

        submitButton.addActionListener(e -> {
            //get the new checkout time
            String username = (String) usernameField.getText();
            checkoutDropdown.getSelectedItem();

            //create reservation
            if (GuestCreateReservationPageController.reserveRoom(CurrentGuest.getCurrentGuest(), roomToReserve, )) {
                JOptionPane.showMessageDialog(frame, "Room " + roomToReserve.getRoomNumber() + " reserved.");
            }

            //getting just the room number from the dropdown information
            int firstCommaIndex = roomNumber.indexOf(',');
            String roomNumberWithWords = (firstCommaIndex != -1) ? roomNumber.substring(0, firstCommaIndex) : roomNumber;
            String selectedRoom = roomNumberWithWords.replaceAll("[^0-9]", "");

            //creating a cruise object to get information from
            Cruise cruise = GuestCreateReservationPageContoller.getCruise(cruiseName);

            User u = null;
            try {
                u = GuestCreateReservationPageContoller.getUser(username);
            } catch (UserNotFoundException ex) {
                ex.printStackTrace();
            }

            Room r = GuestCreateReservationPageContoller.getRoom(Integer.parseInt(selectedRoom), cruiseName);
            LocalDate startDate = cruise.getDeparture();
            System.out.println(checkoutDropdown.getSelectedItem().toString());
            LocalDate endDate = LocalDate.now();
            int travelPathSize = cruise.getTravelPath().size();

            //get final country arrival
            LocalDate end = cruise.getTravelPath().get(travelPathSize-1).getArrivalTime();

            Country startCountry = cruise.getTravelPath().get(0);
            Country endCountry = cruise.getTravelPath().get(travelPathSize-1);

            if (GuestCreateReservationPageContoller.createReservation(new Reservation(-1, u, r, startDate, endDate, startCountry, endCountry))) {
                JOptionPane.showMessageDialog(mainPanel, "Reservation created.");
            }
            else {
                try {
                    throw new CouldNotCreateReservationException("Could not create reservation. See stack trace for details.");
                } catch (CouldNotCreateReservationException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void show() {
        frame.setVisible(true);
    }

    public class LineWrapRenderer extends DefaultListCellRenderer {
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            label.setText("<html><p style=\"width:300\">" + value.toString() + "</p></html>");
            return label;
        }
    }

    public boolean validateUser(String username) {
        //if username doesn't exist
        if (!GuestCreateReservationPageContoller.usernameExists(username)) {
            JOptionPane.showMessageDialog(mainPanel, "User " + username + " does not exist.");

            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        new GuestCreateReservationPageContoller(null);
    }
}