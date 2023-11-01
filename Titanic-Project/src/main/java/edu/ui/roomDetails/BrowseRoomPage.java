package edu.ui.roomDetails;

import edu.core.cruise.Cruise;
import edu.core.reservation.Reservation;
import edu.core.reservation.Room;
import edu.core.users.CurrentGuest;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
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
    private JLabel titleLabel, bedCount, sortBy;
    private JPanel northPanel, filterPanel;
    private JMenuBar searchMenu;
    private JList<Room> roomList;
    private JTextField searchTextField;
    private JButton backButton, selectRoomButton, optionsButton, searchButton;
    private JButton applyButton;
    private JCheckBox smokingBox, nonSmokingBox;
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
        JPanel titlePanel = new JPanel();
        titlePanel.add(titleLabel);

        northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));

        northPanel.add(titlePanel, BorderLayout.NORTH);
        //roomFrame.add(titleLabel, BorderLayout.NORTH);

        generateSearchMenu();
        generateFilterPanel();

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

    private void generateSearchMenu(){
        searchMenu = new JMenuBar();
        searchMenu.setPreferredSize(new Dimension(1000, 30));
        searchTextField = new JTextField();
        searchButton = new JButton("search");
        optionsButton = new JButton("options");

        optionsButton.addActionListener(e -> {
            filterPanelVisiblity();
        });

        searchMenu.add(searchTextField);
        searchMenu.add(searchButton);
        searchMenu.add(optionsButton);
    }
    private void generateFilterPanel(){
        filterPanel = new JPanel();
        smokingBox = new JCheckBox("Smoking");
        smokingBox.setSelected(true);
        nonSmokingBox = new JCheckBox("Non-Smoking  ");
        nonSmokingBox.setSelected(true);
        applyButton = new JButton("apply");

        bedCount = new JLabel("number of beds  ");
        sortBy = new JLabel("sortBy  ");

        JComboBox<String> bedCountOption, sortTypeOption;
        String bedCounts[] = { "All","1", "2", "3", "4"};
        bedCountOption = new JComboBox<>(bedCounts);

        String sortTypes[] = { "None","Price: Ascending", "Price: Descending"};
        sortTypeOption = new JComboBox<>(sortTypes);

        applyButton.addActionListener( e->{
            applyFilters();
        });

        filterPanel.add(smokingBox, BorderLayout.SOUTH);
        filterPanel.add(nonSmokingBox);
        filterPanel.add(bedCountOption);
        filterPanel.add(bedCount);
        filterPanel.add(sortTypeOption);
        filterPanel.add(sortBy);

        filterPanel.add(applyButton);
    }

    private void filterPanelVisiblity(){
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
    }

    private void applyFilters(){

    }

}
