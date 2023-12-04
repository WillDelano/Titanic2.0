package edu.ui.travelAgentEditReservations;

import edu.core.cruise.Cruise;
import edu.core.reservation.Reservation;
import edu.core.reservation.Room;
import edu.databaseAccessors.RoomDatabase;
import edu.ui.guestCreateReservation.GuestCreateReservationPage;
import edu.ui.guestCreateReservation.GuestCreateReservationPageController;
import edu.ui.reservationListInterface.ReservationListInterface;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditReservationPage {
    private Reservation reservation;
    private String cruise;
    private JFrame frame;
    private JLabel titleLabel;
    private JPanel mainPanel;
    private JLabel checkoutLabel;
    private JRadioButton smokingYes;
    private JRadioButton smokingNo;
    private JLabel bedTypeLabel;
    private JLabel bedNumberLabel;
    private JComboBox<String> bedTypeBox;
    private JComboBox<String> bedNumberBox;
    private JLabel roomNumberLabel;
    private JComboBox<String> cruiseComboBox;
    private JTextField checkoutField;
    private JTextField roomNumberField;
    private JButton submitButton;
    private JButton backButton;
    JComboBox<String> checkoutDropdown;
    private ReservationListInterface previousPage;

    public EditReservationPage(ReservationListInterface previousPage, String cruise, Reservation reservation) {
        this.reservation = reservation;
        this.cruise = cruise;
        this.previousPage = previousPage;
        this.checkoutDropdown = new JComboBox<>();
        createGUI();
    }

    private void createGUI() {
        frame = new JFrame("Edit Reservation");
        frame.setSize(1000, 600);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(6, 3, 15, 20));

        titleLabel = new JLabel("Edit Reservation", JLabel.CENTER);

        checkoutLabel = new JLabel("Check-out Date:");

        Cruise cruise = EditReservationController.getCruise(reservation.getRoom().getCruise());
        List<LocalDate> checkoutList = EditReservationController.getAllCheckoutDates(reservation);
        String[] checkoutArray = checkoutList.stream().map(Object::toString).toArray(String[]::new);

        //modify each string individually
        for (int i = 0; i < checkoutArray.length; i++) {
            //adding country name
            checkoutArray[i] += " - " + cruise.getTravelPath().get(i).getName();
        }

        //update the model of the existing checkoutDropdown
        checkoutDropdown.setModel(new DefaultComboBoxModel<>(checkoutArray));
        checkoutDropdown.setRenderer(new EditReservationPage.LineWrapRenderer());

        roomNumberLabel = new JLabel("Room Number (Enter or pick from the dropdown list):");
        roomNumberField = new JTextField();
        List<Room> roomList = EditReservationController.getAllRooms(cruise.getName());
        String[] roomNumberArray = roomList.stream().map(Object::toString).toArray(String[]::new);
        JComboBox<String> roomNumberDropdown = new JComboBox<>(roomNumberArray);

        roomNumberDropdown.setRenderer(new LineWrapRenderer());

        JButton cancelButton = new JButton("Cancel Reservation");

        submitButton = new JButton("Submit");
        backButton = new JButton("Back");

        //top row
        mainPanel.add(new JLabel());
        mainPanel.add(titleLabel);
        mainPanel.add(new JLabel());

        //second row
        mainPanel.add(checkoutLabel);
        mainPanel.add(checkoutDropdown);
        mainPanel.add(new JLabel());

        for (int i = 0; i < checkoutArray.length; i++) {
            //set the selected to the matching end reservation date
            if (checkoutArray[i].substring(0, 10).equals(String.valueOf(reservation.getEndDate()))) {
                checkoutDropdown.setSelectedItem(checkoutArray[i]);
            }
        }

        //third row
        mainPanel.add(roomNumberLabel);
        mainPanel.add(roomNumberField);
        roomNumberField.setText(Integer.toString(reservation.getRoom().getRoomNumber()));
        mainPanel.add(roomNumberDropdown);

        List<String> roomNumbers = new ArrayList<>();

        //take just the room number from every reservation string and add it to list
        for (String s : roomNumberArray) {
            // Define the pattern for room number
            Pattern pattern = Pattern.compile("Room Number: (\\d+)");
            // Create a matcher with the input string
            Matcher matcher = pattern.matcher(s);
            // Check if the pattern is found
            if (matcher.find()) {
                // Extract and print the room number
                roomNumbers.add(matcher.group(1));
            }
        }

        //match the string of the room number selected with the reservation's room number
        for (int i = 0; i < roomNumbers.size(); i++) {
            if (String.valueOf(reservation.getRoom().getRoomNumber()).equals(roomNumbers.get(i))) {
                roomNumberDropdown.setSelectedItem(roomNumberArray[i]);
            }
        }

        //fourth row
        mainPanel.add(new JLabel());
        mainPanel.add(cancelButton);
        mainPanel.add(new JLabel());

        //last row
        mainPanel.add(new JLabel());
        mainPanel.add(submitButton);
        mainPanel.add(new JLabel());

        mainPanel.add(new JLabel());
        mainPanel.add(backButton);
        mainPanel.add(new JLabel());

        frame.add(mainPanel);
        frame.setVisible(true);

        backButton.addActionListener(e -> {
            frame.dispose(); // Close the Edit Reservation page
            try {
                previousPage.show(); // Go back to the previous page
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        cancelButton.addActionListener(e -> {
            if (cancelReservation()) {
                frame.dispose(); // Close the Edit Reservation page
                try {
                    previousPage.show(); // Go back to the previous page
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        submitButton.addActionListener(e -> {
            //get the new checkout time
            String checkout = (String) checkoutDropdown.getSelectedItem();
            //remove the country in the string
            checkout = checkout.substring(0, 10);

            //get the item selected with the dropdown
            String roomDetails = (String) roomNumberDropdown.getSelectedItem();

            //getting just the room number from the dropdown information
            int firstCommaIndex = roomDetails.indexOf(',');
            String roomNumberWithWords = (firstCommaIndex != -1) ? roomDetails.substring(0, firstCommaIndex) : roomDetails;
            String selectedRoom = roomNumberWithWords.replaceAll("[^0-9]", "");
            String room;

            //if selected room is still the current reservation's room, the dropdown was not used
            if (Integer.parseInt(selectedRoom) == reservation.getRoom().getRoomNumber()) {
                room = roomNumberField.getText();
            }
            //dropdown was used
            else {
                room = selectedRoom;
            }

            //if checkout is not valid, start over
            if (!validateDate(checkout)) {
                frame.dispose();
                createGUI();
                return;
            }

            //if room does not exist, start over
            if (!validateRoomNumber(Integer.parseInt(room))) {
                frame.dispose();
                createGUI();
                return;
            }

            //if no changes were made, remind the user
            if (Integer.parseInt(room) == reservation.getRoom().getRoomNumber() &&
                    LocalDate.parse(checkout).equals(reservation.getEndDate())) {

                try {
                    noChangesDecision();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            //if there is a non-duplicate value, update the reservation
            else {
                //if the user confirms their decision, update and go back to reservation page
                if (validateDecision(checkout, room)) {
                    updateReservation(checkout, room);
                    frame.dispose();
                    try {
                        previousPage.show(); // Go back to the reservation page
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                //restart the edit frame
                else {
                    frame.dispose();
                    createGUI();
                }
            }
        });
    }

    private void updateReservation(String checkout, String room) {
        EditReservationController.updateReservation(reservation, checkout, room);
    }

    public boolean cancelReservation() {
        int dialogResult = JOptionPane.showConfirmDialog(mainPanel, "Are you sure you want to cancel the reservation?",
                "This action cannot be undone!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (dialogResult == JOptionPane.YES_OPTION) {
            EditReservationController.deleteReservation(reservation);
            return true;
        }
        return false;
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

    public boolean validateDecision(String checkout, String room) {
        UIManager.put("OptionPane.yesButtonText", "Confirm");
        UIManager.put("OptionPane.noButtonText", "Cancel");

        //if both values to be changed are different
        if (Integer.parseInt(room) != reservation.getRoom().getRoomNumber() &&
                !(LocalDate.parse(checkout).equals(reservation.getEndDate()))) {
            int dialogResult = JOptionPane.showConfirmDialog(mainPanel, "You are changing the check-out date to: "
                    + checkout + " and the room to " + room, "Review Changes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            return dialogResult == JOptionPane.YES_OPTION;
        }
        //if only the room number was changed
        else if (Integer.parseInt(room) != reservation.getRoom().getRoomNumber()) {
            int dialogResult = JOptionPane.showConfirmDialog(mainPanel, "You are changing the room to: Room "
                    + room,"Review Changes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            return dialogResult == JOptionPane.YES_OPTION;
        }
        //only the date was changed
        else {
            int dialogResult = JOptionPane.showConfirmDialog(mainPanel, "You are changing the check-out date to: "
                    + checkout, "Review Changes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            return dialogResult == JOptionPane.YES_OPTION;
        }
    }

    public boolean validateRoomNumber(int room) {
        //if the room exists in the database
        if (RoomDatabase.getRoom(room).getRoomNumber() != -1) {
            //if the room is not booked
            if (!RoomDatabase.getRoom(room).isBooked()) {
                return true;
            }
            //the room exists but is booked
            else {
                JOptionPane.showMessageDialog(frame, "That room is already booked.", "Sorry!", JOptionPane.WARNING_MESSAGE);
            }
        }
        //the room is not in the database
        else {
            JOptionPane.showMessageDialog(frame, "That room does not exist.", "Sorry!", JOptionPane.WARNING_MESSAGE);
        }
        return false;
    }

    public boolean validateDate(String checkout) {
        try {
            LocalDate date = LocalDate.parse(checkout);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(frame, "Not a valid date. Use format: YYYY-MM-DD", "Invalid Date", JOptionPane.WARNING_MESSAGE);
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean noChangesDecision() throws SQLException {
        UIManager.put("OptionPane.yesButtonText", "Yes, quit");
        UIManager.put("OptionPane.noButtonText", "No, continue");

        int dialogResult = JOptionPane.showConfirmDialog(mainPanel, "No changes have been made. Would you like to quit?",
                "Reservation is unchanged", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (dialogResult == JOptionPane.YES_OPTION) {
            frame.dispose(); //close this frame
            previousPage.show(); // Go back to the landingPage
            return true;
        }
        else {
            frame.dispose(); //close this instance of frame
            createGUI(); //restart the frame
            return false;
        }
    }
}