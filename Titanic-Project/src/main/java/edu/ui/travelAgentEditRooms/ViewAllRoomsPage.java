package edu.ui.travelAgentEditRooms;

import edu.core.reservation.Room;
import edu.databaseAccessors.ReservationDatabase;
import edu.exceptions.NoMatchingReservationException;
import edu.ui.landingPage.LandingPage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.List;

/**
 * GUI class for displaying and managing all rooms available for reservations.
 * Allows the travel agent to view a list of rooms, select a room for further actions,
 * and navigate back to the landing page.
 * Utilizes the {@link ViewAllRoomsController} to interact with room data.
 *
 * @author William Delano
 * @version 1.0
 * @see Room, ReservationDatabase, NoMatchingReservationException, LandingPage, ViewAllRoomsController
 */
public class ViewAllRoomsPage {
    private JFrame frame;
    private JPanel contentPanel;
    private JTable roomsTable;
    private Timer refreshTimer;
    LandingPage prevPage;

    /**
     * Constructor for the ViewAllRoomsPage class.
     *
     * @param prevPage The landing page to return to when navigating back.
     */
    public ViewAllRoomsPage(LandingPage prevPage) {
        this.prevPage = prevPage;
        prepareUI();
    }

    /**
     * Prepares the user interface for displaying all available rooms.
     * Sets up the main frame, content panel, table, and buttons for interacting with room data.
     * Initializes the button actions for selecting a room and navigating back to the landing page.
     * Invokes the {@link ViewAllRoomsController#getAllRooms()} method to retrieve room data from the database.
     * Displays the room information in a JTable and adjusts column widths for better visibility.
     * Finally, makes the frame visible to the user.
     */
    private void prepareUI() {
        frame = new JFrame("All Rooms");
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JButton select = new JButton("Select");
        select.addActionListener(e -> {
            try {
                selectRow(roomsTable);
            } catch (NoMatchingReservationException ex) {
                ex.printStackTrace();
            }
        });

        JButton back = new JButton("Back");
        back.addActionListener(e -> {
            frame.dispose();
            prevPage.show();
        });

        JPanel buttonPanel = new JPanel(); // Create a new panel for buttons
        buttonPanel.add(select);
        buttonPanel.add(back);

        contentPanel.add(buttonPanel, BorderLayout.SOUTH); // Add the button panel to the content panel

        roomsTable = new JTable();
        roomsTable.setAutoCreateRowSorter(true);
        roomsTable.setFillsViewportHeight(true);

        contentPanel.add(new JScrollPane(roomsTable), BorderLayout.CENTER);

        frame.add(contentPanel, BorderLayout.CENTER);

        refreshReservations();
        frame.setVisible(true);
    }


    /**
     * Refreshes the displayed list of rooms by retrieving the latest data from the database.
     */
    public void refreshReservations() {
        List<Room> roomList = ViewAllRoomsController.getAllRooms();

        int numReservations = roomList.size();
        String[][] data = new String[numReservations][7];
        int i = 0;

        for (Room temp : roomList) {
            data[i][0] = String.valueOf(temp.getRoomNumber());
            data[i][1] = "$" + String.valueOf(temp.getRoomPrice());
            data[i][2] = String.valueOf(temp.getNumberOfBeds());
            data[i][3] = String.valueOf(temp.getBedType());
            data[i][4] = String.valueOf(temp.getCruise());
            data[i][5] = String.valueOf(temp.getSmokingAvailable());
            data[i][6] = String.valueOf(temp.isBooked());
            i++;
        }

        String[] columnNames = {"#", "Price", "Number of Beds", "Bed Types", "Cruise", "Smoking", "Booked"};
        roomsTable.setModel(new DefaultTableModel(data, columnNames));

        TableColumnModel columnModel = roomsTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(1);
        columnModel.getColumn(4).setPreferredWidth(100);
    }

    /**
     * Displays the ViewAllRoomsPage.
     */
    public void show() {
        refreshReservations();
        frame.setVisible(true);
    }

    /**
     * Handles the action when a row is selected in the JTable.
     *
     * @param table The JTable containing the list of rooms.
     * @throws NoMatchingReservationException If there is no matching reservation for the selected room.
     */
    private void selectRow(JTable table) throws NoMatchingReservationException {
        Room r;
        int selectedRow;
        selectedRow = table.getSelectedRow();

        //check if a row is selected
        if (selectedRow >= 0) {
            int modelRow = table.convertRowIndexToModel(selectedRow);
            DefaultTableModel model = (DefaultTableModel) table.getModel();

            //you can't cast the object to an int in case it's null, so you have to cast to string, then cast to int
            String row = (String) model.getValueAt(modelRow, 0);
            int intRow = Integer.parseInt(row);

            r = ViewAllRoomsController.getRoom(intRow);

            ViewAllRoomsController.editRoom(this, r);
            frame.setVisible(false);
        }
        else {
            JOptionPane.showMessageDialog(null, "No room is selected.");
        }
    }

    /**
     * The main method to test and demonstrate the ViewAllRoomsPage.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        new ViewAllRoomsPage(null).show();
    }
}
