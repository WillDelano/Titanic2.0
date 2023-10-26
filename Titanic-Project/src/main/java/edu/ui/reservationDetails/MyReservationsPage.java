package edu.ui.reservationDetails;

import edu.core.users.CurrentGuest;
import edu.core.reservation.Reservation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Set;

public class MyReservationsPage {

    private JFrame frame;
    private JPanel contentPanel;
    private JTable reservationsTable;

    public MyReservationsPage() {
        prepareUI();
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

        //display guest's reservations
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
        };
        String[] columnNames = {"Reservation", "Room Number", "Start Date", "End Date", "End Country"};

        reservationsTable = new JTable(data, columnNames);
        reservationsTable.setAutoCreateRowSorter(true);
        reservationsTable.setFillsViewportHeight(true);

        contentPanel.add(new JScrollPane(reservationsTable), BorderLayout.CENTER);

        frame.add(contentPanel, BorderLayout.CENTER);
    }

    public void show() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new MyReservationsPage().show();
    }
}
