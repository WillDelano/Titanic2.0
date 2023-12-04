package edu.ui.travelAgentCreateReservations;

import edu.core.cruise.Country;
import edu.core.cruise.Cruise;
import edu.core.reservation.Reservation;
import edu.core.reservation.Room;
import edu.core.users.User;
import edu.databaseAccessors.CruiseDatabase;

import edu.exceptions.CouldNotCreateReservationException;
import edu.exceptions.UserNotFoundException;

import edu.ui.travelAgentEditReservations.GuestsWithReservationPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import java.util.List;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TravelAgentCreateReservationPage {
    private JFrame frame;
    private JLabel titleLabel;
    private JPanel mainPanel;
    private JLabel checkoutLabel;
    private JLabel roomNumberLabel;
    private JTextField checkoutField;
    private JTextField roomNumberField;
    private JButton submitButton;
    private JButton backButton;
    private GuestsWithReservationPage previousPage;
    JComboBox<String> checkoutDropdown;
    JComboBox<String> roomNumberDropdown;
    JComboBox<String> cruiseDropdown;
    JTextField checkinField;
    JLabel checkinLabel;
    JLabel usernameLabel;
    JTextField usernameField;

    boolean test = false;

    public TravelAgentCreateReservationPage(GuestsWithReservationPage previousPage) {
        this.previousPage = previousPage;
        this.checkoutDropdown = new JComboBox<>();
        this.roomNumberDropdown = new JComboBox<>();
        this.cruiseDropdown = new JComboBox<>();
        this.checkinField = new JTextField();
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

        JLabel cruiseLabel = new JLabel("Cruises:");
        checkoutField = new JTextField();
        List<String> cruiseList = TravelAgentCreateReservationController.getAllCruises();
        String[] cruiseArray = cruiseList.stream().map(Object::toString).toArray(String[]::new);
        cruiseDropdown = new JComboBox<>(cruiseArray);
        cruiseDropdown.setRenderer(new LineWrapRenderer());

        checkinLabel = new JLabel("Check-in Date:");
        checkinField = new JTextField();
        //a guest can only check-in the day the cruise is leaving, so it cannot be changed
        checkinField.setEditable(false);

        checkoutLabel = new JLabel("Check-out Dates:");


        roomNumberLabel = new JLabel("Rooms:");
        roomNumberField = new JTextField();

        submitButton = new JButton("Submit");
        backButton = new JButton("Back");

        //first row
        mainPanel.add(new JLabel());
        mainPanel.add(titleLabel);
        mainPanel.add(new JLabel());

        //second row
        mainPanel.add(usernameLabel);
        mainPanel.add(usernameField);
        mainPanel.add(new JLabel());

        //third row
        mainPanel.add(cruiseLabel);
        mainPanel.add(cruiseDropdown);
        mainPanel.add(new JLabel());

        //fourth row
        mainPanel.add(checkinLabel);
        mainPanel.add(checkinField);
        mainPanel.add(new JLabel());

        //fifth row
        mainPanel.add(checkoutLabel);

        mainPanel.add(checkoutDropdown);
        mainPanel.add(new JLabel());

        //sixth row
        mainPanel.add(roomNumberLabel);
        mainPanel.add(roomNumberDropdown);
        mainPanel.add(new JLabel());

        //seventh row
        mainPanel.add(backButton);
        mainPanel.add(new JLabel());
        mainPanel.add(submitButton);

        //set values of checkout dates and rooms to initially selected cruise
        refreshData((String) cruiseDropdown.getSelectedItem());

        frame.add(mainPanel);
        frame.setVisible(true);

        cruiseDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshData((String) cruiseDropdown.getSelectedItem());
            }
        });


        backButton.addActionListener(e -> {
            frame.dispose(); // Close the Edit Reservation page
            previousPage.show(); // Go back to the previous page
        });

        submitButton.addActionListener(e -> {
            //get the new checkout time
            String username = (String) usernameField.getText();

            //get the cruise selected with the dropdown
            String cruiseName = (String) cruiseDropdown.getSelectedItem();

            //get the room toString from the dropdown
            String roomNumber = (String) roomNumberDropdown.getSelectedItem();

            //if username is not valid, start over
            if (!validateUser(username)) {
                frame.dispose();
                createGUI();
                return;
            }

            //getting just the room number from the dropdown information
            int firstCommaIndex = roomNumber.indexOf(',');
            String roomNumberWithWords = (firstCommaIndex != -1) ? roomNumber.substring(0, firstCommaIndex) : roomNumber;
            String selectedRoom = roomNumberWithWords.replaceAll("[^0-9]", "");

            //creating a cruise object to get information from
            Cruise cruise = TravelAgentCreateReservationController.getCruise(cruiseName);

            User u = null;
            try {
                u = TravelAgentCreateReservationController.getUser(username);
            } catch (UserNotFoundException ex) {
                ex.printStackTrace();
            }

            Room r = TravelAgentCreateReservationController.getRoom(Integer.parseInt(selectedRoom), cruiseName);
            LocalDate startDate = cruise.getDeparture();


            String stringEndDate = checkoutDropdown.getSelectedItem().toString();
            // Define the date format of the input string
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            // Parse the date from the input string
            LocalDate endDate = LocalDate.parse(stringEndDate.substring(0, 10), formatter);

            int travelPathSize = cruise.getTravelPath().size();

            //get final country arrival
            LocalDate end = cruise.getTravelPath().get(travelPathSize-1).getArrivalTime();

            Country startCountry = cruise.getTravelPath().get(0);
            Country endCountry = cruise.getTravelPath().get(travelPathSize-1);


            if (TravelAgentCreateReservationController.createReservation(new Reservation(-1, u, r, startDate, endDate, startCountry, endCountry))) {
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

    private void refreshData(String cruiseName) {
        Cruise cruise = CruiseDatabase.getCruise(cruiseName);

        checkinField.setText(cruise.getDeparture().toString());


        List<LocalDate> checkoutList = TravelAgentCreateReservationController.getAllCheckoutDates(cruise);
        String[] checkoutArray = checkoutList.stream().map(Object::toString).toArray(String[]::new);

        //modify each string individually
        for (int i = 0; i < checkoutArray.length; i++) {
            //adding country name
            checkoutArray[i] += " - " + cruise.getTravelPath().get(i).getName();
        }


        //update the model of the existing checkoutDropdown
        checkoutDropdown.setModel(new DefaultComboBoxModel<>(checkoutArray));
        checkoutDropdown.setRenderer(new LineWrapRenderer());

        List<Room> roomList = TravelAgentCreateReservationController.getAllRooms(cruise);
        String[] roomNumberArray = roomList.stream().map(Object::toString).toArray(String[]::new);

        //update the model of the existing roomNumberDropdown
        roomNumberDropdown.setModel(new DefaultComboBoxModel<>(roomNumberArray));
        roomNumberDropdown.setRenderer(new LineWrapRenderer());
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
        if (!TravelAgentCreateReservationController.usernameExists(username)) {
            JOptionPane.showMessageDialog(mainPanel, "User " + username + " does not exist.");

            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        new TravelAgentCreateReservationPage(null);
    }
}