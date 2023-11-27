package edu.ui.roomDetails;

import edu.core.reservation.Room;
import edu.core.users.CurrentGuest;
import edu.databaseAccessors.RoomDatabase;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * UI for displaying all rooms on a cruise
 *
 * This class displays all the rooms of a selected cruise to a user
 *
 * @author Vincent Dinh
 * @version 1.0
 * @see RoomDatabase , Room, BrowseRoomController
 */
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

        List<Room> sampleRooms = BrowseRoomController.getRooms(selectedCruise);
        roomList = new JList<>(sampleRooms.toArray(new Room[0]));
        JScrollPane listScrollPane = new JScrollPane(roomList);
        roomFrame.add(listScrollPane, BorderLayout.CENTER);

        backButton = new JButton("Back to Cruise Details");
        backButton.addActionListener(e -> roomFrame.dispose());

        selectRoomButton = new JButton("Select Room");
        selectRoomButton.addActionListener(e -> {
            Room selectedRoom = roomList.getSelectedValue();
            if (selectedRoom != null) {
                controller.reserveRoom(CurrentGuest.getCurrentGuest(), selectedRoom);
                JOptionPane.showMessageDialog(roomFrame, "Room " + selectedRoom.getRoomNumber() + " reserved.");
            } else {
                JOptionPane.showMessageDialog(roomFrame, "Please select a room first.");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(selectRoomButton);

        roomFrame.add(buttonPanel, BorderLayout.SOUTH);
        roomFrame.setVisible(true);
    }
}