package edu.ui.guestBrowseRooms;

import edu.core.reservation.Reservation;
import edu.core.reservation.Room;
import edu.core.users.CurrentGuest;
import edu.databaseAccessors.RoomDatabase;
import edu.exceptions.NoMatchingRoomException;
import edu.ui.roomListInterface.RoomListInterface;
import edu.ui.guestSelectCruise.SelectCruisePage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
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
public class BrowseRoomPage implements RoomListInterface {

    private JFrame roomFrame;
    private JLabel titleLabel;
    private JList<Room> roomList;
    private JButton backButton;
    private JButton selectRoomButton;
    private JTable roomTable;
    private String selectedCruise;
    private SelectCruisePage prevPage;
    public BrowseRoomPage(SelectCruisePage prevPage, String selectedCruise) {
        this.selectedCruise = selectedCruise;
        this.prevPage = prevPage;
        prepareGUI();
    }

    private void prepareGUI() {
        roomFrame = new JFrame("Rooms for Cruise: " + selectedCruise);
        roomFrame.setSize(1000, 700);
        roomFrame.setLayout(new BorderLayout());

        titleLabel = new JLabel("Available Rooms for " + selectedCruise, JLabel.CENTER);
        roomFrame.add(titleLabel, BorderLayout.NORTH);

        backButton = new JButton("Back to Cruise Details");
        backButton.addActionListener(e -> {
            roomFrame.dispose();
            prevPage.show();
        });


        selectRoomButton = new JButton("Select Room");
        selectRoomButton.addActionListener(e -> {
            try {
                selectRow(roomTable);
            } catch (NoMatchingRoomException ex) {
                ex.printStackTrace();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(selectRoomButton);

        roomFrame.add(buttonPanel, BorderLayout.SOUTH);
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        roomTable = new JTable();
        roomTable.setAutoCreateRowSorter(true);
        roomTable.setFillsViewportHeight(true);

        contentPanel.add(new JScrollPane(roomTable), BorderLayout.CENTER);

        roomFrame.add(contentPanel, BorderLayout.CENTER);
        refreshRooms();
    }

    private void selectRow(JTable roomTable) throws NoMatchingRoomException{
        //Room selectedRoom = roomList.getSelectedValue();
        BrowseRoomController controller = new BrowseRoomController();

        Room r;
        int selectedRowForDeletion;
        selectedRowForDeletion = roomTable.getSelectedRow();

        //check if a row is selected
        if (selectedRowForDeletion >= 0) {
            int modelRow = roomTable.convertRowIndexToModel(selectedRowForDeletion);
            DefaultTableModel model = (DefaultTableModel) roomTable.getModel();

            //you can't cast the object to an int in case it's null, so you have to cast to string, then cast to int
            String row = (String) model.getValueAt(modelRow, 1);
            int test = Integer.parseInt(row);

            r = BrowseRoomController.getRoom(test);
            if (r != null) {
                //controller.reserveRoom(CurrentGuest.getCurrentGuest(), r);
                JOptionPane.showMessageDialog(roomFrame, "Room " + r.getRoomNumber() + " reserved.");
            } else {
                JOptionPane.showMessageDialog(roomFrame, "Please select a room first.");
            }

            roomFrame.setVisible(false);
        }
        else {
            JOptionPane.showMessageDialog(null, "No reservation is selected.");
        }
    }

    public void refreshRooms() {
        List<Room> roomSet = BrowseRoomController.getRooms(selectedCruise);
        System.err.println("Reservations: ");
        int numRooms = 0;

        for (Room q : roomSet) {
            System.err.println("\t" + q);
            if(!q.isBooked()){
                numRooms++;
            }
        }

        //int numRooms = roomSet.size();
        String[][] data = new String[numRooms][7];
        int i = 0;

        for (Room temp : roomSet) {
            if(!temp.isBooked()){
                data[i][0] = String.valueOf(i + 1);
                data[i][1] = String.valueOf(temp.getRoomNumber());
                data[i][2] = String.valueOf(temp.getNumberOfBeds());
                data[i][3] = String.valueOf(temp.getBedType());
                data[i][4] = String.valueOf(temp.getSmokingAvailable());
                data[i][5] = String.valueOf(temp.getRoomPrice());
                data[i][6] = String.valueOf(temp.getCruise());
                i++;
            }
        }

        String[] columnNames = {"#", "Room Number", "Number of Beds", "Bed Type", "Smoking Available", "Room Price", "Cruise"};
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
    public void show() {
        refreshRooms();
        roomFrame.setVisible(true);
    }
    public static void main(String[] args) {
        //new BrowseRoomPage("Caribbean Adventure").show();
    }
}
