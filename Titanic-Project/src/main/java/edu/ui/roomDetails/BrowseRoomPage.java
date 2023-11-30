package edu.ui.roomDetails;

import edu.core.reservation.Room;
import edu.core.reservation.roomSearch;
import edu.core.users.CurrentGuest;
import edu.databaseAccessors.RoomDatabase;
import edu.ui.authentication.LoginPage;

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
 * @see RoomDatabase , Room, BrowseRoomController
 */
public class BrowseRoomPage {

    private roomSearch cruiseSearch;
    private JFrame roomFrame;
    JScrollPane listScrollPane;
    private JLabel titleLabel, bedCount, sortBy;
    private JPanel northPanel, filterPanel;
    private JMenuBar searchMenu;
    private JList<Room> roomList;
    private JTextField searchTextField;
    private JButton backButton, selectRoomButton, optionsButton, searchButton;
    private JButton applyButton;
    private JCheckBox smokingBox, nonSmokingBox;
    JComboBox<String> bedCountOption, sortTypeOption;
    List<Room> currentRooms;
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

        generateSearchMenu();
        generateFilterPanel();

        List<Room> sampleRooms = BrowseRoomController.getRooms(selectedCruise);
        //currentRooms = sampleRooms;
        cruiseSearch = new roomSearch(sampleRooms);
        roomList = new JList<>(sampleRooms.toArray(new Room[0]));
        currentRooms = new ArrayList<>(sampleRooms);

        listScrollPane = new JScrollPane(roomList);

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

        //roomFrame.add(searchPanel, BorderLayout.NORTH);
        roomFrame.add(northPanel, BorderLayout.NORTH);
        roomFrame.setJMenuBar(searchMenu);
        roomFrame.add(buttonPanel, BorderLayout.SOUTH);
        roomFrame.setVisible(true);
    }
    /**
     * Generates panel for search input
     *
     */
    private void generateSearchMenu(){
        searchMenu = new JMenuBar();
        searchMenu.setPreferredSize(new Dimension(1000, 30));
        searchTextField = new JTextField();
        searchButton = new JButton("search");
        optionsButton = new JButton("options");

        optionsButton.addActionListener(e -> {
            filterPanelVisibility();
        });
        searchButton.addActionListener(e -> {
            setFilters();
            List <Room> list = cruiseSearch.findRooms(searchTextField.getText());
            currentRooms = new ArrayList<>(list);
            roomFrame.remove(listScrollPane);

            roomList = new JList<>(list.toArray(new Room[0]));
            listScrollPane = new JScrollPane(roomList);
            listScrollPane.getViewport().revalidate();
            listScrollPane.getViewport().repaint();
            roomFrame.add(listScrollPane, BorderLayout.CENTER);

            roomFrame.revalidate();
            roomFrame.repaint();
        });

        searchMenu.add(searchTextField);
        searchMenu.add(searchButton);
        searchMenu.add(optionsButton);
    }

    /**
     * Generates panel for filter input options
     *
     */
    private void generateFilterPanel(){
        filterPanel = new JPanel();
        smokingBox = new JCheckBox("smoking");
        smokingBox.setSelected(true);
        nonSmokingBox = new JCheckBox("non-Smoking  ");
        nonSmokingBox.setSelected(true);
        applyButton = new JButton("apply");

        bedCount = new JLabel("number of beds  ");
        sortBy = new JLabel("sort by:  ");

        String bedCounts[] = { "All","1", "2", "3", "4"};
        bedCountOption = new JComboBox<>(bedCounts);

        String sortTypes[] = { "None","Price: Ascending", "Price: Descending"};
        sortTypeOption = new JComboBox<>(sortTypes);

        applyButton.addActionListener( e->{
            setFilters();
            roomFrame.remove(listScrollPane);

            List<Room> list = new ArrayList<>(currentRooms);
            list = cruiseSearch.sortAndFilterRooms(list);
            roomList = new JList<>(list.toArray(new Room[0]));

            listScrollPane = new JScrollPane(roomList);
            listScrollPane.getViewport().revalidate();
            listScrollPane.getViewport().repaint();
            roomFrame.add(listScrollPane, BorderLayout.CENTER);

            roomFrame.revalidate();
            roomFrame.repaint();
        });

        filterPanel.add(smokingBox, BorderLayout.SOUTH);
        filterPanel.add(nonSmokingBox);
        filterPanel.add(bedCountOption);
        filterPanel.add(bedCount);
        filterPanel.add(sortTypeOption);
        filterPanel.add(sortBy);

        filterPanel.add(applyButton);
    }

    /**
     * Allows panel for filters option to be open and closed
     *
     */
    private void filterPanelVisibility(){
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


    /**
     * Sets enabled filter options for roomSearch
     *
     */
    private void setFilters(){

        //smoking options
        if(smokingBox.isSelected() && nonSmokingBox.isSelected()){
            cruiseSearch.setSmokingType(roomSearch.smokingSortType.ALL);
        } else if (smokingBox.isSelected() && !nonSmokingBox.isSelected()){
            cruiseSearch.setSmokingType(roomSearch.smokingSortType.SMOKING);
        } else if (!smokingBox.isSelected() && nonSmokingBox.isSelected()){
            cruiseSearch.setSmokingType(roomSearch.smokingSortType.NON_SMOKING);
        } else {
            cruiseSearch.setSmokingType(roomSearch.smokingSortType.ALL);
        }

        //bed count options
        if(bedCountOption.getSelectedItem().equals("All")){
            cruiseSearch.setBedCount(roomSearch.bedCountType.ALL);

        } else if (bedCountOption.getSelectedItem().equals("1")) {
            cruiseSearch.setBedCount(roomSearch.bedCountType.ONE);

        }else if (bedCountOption.getSelectedItem().equals("2")) {
            cruiseSearch.setBedCount(roomSearch.bedCountType.TWO);

        }else if (bedCountOption.getSelectedItem().equals("3")) {
            cruiseSearch.setBedCount(roomSearch.bedCountType.THREE);

        }else if (bedCountOption.getSelectedItem().equals("4")) {
            cruiseSearch.setBedCount(roomSearch.bedCountType.FOUR);
        }

        //sort option
        if(sortTypeOption.getSelectedItem().equals("None")){
            cruiseSearch.setPriceSorting(roomSearch.priceSortType.NONE);

        } else if (sortTypeOption.getSelectedItem().equals("Price: Ascending")) {
            cruiseSearch.setPriceSorting(roomSearch.priceSortType.ASCENDING);

        }else if (sortTypeOption.getSelectedItem().equals("Price: Descending")) {
            cruiseSearch.setPriceSorting(roomSearch.priceSortType.DESCENDING);
        }
    }
}
