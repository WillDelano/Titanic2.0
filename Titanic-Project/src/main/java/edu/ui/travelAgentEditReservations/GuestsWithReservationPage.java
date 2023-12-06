package edu.ui.travelAgentEditReservations;

import edu.core.cruise.Cruise;
import edu.core.users.Guest;
import edu.exceptions.UserNotFoundException;
import edu.ui.landingPage.TravelAgentLandingPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import edu.core.reservation.Room;
import edu.core.users.CurrentGuest;
import edu.core.users.Guest;
import edu.core.users.TravelAgent;
import edu.exceptions.NoMatchingRoomException;
import edu.ui.landingPage.LandingPage;
import edu.ui.landingPage.TravelAgentLandingPage;
import edu.exceptions.UserNotFoundException;
import edu.ui.landingPage.TravelAgentLandingPage;
import edu.ui.travelAgentCreateReservations.TravelAgentCreateReservationPage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Opens a Swing page for a travel agent to view guests with reservations.
 * Allows the travel agent to select a guest, view reservation details, and create new reservations.
 *
 * @author Vincent Dinh, Gabriel Choi
 * @version 1.0
 */
public class GuestsWithReservationPage {
    private TravelAgentLandingPage landingPage;
    private JFrame mainFrame;
    private JLabel titleLabel;
    private JButton backButton;
    private JTextArea detailsTextArea;
    private JTable table;
    private JButton newReservation;

    /**
     * Constructs a new GuestsWithReservationPage.
     *
     * @param landingPage The landing page for the travel agent.
     * @throws UserNotFoundException If a user is not found.
     */
    public GuestsWithReservationPage(TravelAgentLandingPage landingPage) throws UserNotFoundException {
        this.landingPage = landingPage;
        prepareGUI();
    }

    /**
     * Prepares the graphical user interface for the GuestsWithReservationPage.
     */
    private void prepareGUI() {
        mainFrame = new JFrame("Select a Cruise");
        mainFrame.setSize(1000, 700);
        mainFrame.setLayout(new BorderLayout());

        titleLabel = new JLabel("Guests with a Reservation", JLabel.CENTER);
        mainFrame.add(titleLabel, BorderLayout.NORTH);

        List<Guest> guestsWithReservations = null;

        try {
            guestsWithReservations = GuestsWithReservationController.getGuestsWithReservations();
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));

        JPanel buttonPanel = new JPanel();
        JButton selectButton = new JButton("Select");
        selectButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        selectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //handleReservationList(guest);
                selectRow(table);
            }
        });
        buttonPanel.add(selectButton);

        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            mainFrame.dispose(); // Close the Edit Reservation page
            landingPage.show(); // Go back to the landingPage
        });

        newReservation = new JButton("Create a new reservation");
        newReservation.addActionListener(e -> createReservation());

        buttonPanel.add(backButton);
        buttonPanel.add(newReservation);

        mainFrame.add(buttonPanel, BorderLayout.SOUTH);
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        table = new JTable();
        table.setAutoCreateRowSorter(true);
        table.setFillsViewportHeight(true);

        contentPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        mainFrame.add(contentPanel, BorderLayout.CENTER);

        try {
            refreshRooms();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
    }

    /**
     * Refreshes the table with the list of guests with reservations.
     *
     * @throws UserNotFoundException If a user is not found.
     */
    public void refreshRooms() throws UserNotFoundException {
        List<Guest> guestList = GuestsWithReservationController.getGuestsWithReservations();
        System.err.println("Reservations: ");
        int numGuests = guestList.size();

        //int numRooms = roomSet.size();
        String[][] data = new String[numGuests][5];
        int i = 0;

        for (Guest temp : guestList) {
            data[i][0] = String.valueOf(i + 1);
            data[i][1] = String.valueOf(temp.getUsername());
            data[i][2] = String.valueOf(temp.getFirstName());
            data[i][3] = String.valueOf(temp.getLastName());
            data[i][4] = String.valueOf(temp.getReservations().size());
            i++;
        }

        String[] columnNames = {"#", "Guest Username", "Guest Firstname", "Guest Lastname", "# of Reservations"};
        DefaultTableModel finalModel = new DefaultTableModel(data, columnNames){
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        table.setModel(finalModel);

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(1);
        columnModel.getColumn(1).setPreferredWidth(100);
    }

    /**
     * Handles the selection of a row in the table.
     *
     * @param roomTable The table of guests with reservations.
     */
    private void selectRow(JTable roomTable){
        List<Guest> guestsWithReservations = new ArrayList<>();
        try {
            guestsWithReservations = GuestsWithReservationController.getGuestsWithReservations();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Guest r;
        int selectedRowForDeletion;
        selectedRowForDeletion = roomTable.getSelectedRow();

        //check if a row is selected
        if (selectedRowForDeletion >= 0) {
            handleReservationList(guestsWithReservations.get(selectedRowForDeletion));
            mainFrame.setVisible(false);
        }
        else {
            JOptionPane.showMessageDialog(null, "No reservation is selected.");
        }
    }

    /**
     * Handles the list of reservations for a selected guest.
     *
     * @param guest The selected guest.
     */
    private void handleReservationList(Guest guest) {
        mainFrame.setVisible(false);
        try {
            new ReservationListPage(this, guest);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a new reservation for a guest.
     */
    private void createReservation() {
        new TravelAgentCreateReservationPage(this);
    }

    /**
     * Displays the main frame of the GuestsWithReservationPage.
     */
    public void show() {
        mainFrame.setVisible(true);
    }

    /**
     * Main method for testing the GuestsWithReservationPage.
     *
     * @param args Command-line arguments.
     * @throws UserNotFoundException If a user is not found.
     */
    public static void main(String[] args) throws UserNotFoundException {
        TravelAgent ta = new TravelAgent("s","s","s","s","s");
        new GuestsWithReservationPage(new TravelAgentLandingPage(ta));
    }
}
