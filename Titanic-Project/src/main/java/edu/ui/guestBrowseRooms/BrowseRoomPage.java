package edu.ui.guestBrowseRooms;

import edu.core.reservation.Room;
import edu.core.users.CurrentGuest;
import edu.databaseAccessors.RoomDatabase;
<<<<<<< HEAD
=======
import edu.ui.guestCreateReservation.GuestCreateReservationPage;
import edu.ui.guestSelectCruise.SelectCruisePage;
>>>>>>> 0d351394b1b58d11507c22ab0d15eb848501b3be

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
<<<<<<< HEAD

    public BrowseRoomPage(String selectedCruise) {
=======
    private SelectCruisePage prevPage;

    public BrowseRoomPage(SelectCruisePage prevPage, String selectedCruise) {
        this.prevPage = prevPage;
>>>>>>> 0d351394b1b58d11507c22ab0d15eb848501b3be
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
<<<<<<< HEAD
        backButton.addActionListener(e -> roomFrame.dispose());
=======
        backButton.addActionListener(e -> {
            roomFrame.dispose();
            prevPage.show();
        });
>>>>>>> 0d351394b1b58d11507c22ab0d15eb848501b3be

        selectRoomButton = new JButton("Select Room");
        selectRoomButton.addActionListener(e -> {
            Room selectedRoom = roomList.getSelectedValue();
            if (selectedRoom != null) {
<<<<<<< HEAD
                controller.reserveRoom(CurrentGuest.getCurrentGuest(), selectedRoom);
                JOptionPane.showMessageDialog(roomFrame, "Room " + selectedRoom.getRoomNumber() + " reserved.");
=======
                new GuestCreateReservationPage(this, selectedRoom);
>>>>>>> 0d351394b1b58d11507c22ab0d15eb848501b3be
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