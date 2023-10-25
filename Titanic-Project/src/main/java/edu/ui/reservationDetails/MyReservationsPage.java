package edu.ui.reservationDetails;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

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

        // random data just to show up
        String[][] data = {
                {"1", "Reservation 1", "Date 1", "Status 1"},
                {"2", "Reservation 2", "Date 2", "Status 2"},
                {"3", "Reservation 3", "Date 3", "Status 3"}
        };
        String[] columnNames = {"ID", "Reservation Name", "Date", "Status"};

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
