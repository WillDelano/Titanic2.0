package edu.ui.guestBrowseRooms;

import edu.core.reservation.Room;
import edu.core.users.CurrentGuest;
import edu.databaseAccessors.RoomDatabase;

import javax.swing.*;
import edu.core.reservation.Reservation;
import edu.core.reservation.Room;
import edu.core.users.CurrentGuest;
import edu.databaseAccessors.RoomDatabase;
import edu.exceptions.NoMatchingRoomException;
import edu.ui.guestCreateReservation.GuestCreateReservationPage;
import edu.ui.roomListInterface.RoomListInterface;
import edu.ui.guestSelectCruise.SelectCruisePage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.stream.Collectors;
import edu.core.reservation.roomSearch;

/**
 * UI for displaying all rooms on a cruise
 *
 * This class displays all the rooms of a selected cruise to a user
 *
 * @author Vincent Dinh
 * @version 1.0
 * @see RoomDatabase , Room, BrowseRoomController
 */

public class BrowseRoomPage implements RoomListInterface {
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
    private boolean optionVisible = false;
    private JTable roomTable;
    private String selectedCruise;
    private SelectCruisePage prevPage;
    private JComboBox<String> bedTypeFilter;
    private JComboBox<Boolean> smokingFilter;
    private List<Room> allRooms;

    public BrowseRoomPage(SelectCruisePage prevPage, String selectedCruise) {
        this.selectedCruise = selectedCruise;
        this.prevPage = prevPage;
        allRooms = RoomDatabase.getRoomsForCruise(selectedCruise);
        prepareGUI();
    }

    private void prepareGUI() {
        roomFrame = new JFrame("Rooms for Cruise: " + selectedCruise);
        roomFrame.setSize(1000, 700);
        roomFrame.setLayout(new BorderLayout());

        titleLabel = new JLabel("Available Rooms for " + selectedCruise, JLabel.CENTER);

        northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout());
        northPanel.add(titleLabel, BorderLayout.NORTH);

        generateSearchMenu();
        generateFilterPanel();
        cruiseSearch = new roomSearch(allRooms);

        roomFrame.add(northPanel, BorderLayout.NORTH);

        selectRoomButton = new JButton("Select Room");
        selectRoomButton.addActionListener(e -> {
            try {
                selectRow(roomTable);
            } catch (NoMatchingRoomException ex) {
                ex.printStackTrace();
            }
        });

        backButton = new JButton("Back to Cruise Details");
        backButton.addActionListener(e -> {
            roomFrame.dispose();
            prevPage.show();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(selectRoomButton);

        roomFrame.add(buttonPanel, BorderLayout.SOUTH);

        roomTable = new JTable();
        roomTable.setAutoCreateRowSorter(true);
        roomTable.setFillsViewportHeight(true);
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPanel.add(new JScrollPane(roomTable), BorderLayout.CENTER);

        roomFrame.add(contentPanel, BorderLayout.CENTER);
        roomFrame.add(northPanel, BorderLayout.NORTH);
        roomFrame.setJMenuBar(searchMenu);
        roomFrame.add(buttonPanel, BorderLayout.SOUTH);

        refreshRooms();

        roomFrame.setVisible(true);
    }


    private void filterRooms(ActionEvent e) {
        String searchText = searchTextField.getText().toLowerCase();
        String selectedBedType = bedTypeFilter.getSelectedItem().toString();
        boolean selectedSmoking = (Boolean)smokingFilter.getSelectedItem();

        List<Room> filteredRooms = allRooms.stream()
                .filter(room -> room.toString().toLowerCase().contains(searchText))
                .filter(room -> selectedBedType.equals("All") || room.getBedType().equals(selectedBedType))
                .filter(room -> room.getSmokingAvailable() == selectedSmoking)
                .collect(Collectors.toList());

        updateRoomTable(filteredRooms);
    }

    private void updateRoomTable(List<Room> rooms) {
        String[] columnNames = {"Room Number", "Number of Beds", "Bed Type", "Smoking Available", "Room Price", "Cruise"};

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Room room : rooms) {
            String smoking = room.getSmokingAvailable() ? "Yes" : "No";
            Object[] row = new Object[]{
                    room.getRoomNumber(),
                    room.getNumberOfBeds(),
                    room.getBedType(),
                    smoking,
                    room.getRoomPrice(),
                    room.getCruise()
            };
            model.addRow(row);
        }
        roomTable.setModel(model);
    }

    private void selectRow(JTable roomTable) throws NoMatchingRoomException{
        BrowseRoomController controller = new BrowseRoomController();

        Room r;
        int selectedRowForDeletion;
        selectedRowForDeletion = roomTable.getSelectedRow();

        //check if a row is selected
        if (selectedRowForDeletion >= 0) {
            int modelRow = roomTable.convertRowIndexToModel(selectedRowForDeletion);
            DefaultTableModel model = (DefaultTableModel) roomTable.getModel();

            //you can't cast the object to an int in case it's null, so you have to cast to string, then cast to int
            String row = model.getValueAt(modelRow, 0).toString();
            int roomNumber = Integer.parseInt(row);

            r = BrowseRoomController.getRoom(roomNumber);
            if (r != null) {
                new GuestCreateReservationPage(this, r);
            } else {
                JOptionPane.showMessageDialog(roomFrame, "Please select a room first.");
            }

            roomFrame.setVisible(false);
        }
        else {
            JOptionPane.showMessageDialog(null, "No reservation is selected.");
        }
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
            applyFilters();
            List <Room> list = cruiseSearch.findRooms(searchTextField.getText());
            updateRoomTable(list);
            /*
            currentRooms = new ArrayList<>(list);
            roomFrame.remove(listScrollPane);

            roomList = new JList<>(list.toArray(new Room[0]));
            listScrollPane = new JScrollPane(roomList);
            listScrollPane.getViewport().revalidate();
            listScrollPane.getViewport().repaint();
            roomFrame.add(listScrollPane, BorderLayout.CENTER);

            roomFrame.revalidate();
            roomFrame.repaint();*/
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
        smokingBox = new JCheckBox("Smoking");
        smokingBox.setSelected(true);
        nonSmokingBox = new JCheckBox("Non-Smoking  ");
        nonSmokingBox.setSelected(true);
        applyButton = new JButton("apply");

        bedCount = new JLabel("number of beds  ");
        sortBy = new JLabel("sortBy  ");

        String bedCounts[] = { "All","1", "2", "3", "4"};
        bedCountOption = new JComboBox<>(bedCounts);

        String sortTypes[] = { "None","Price: Ascending", "Price: Descending"};
        sortTypeOption = new JComboBox<>(sortTypes);

        applyButton.addActionListener( e->{
            applyFilters();
            //roomFrame.remove(listScrollPane);

            List<Room> list = new ArrayList<>(cruiseSearch.sortAndFilterRooms(allRooms));
            updateRoomTable(list);
            /*
            roomList = new JList<>(list.toArray(new Room[0]));

            listScrollPane = new JScrollPane(roomList);
            listScrollPane.getViewport().revalidate();
            listScrollPane.getViewport().repaint();
            roomFrame.add(listScrollPane, BorderLayout.CENTER);

            roomFrame.revalidate();
            roomFrame.repaint();*/
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

    public void refreshRooms() {
        List<Room> roomSet = BrowseRoomController.getRooms(selectedCruise);

        int numRooms = roomSet.size();
        String[][] data = new String[numRooms][6];
        int i = 0;

        for (Room temp : roomSet) {
            if(!temp.isBooked()){
                data[i][0] = String.valueOf(temp.getRoomNumber());
                data[i][1] = String.valueOf(temp.getNumberOfBeds());
                data[i][2] = String.valueOf(temp.getBedType());
                data[i][3] = String.valueOf(temp.getSmokingAvailable());
                data[i][4] = String.valueOf(temp.getRoomPrice());
                data[i][5] = String.valueOf(temp.getCruise());
                i++;
            }
        }

        String[] columnNames = {"Room Number", "Number of Beds", "Bed Type", "Smoking Available", "Room Price", "Cruise"};
        DefaultTableModel finalModel = new DefaultTableModel(data, columnNames){
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        roomTable.setModel(finalModel);

        TableColumnModel columnModel = roomTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(1);
        columnModel.getColumn(1).setPreferredWidth(100);
    }
    private void applyFilters(){

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
    public void show() {
        refreshRooms();
        roomFrame.setVisible(true);
    }
    public static void main(String[] args) {
        //new BrowseRoomPage("Caribbean Adventure").show();
    }
}