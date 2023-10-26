package edu.ui.roomDetails;

import edu.core.reservation.Reservation;
import edu.core.reservation.Room;
import edu.core.users.CurrentGuest;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BrowseRoomPage {

    private JFrame roomFrame;
    private JLabel titleLabel;
    private JList<Room> roomList;
    private JButton backButton;
    private JButton selectRoomButton;

    public BrowseRoomPage(String selectedCruise) {
        prepareGUI(selectedCruise);
    }

    private void prepareGUI(String selectedCruise) {
        BrowseRoomController controller = new BrowseRoomController();
        roomFrame = new JFrame("Rooms for Cruise: " + selectedCruise);
        roomFrame.setSize(1000, 700);
        roomFrame.setLayout(new BorderLayout());

        titleLabel = new JLabel("Available Rooms for " + selectedCruise, JLabel.CENTER);
        roomFrame.add(titleLabel, BorderLayout.NORTH);

        //TODO: Add parameter to get rooms of a specific cruise
        List<Room> sampleRooms = BrowseRoomController.getRooms();
        roomList = new JList<>(sampleRooms.toArray(new Room[0]));

        JScrollPane listScrollPane = new JScrollPane(roomList);
        roomFrame.add(listScrollPane, BorderLayout.CENTER);

        backButton = new JButton("Back to Cruise Details");
        backButton.addActionListener(e -> {
            roomFrame.dispose(); // close the RoomPage
        });

        // Add a "Select Room" button
        selectRoomButton = new JButton("Select Room");
        selectRoomButton.addActionListener(e -> {
            Room selectedRoom = roomList.getSelectedValue();
            if (selectedRoom != null) {
                int dialogResult = JOptionPane.showConfirmDialog(
                        roomFrame,
                        "Reserve Room " + selectedRoom.getRoomNumber() + "?",
                        "Confirmation",
                        JOptionPane.OK_CANCEL_OPTION
                );
                if (dialogResult == JOptionPane.OK_OPTION) {
                    // Perform room reservation logic here
                    JOptionPane.showMessageDialog(roomFrame, "Room " + selectedRoom.getRoomNumber() + " reserved.");
                    controller.reserveRoom(CurrentGuest.getCurrentGuest(), selectedRoom);

                    // Notify the MyReservationsPage to refresh its data
                    new edu.ui.reservationDetails.MyReservationsPage().refreshReservations();  // Make sure refreshReservations() is public
                }
            } else {
                JOptionPane.showMessageDialog(roomFrame, "Please select a room first.");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(selectRoomButton); // Add the "Select Room" button

        roomFrame.add(buttonPanel, BorderLayout.SOUTH);
        roomFrame.setVisible(true);
    }
}
