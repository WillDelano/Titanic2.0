package edu.ui.roomDetails;

import edu.core.cruise.Cruise;
import edu.core.reservation.Reservation;
import edu.core.reservation.Room;
import edu.core.users.CurrentGuest;

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
 * @see edu.database.RoomDatabase, Room, BrowseRoomController
 */
public class BrowseRoomPage {

    private JFrame roomFrame;
    private JLabel titleLabel;
    private JPanel northPanel;
    private JList<Room> roomList;
    private JButton backButton;
    private JButton selectRoomButton;
    private boolean optionVisible = false;//, smokingRooms = true, nonSmokingRooms = true;

    public BrowseRoomPage(String selectedCruise) {
        prepareGUI(selectedCruise);
    }

    private void prepareGUI(String selectedCruise) {
        BrowseRoomController controller = new BrowseRoomController();
        roomFrame = new JFrame("Rooms for Cruise: " + selectedCruise);
        roomFrame.setSize(1000, 700);
        roomFrame.setLayout(new BorderLayout());

        titleLabel = new JLabel("Available Rooms for " + selectedCruise, JLabel.CENTER);

        northPanel = new JPanel();

        northPanel.add(titleLabel, BorderLayout.NORTH);
        //roomFrame.add(titleLabel, BorderLayout.NORTH);

        //Search menu components
        JMenuBar searchMenu= new JMenuBar();
        searchMenu.setPreferredSize(new Dimension(1000, 30));
        JTextField searchTextField = new JTextField();
        JButton searchButton = new JButton("search");
        JButton optionsButton = new JButton("options");


        JPanel filterPanel = new JPanel();
        JCheckBox smokingBox = new JCheckBox("Smoking");
        smokingBox.setSelected(true);
        JCheckBox nonSmokingBox = new JCheckBox("Non-Smoking");
        nonSmokingBox.setSelected(true);
        JButton applyButton = new JButton("apply");


        filterPanel.add(smokingBox);
        filterPanel.add(nonSmokingBox);
        filterPanel.add(applyButton);


        optionsButton.addActionListener(e -> {
            if(!optionVisible) {
                northPanel.add(filterPanel, BorderLayout.CENTER);
                optionVisible = true;
                //roomFrame.add(filterPanel, BorderLayout.NORTH);
                roomFrame.revalidate();
            } else {
                optionVisible = false;
                //roomFrame.remove(filterPanel);
                northPanel.remove(filterPanel);
                roomFrame.revalidate();
            }
        });

        searchMenu.add(searchTextField);
        searchMenu.add(searchButton);
        searchMenu.add(optionsButton);

        List<Room> sampleRooms = BrowseRoomController.getRooms(selectedCruise);
        roomList = new JList<>(sampleRooms.toArray(new Room[0]));

        JScrollPane listScrollPane = new JScrollPane(roomList);
        roomFrame.add(listScrollPane, BorderLayout.CENTER);

        backButton = new JButton("Back to Cruise Details");
        backButton.addActionListener(e -> {
            roomFrame.dispose(); // close the RoomPage
        });

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
                }
            } else {
                JOptionPane.showMessageDialog(roomFrame, "Please select a room first.");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(selectRoomButton);

        //roomFrame.add(searchPanel, BorderLayout.NORTH);
        roomFrame.add(northPanel, BorderLayout.NORTH);
        roomFrame.setJMenuBar(searchMenu);
        roomFrame.add(buttonPanel, BorderLayout.SOUTH);
        roomFrame.setVisible(true);
    }
}
