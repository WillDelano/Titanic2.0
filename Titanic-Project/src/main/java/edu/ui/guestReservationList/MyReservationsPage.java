package edu.ui.guestReservationList;

import edu.core.users.CurrentGuest;
import edu.core.reservation.Reservation;
import edu.core.users.Guest;
import edu.databaseAccessors.ReservationDatabase;
import edu.exceptions.NoMatchingReservationException;
import edu.ui.billingProcessing.ProcessBillingPage;
import edu.ui.reservationListInterface.ReservationListInterface;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.Set;


/**
 * UI display for a guest's reservations
 *
 * This class displays all the reservations assigned to a user
 *
 * @author Vincent Dinh
 * @version 1.0
 * @see ReservationDatabase , Reservation
 */
public class MyReservationsPage implements ReservationListInterface {
    private JFrame frame;
    private JPanel contentPanel;
    private JTable reservationsTable;
    private Timer refreshTimer;

    public MyReservationsPage() throws SQLException {
        prepareUI();
    }

    private void prepareUI() throws SQLException {
        Set<Reservation> reservationSet = null;
        try {
            reservationSet = CurrentGuest.getCurrentGuest().getReservations();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Reservations: ");

        for (Reservation q : reservationSet) {
            System.out.println("\t" + q.getRoom());
        }

        frame = new JFrame("My Reservations");
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JButton select = new JButton("Select");
        select.addActionListener(e -> {
            try {
                selectRow(reservationsTable);
            } catch (NoMatchingReservationException ex) {
                ex.printStackTrace();
            }
        });

        JPanel buttonPanel = new JPanel(); // Create a new panel for buttons
        buttonPanel.add(select);

        contentPanel.add(buttonPanel, BorderLayout.SOUTH); // Add the button panel to the content panel

        JButton payNowButton = new JButton("Pay Now");
        payNowButton.addActionListener(e -> navigateToProcessBilling());

        buttonPanel.add(payNowButton);

        reservationsTable = new JTable();
        reservationsTable.setAutoCreateRowSorter(true);
        reservationsTable.setFillsViewportHeight(true);

        contentPanel.add(new JScrollPane(reservationsTable), BorderLayout.CENTER);

        frame.add(contentPanel, BorderLayout.CENTER);

        refreshReservations();
    }

    public void refreshReservations() throws SQLException {
        Set<Reservation> reservationSet = CurrentGuest.getCurrentGuest().getReservations();


        int numReservations = reservationSet.size();
        String[][] data = new String[numReservations][8];
        int i = 0;

        for (Reservation temp : reservationSet) {
            data[i][0] = String.valueOf(i + 1);
            data[i][1] = String.valueOf(temp.getRoom().getCruise());
            data[i][2] = String.valueOf(temp.getRoom().getRoomNumber());
            data[i][3] = String.valueOf(temp.getStartDate());
            data[i][4] = String.valueOf(temp.getEndDate());
            data[i][5] = String.valueOf(temp.getStartCountry().getName());
            data[i][6] = String.valueOf(temp.getEndCountry().getName());
            data[i][7] = String.valueOf(temp.getRoom().getRoomPrice());
            i++;
        }

        String[] columnNames = {"#", "Cruise", "Room Number", "Start Date", "End Date", "Start Country", "End Country", "Room Price"};
        reservationsTable.setModel(new DefaultTableModel(data, columnNames));

        TableColumnModel columnModel = reservationsTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(1);
        columnModel.getColumn(1).setPreferredWidth(100);
    }

    public void show() throws SQLException {
        refreshReservations();
        frame.setVisible(true);
    }

    private void selectRow(JTable table) throws NoMatchingReservationException {
        Reservation r;
        int selectedRow;
        selectedRow = table.getSelectedRow();

        //check if a row is selected
        if (selectedRow >= 0) {
            int modelRow = table.convertRowIndexToModel(selectedRow);
            DefaultTableModel model = (DefaultTableModel) table.getModel();

            //you can't cast the object to an int in case it's null, so you have to cast to string, then cast to int
            String row = (String) model.getValueAt(modelRow, 2);
            int intRow = Integer.parseInt(row);

            r = MyReservationsPageController.getReservation(intRow);

            MyReservationsPageController.editReservation(this, r);
            frame.setVisible(false);
        }
        else {
            JOptionPane.showMessageDialog(null, "No reservation is selected.");
        }
    }

    private void navigateToProcessBilling() {
        int selectedRow = reservationsTable.getSelectedRow();
        if (selectedRow >= 0) {
            int modelRow = reservationsTable.convertRowIndexToModel(selectedRow);
            DefaultTableModel model = (DefaultTableModel) reservationsTable.getModel();
            String priceString = (String) model.getValueAt(modelRow, 7);
            double price = Double.parseDouble(priceString);

            ProcessBillingPage billingPage = new ProcessBillingPage(price);
            billingPage.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Please select a reservation to pay.");
        }
    }
    public static void main(String[] args) throws SQLException {
        Guest g = new Guest("wdelano", "baylor", "Will", "Delano", 0, "wdelano2002@gmail.com");

        CurrentGuest.setCurrentGuest(g);
        new MyReservationsPage().show();
    }
}