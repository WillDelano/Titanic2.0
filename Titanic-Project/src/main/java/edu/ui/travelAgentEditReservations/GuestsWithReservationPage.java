package edu.ui.travelAgentEditReservations;

import edu.core.cruise.Cruise;
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
 * Controller for displaying and selecting a cruise on the ui
 *
 * This class allows a user to browse and select cruises
 *
 * @author Vincent Dinh
 * @version 1.0
 * @see Cruise, CruiseDatabase, CruiseDetailsPage
 */
public class GuestsWithReservationPage {
    private TravelAgentLandingPage landingPage;
    private JFrame mainFrame;
    private JLabel titleLabel;
    private JButton backButton;
    private JTextArea detailsTextArea;
    private JTable table;
    private JButton newReservation;

    public GuestsWithReservationPage(TravelAgentLandingPage landingPage) {
        this.landingPage = landingPage;
        prepareGUI();
    }

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

        buttonPanel = new JPanel();
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

    public void refreshRooms() throws UserNotFoundException, SQLException {
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

    private void handleReservationList(Guest guest) {
        mainFrame.setVisible(false);
        new ReservationListPage(this, guest);
    }

    private void createReservation() {
        new TravelAgentCreateReservationPage(this);
    }

    public void show() {
        mainFrame.setVisible(true);
    }


    public static void main(String[] args){
        TravelAgent ta = new TravelAgent("s","s","s","s","s");
        new GuestsWithReservationPage(new TravelAgentLandingPage(ta));
    }
}

