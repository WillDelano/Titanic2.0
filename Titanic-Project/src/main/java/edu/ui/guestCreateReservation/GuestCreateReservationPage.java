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
import edu.ui.travelAgentCreateReservations.TravelAgentCreateReservationController;
import edu.ui.travelAgentCreateReservations.TravelAgentCreateReservationPage;
import edu.ui.travelAgentEditReservations.GuestsWithReservationPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * UI for creating a new reservation for a guest.
 * Allows guests to create reservations by selecting a room, check-in and check-out dates.
 * Displays relevant information and provides a user-friendly interface.
 *
 * @author [Your Name]
 * @version 1.0
 * @see GuestCreateReservationPageController
 * @see BrowseRoomPage
 * @see CurrentGuest
 * @see Room
 * @see Reservation
 */
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

    /**
     * Constructs a new GuestCreateReservationPage.
     *
     * @param previousPage The previous page (BrowseRoomPage) to navigate back.
     * @param room The room for which the reservation is being created.
     */
    public GuestCreateReservationPage(BrowseRoomPage previousPage, Room room) {
        this.previousPage = previousPage;
        this.checkoutDropdown = new JComboBox<>();
        this.roomNumberDropdown = new JComboBox<>();
        this.cruiseDropdown = new JComboBox<>();
        this.checkinField = new JTextField();
        this.roomToReserve = room;

        createGUI();
    }

    /**
     * Creates and initializes the graphical user interface for creating a reservation.
     */
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

        Cruise cruise = GuestCreateReservationPageController.getCruise(roomToReserve.getCruise());
        List<LocalDate> checkoutList = GuestCreateReservationPageController.getAllCheckoutDates(roomToReserve);
        String[] checkoutArray = checkoutList.stream().map(Object::toString).toArray(String[]::new);

        //modify each string individually
        for (int i = 0; i < checkoutArray.length; i++) {
            //adding country name
            checkoutArray[i] += " - " + cruise.getTravelPath().get(i).getName();
        }

        //update the model of the existing checkoutDropdown
        checkoutDropdown.setModel(new DefaultComboBoxModel<>(checkoutArray));
        checkoutDropdown.setRenderer(new GuestCreateReservationPage.LineWrapRenderer());

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
            previousPage.show();
        });

        submitButton.addActionListener(e -> {
            //get the new checkout time
            String stringEndDate = checkoutDropdown.getSelectedItem().toString();
            // Define the date format of the input string
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            // Parse the date from the input string
            LocalDate endDate = LocalDate.parse(stringEndDate.substring(0, 10), formatter);

            //create reservation
            if (GuestCreateReservationPageController.reserveRoom(CurrentGuest.getCurrentGuest(), roomToReserve, endDate)) {
                JOptionPane.showMessageDialog(frame, "Room " + roomToReserve.getRoomNumber() + " reserved.");
            }
        });
    }

    /**
     * Displays the GuestCreateReservationPage.
     */
    public void show() {
        frame.setVisible(true);
    }

    /**
     * Custom list cell renderer for displaying wrapped text in a JComboBox.
     */
    public class LineWrapRenderer extends DefaultListCellRenderer {
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            label.setText("<html><p style=\"width:300\">" + value.toString() + "</p></html>");
            return label;
        }
    }
}