package edu.ui.travelAgentCreateReservations;

import edu.core.cruise.Cruise;
import edu.core.reservation.Reservation;
import edu.core.reservation.Room;
import edu.databaseAccessors.CruiseDatabase;
import edu.databaseAccessors.RoomDatabase;
import edu.ui.guestReservationList.MyReservationsPage;
import edu.ui.landingPage.TravelAgentLandingPage;
import edu.ui.reservationListInterface.ReservationListInterface;
import edu.ui.travelAgentEditReservations.GuestsWithReservationPage;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

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

    public TravelAgentCreateReservationPage(GuestsWithReservationPage previousPage) {
        this.previousPage = previousPage;
        createGUI();
    }

    private void createGUI() {
        frame = new JFrame("New Reservation");
        frame.setSize(1000, 600);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(6, 3, 15, 20));

        titleLabel = new JLabel("Create Reservation", JLabel.CENTER);

        JLabel usernameLabel = new JLabel("Guest username:");
        JTextField usernameField = new JTextField();

        JLabel cruiseLabel = new JLabel("Cruises:");
        checkoutField = new JTextField();
        List<Cruise> cruiseList = TravelAgentCreateReservationController.getAllCruises();
        String[] cruiseArray = cruiseList.stream().map(Object::toString).toArray(String[]::new);
        JComboBox<String> cruiseDropdown = new JComboBox<>(cruiseArray);
        cruiseDropdown.setRenderer(new LineWrapRenderer());

        JLabel checkinLabel = new JLabel("Check-in Date:");
        JTextField checkinField = new JTextField();
        //a guest can only check-in the day the cruise is leaving, so it cannot be changed
        checkinField.setEditable(false);

        checkoutLabel = new JLabel("Check-out Dates:");
        checkoutField = new JTextField();
        List<Cruise> checkoutList = TravelAgentCreateReservationController.getAllCheckoutDates();
        String[] checkoutArray = checkoutList.stream().map(Object::toString).toArray(String[]::new);
        JComboBox<String> checkoutDropdown = new JComboBox<>(checkoutArray);
        checkoutDropdown.setRenderer(new LineWrapRenderer());

        roomNumberLabel = new JLabel("Rooms:");
        roomNumberField = new JTextField();
        List<Room> roomList = TravelAgentCreateReservationController.getAllRooms(cruiseDropdown.getSelectedItem());
        String[] roomNumberArray = roomList.stream().map(Object::toString).toArray(String[]::new);
        JComboBox<String> roomNumberDropdown = new JComboBox<>(roomNumberArray);
        roomNumberDropdown.setRenderer(new LineWrapRenderer());

        submitButton = new JButton("Submit");
        backButton = new JButton("Back");

        //top row
        mainPanel.add(new JLabel());
        mainPanel.add(titleLabel);
        mainPanel.add(new JLabel());

        //second row
        mainPanel.add(usernameLabel);
        mainPanel.add(usernameField);
        mainPanel.add(new JLabel());

        //third row
        mainPanel.add(cruiseLabel);
        mainPanel.add(new JLabel());
        //mainPanel.add(cruiseDropdown);

        //fourth row
        mainPanel.add(checkinLabel);
        mainPanel.add(checkinField);
        //TODO set text to selected cruise start date
        checkoutField.setText("TODO // DATE THAT CRUISE LEAVES");
        mainPanel.add(new JLabel());

        //last row
        mainPanel.add(checkoutLabel);
        mainPanel.add(new JLabel());
        //mainPanel.add(checkoutDropdown);

        //last row
        mainPanel.add(roomNumberLabel);
        mainPanel.add(new JLabel());
        //mainPanel.add(roomNumberDropdown);

        mainPanel.add(submitButton);
        mainPanel.add(new JLabel());
        mainPanel.add(backButton);

        frame.add(mainPanel);
        frame.setVisible(true);

        backButton.addActionListener(e -> {
            frame.dispose(); // Close the Edit Reservation page
            previousPage.show(); // Go back to the previous page
        });

        /*submitButton.addActionListener(e -> {
            //get the new checkout time
            String checkout = (String) checkoutField.getText();

            //get the item selected with the dropdown
            String roomDetails = (String) roomNumberDropdown.getSelectedItem();

            //getting just the room number from the dropdown information
            int firstCommaIndex = roomDetails.indexOf(',');
            String roomNumberWithWords = (firstCommaIndex != -1) ? roomDetails.substring(0, firstCommaIndex) : roomDetails;
            String selectedRoom = roomNumberWithWords.replaceAll("[^0-9]", "");

            String room;


            //if checkout is not valid, start over
            if (!validateDate(checkout)) {
                frame.dispose();
                createGUI();
                return;
            }


            //if no changes were made, remind the user
            if (Integer.parseInt(room) == reservation.getRoom().getRoomNumber() &&
                    LocalDate.parse(checkout).equals(reservation.getEndDate())) {

                noChangesDecision();
            }
            //if there is a non-duplicate value, update the reservation
            else {
                //if the user confirms their decision, update and go back to reservation page
                if (validateDecision(checkout, room)) {
                    updateReservation(checkout, room);
                    frame.dispose();
                    previousPage.show(); // Go back to the reservation page
                }
                //restart the edit frame
                else {
                    frame.dispose();
                    createGUI();
                }
            }
        });*/
    }

    //TODO
    private void createReservation(String checkout, String room) {
        //TravelAgentCreateReservationController.createReservation(reservation, checkout, room);
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

   /*public boolean validateDecision(String checkout, String room) {
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
    }*/

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

    public boolean noChangesDecision() {
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

    public static void main(String[] args) {
        new TravelAgentCreateReservationPage(null);
    }
}