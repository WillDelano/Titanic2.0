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
 * UI display for a guest's reservations
 *
 * This class displays all the reservations assigned to a user
 *
 * @author Vincent Dinh
 * @version 1.0
 * @see ReservationDatabase , Reservation
 */
public class ViewAllRoomsPage {
    private JFrame frame;
    private JPanel contentPanel;
    private JTable roomsTable;
    private Timer refreshTimer;
    LandingPage prevPage;

    public ViewAllRoomsPage(LandingPage prevPage) {
        this.prevPage = prevPage;
        prepareUI();
    }

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

    public void refreshReservations() {
        List<Room> roomList = ViewAllRoomsController.getAllRooms();

        int numReservations = roomList.size();
        String[][] data = new String[numReservations][7];
        int i = 0;

        for (Room temp : roomList) {
            data[i][0] = String.valueOf(temp.getRoomNumber());
            data[i][1] = "$" + String.valueOf(temp.getRoomPrice());
            data[i][2] = String.valueOf(temp.getNumberOfBeds());

            data[i][3] = String.valueOf(temp.getBedTypeStr());

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

    public void show() {
        refreshReservations();
        frame.setVisible(true);
    }

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

    public static void main(String[] args) {
        new ViewAllRoomsPage(null).show();
    }
}
