package edu.ui.reservationDetails;

import edu.core.cruise.Cruise;
import edu.core.users.CurrentGuest;
import edu.core.reservation.Reservation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
 * UI display for a guest's reservations
 *
 * This class displays all the reservations assigned to a user
 *
 * @author Vincent Dinh
 * @version 1.0
 * @see edu.database.ReservationDatabase, Reservation
 */
public class MyReservationsPage {
    private JFrame frame;
    private JPanel contentPanel;
    private JTable reservationsTable;
    private Timer refreshTimer;

    public MyReservationsPage() {
        prepareUI();
        setUpRefreshMechanism();
    }

    private void prepareUI() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame = new JFrame("My Reservations");
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        reservationsTable = new JTable();
        reservationsTable.setAutoCreateRowSorter(true);
        reservationsTable.setFillsViewportHeight(true);
        contentPanel.add(new JScrollPane(reservationsTable), BorderLayout.CENTER);

        frame.add(contentPanel, BorderLayout.CENTER);

        refreshReservations();
    }

    public void refreshReservations() {
        Set<Reservation> reservationSet = CurrentGuest.getCurrentGuest().getReservations();
        System.err.println("Reservations: ");

        for (Reservation q : reservationSet) {
            System.err.println(q.getRoom());
        }

        int numReservations = reservationSet.size();
        String[][] data = new String[numReservations][5];
        int i = 0;

        for (Reservation temp : reservationSet) {
            data[i][0] = String.valueOf(i + 1);
            data[i][1] = String.valueOf(temp.getRoom().getRoomNumber());
            data[i][2] = String.valueOf(temp.getStartDate());
            data[i][3] = String.valueOf(temp.getEndDate());
            data[i][4] = String.valueOf(temp.getEndCountry().getName());
            i++;
        }

        String[] columnNames = {"Reservation", "Room Number", "Start Date", "End Date", "End Country"};
        reservationsTable.setModel(new DefaultTableModel(data, columnNames));
    }

    private void setUpRefreshMechanism() {
        int delay = 10000; // 10 seconds
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                refreshReservations();
            }
        };
        refreshTimer = new Timer(delay, taskPerformer);  // Initialize the Timer instance variable
        refreshTimer.start();

        // Add a WindowListener to refresh reservations every time the window is activated.
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                refreshReservations();
            }
        });
    }

    public void show() {
        frame.setVisible(true);
        if (!refreshTimer.isRunning()) {
            refreshTimer.start();  // Ensure the timer starts running when the window is shown
        }
    }

    public static void main(String[] args) {
        new MyReservationsPage().show();
    }
}
