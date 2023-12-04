package edu.ui.travelAgentEditReservations;

import edu.core.cruise.Cruise;
import edu.core.reservation.Reservation;
import edu.core.users.Guest;
import edu.ui.reservationListInterface.ReservationListInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import edu.core.reservation.Room;
import edu.core.users.CurrentGuest;
import edu.core.users.Guest;
import edu.databaseAccessors.ReservationDatabase;
import edu.exceptions.NoMatchingReservationException;
import edu.ui.guestBrowseRooms.BrowseRoomController;
import edu.ui.guestReservationList.MyReservationsPageController;
import edu.ui.reservationListInterface.ReservationListInterface;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

/**
 * Controller for displaying and selecting a cruise on the ui
 *
 * This class allows a user to browse and select cruises
 *
 * @author Vincent Dinh
 * @version 1.0
 * @see Cruise , CruiseDatabase, CruiseDetailsPage
 */
public class ReservationListPage implements ReservationListInterface {
    private JFrame mainFrame;
    private JLabel titleLabel;
    private JButton selectButton;
    private JButton backButton;
    private GuestsWithReservationPage previousPage;
    private Guest guest;
    private JTable reservationTable;

    public ReservationListPage(GuestsWithReservationPage previousPage, Guest guest) throws SQLException {
        this.previousPage = previousPage;
        this.guest = guest;
        prepareGUI();
    }

    private void prepareGUI() throws SQLException {
        Set<Reservation> reservationSet = ReservationListPageController.getReservationList(guest);

        System.out.println("Reservations: ");

        for (Reservation q : reservationSet) {
            System.out.println("\t" + q.getRoom());
        }

        mainFrame = new JFrame("Reservation List");
        mainFrame.setSize(1000, 700);
        mainFrame.setLayout(new BorderLayout());

        titleLabel = new JLabel(guest.getUsername() + "'s Reservations", JLabel.CENTER);
        mainFrame.add(titleLabel, BorderLayout.NORTH);

        Set<Reservation> listOfReservations = ReservationListPageController.getReservationList(guest);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));

        JButton selectButton = new JButton("Select");
        selectButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        selectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    selectRow(reservationTable);
                } catch (NoMatchingReservationException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            mainFrame.dispose(); // Close the Edit Reservation page
            previousPage.show(); // Go back to the landingPage
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(selectButton);
        JButton dialogButton = new JButton("CheckIn");
        dialogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                javax.swing.SwingUtilities.invokeLater(() -> {
                    try {
                        checkIn(reservationTable);
                    } catch (NoMatchingReservationException ex) {
                        throw new RuntimeException(ex);
                    }
                });
            }
        });
        buttonPanel.add(dialogButton);
        JButton button = new JButton("CheckOut");
        button.addActionListener(new RemoveLineActionLister());
        buttonPanel.add(button);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        reservationTable = new JTable();
        reservationTable.setAutoCreateRowSorter(true);
        reservationTable.setFillsViewportHeight(true);

        contentPanel.add(new JScrollPane(reservationTable), BorderLayout.CENTER);

        mainFrame.add(contentPanel, BorderLayout.CENTER);
        mainFrame.add(buttonPanel, BorderLayout.SOUTH);
        mainFrame.setVisible(true);
        refreshRooms();
    }

    private void handleReservationSelection(Reservation reservation) {
        mainFrame.setVisible(false);
        new EditReservationPage(this, reservation.getRoom().getCruise(), reservation);
    }

    public void refreshRooms() throws SQLException {
        Set<Reservation> reservationSet = ReservationListPageController.getReservationList(guest);

        int numReservations = reservationSet.size();
        String[][] data = new String[numReservations][9];
        int i = 0;

        for (Reservation temp : reservationSet) {
            data[i][0] = String.valueOf(i + 1);
            data[i][1] = String.valueOf(temp.getId());
            data[i][2] = String.valueOf(temp.getStartDate());
            data[i][3] = String.valueOf(temp.getEndDate());
            data[i][4] = String.valueOf(temp.getRoom().getRoomNumber());
            data[i][5] = String.valueOf(temp.getStartCountry().getName());
            data[i][6] = String.valueOf(temp.getEndCountry().getName());
            data[i][7] = String.valueOf(temp.getDays());
            data[i][8] = String.valueOf(temp.getCheckedIn());
            i++;
        }

        String[] columnNames = {"#", "Reservation ID", "Check-in Date", "Check-out Date", "Room #", "Start Country", "End Country", "Total Trip Duration","Checked In"};
        DefaultTableModel finalModel = new DefaultTableModel(data, columnNames) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        reservationTable.setModel(finalModel);

        TableColumnModel columnModel = reservationTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(1);
        columnModel.getColumn(1).setPreferredWidth(100);
    }

    private void selectRow(JTable table) throws NoMatchingReservationException {
        Reservation r;
        int selectedRow;
        selectedRow = table.getSelectedRow();

        //check if a row is selected
        if (selectedRow >= 0) {

            //you can't cast the object to an int in case it's null, so you have to cast to string, then cast to int
            String row = (String) table.getValueAt(selectedRow, 4);
            int room = Integer.parseInt(row);

            r = ReservationDatabase.getReservationByRoom(room);

            handleReservationSelection(r);
            mainFrame.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(null, "No reservation is selected.");
        }
    }

    private void checkIn(JTable table) throws NoMatchingReservationException {
        Reservation r;
        int selectedRow;
        selectedRow = table.getSelectedRow();
        boolean checkedIn = false;

        //check if a row is selected
        if (selectedRow >= 0) {

            int modelRow = table.convertRowIndexToModel(selectedRow);
            DefaultTableModel model = (DefaultTableModel) table.getModel();

            //you can't cast the object to an int in case it's null, so you have to cast to string, then cast to int
            String row = (String) model.getValueAt(modelRow, 0);
            System.out.println("the row is: " + row);
            int intRow = Integer.parseInt(row);
            String roomNumStr = String.valueOf(table.getValueAt(selectedRow, 4));
            System.out.println("the room num is: " + roomNumStr);
            int RoomNum = Integer.parseInt(roomNumStr);
            r = ReservationListPageController.getReservation(RoomNum);
            checkedIn = ReservationListPageController.CheckInGuest(r);

        }
    }

    private final class RemoveLineActionLister implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRowForDeletion;
            selectedRowForDeletion = reservationTable.getSelectedRow();
            //check if a row is selected
            if (selectedRowForDeletion >= 0) {

                int modelRow = reservationTable.convertRowIndexToModel(selectedRowForDeletion);
                DefaultTableModel model = (DefaultTableModel) reservationTable.getModel();
                int answer = JOptionPane
                        .showConfirmDialog(null,
                                "Do you want to checkout Guest " + model.getValueAt(modelRow, 1) + " from room "
                                        + model.getValueAt(modelRow, 5) + "?",
                                "Warning", JOptionPane.YES_NO_OPTION);
                if (answer == 0) {
                    model.removeRow(modelRow);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Unable to delete");
            }
        }
    }


    public void show() throws SQLException {
        mainFrame.setVisible(true);
        refreshRooms();
    }

}