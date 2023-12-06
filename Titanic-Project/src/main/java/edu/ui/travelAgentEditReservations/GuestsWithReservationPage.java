package edu.ui.travelAgentEditReservations;

import edu.core.cruise.Cruise;
import edu.core.users.*;
import edu.exceptions.UserNotFoundException;
import edu.ui.landingPage.TravelAgentLandingPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import edu.core.reservation.Room;
import edu.core.users.Guest;
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
    private JButton backButton, selectButton, searchButton;
    private JTextArea detailsTextArea;
    private JTable table;
    private JButton newReservation;
    private JList<String> guestList;
    private JTextField searchTextField;
    private JMenuBar searchMenu;
    private GuestSearch guestSearch;
    JPanel mainPanel;

    public GuestsWithReservationPage(TravelAgentLandingPage landingPage) throws UserNotFoundException {
        this.landingPage = landingPage;
        prepareGUI();
    }

    private void prepareGUI() {
        mainFrame = new JFrame("Select a Cruise");
        mainFrame.setSize(1000, 700);
        mainFrame.setLayout(new BorderLayout());

        titleLabel = new JLabel("Guests with a Reservation", JLabel.CENTER);
        //mainFrame.add(titleLabel, BorderLayout.NORTH);
        JPanel titlePanel = new JPanel();
        titlePanel.add(titleLabel);


        List<Guest> guestsWithReservations = null;

        try {
            guestsWithReservations = GuestsWithReservationController.getGuestsWithReservations();
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        guestSearch = new GuestSearch(guestsWithReservations);
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        generateSearchMenu();

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

        try {
            refreshRooms(guestsWithReservations);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //mainFrame.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        mainFrame.add(titlePanel,BorderLayout.NORTH);
        mainFrame.setJMenuBar(searchMenu);
        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
    }

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
    public void refreshRooms(List<Guest> list) throws UserNotFoundException {
        //List<Guest> guestList = GuestsWithReservationController.getGuestsWithReservations();
        List<Guest> guestList = new ArrayList<>(list);
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
        try {
            new ReservationListPage(this, guest);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void generateSearchMenu() {
        searchMenu = new JMenuBar();
        searchMenu.setPreferredSize(new Dimension(1000, 25));
        searchTextField = new JTextField();
        searchButton = new JButton("search");

        searchButton.addActionListener(e -> {
            List<Guest> list = guestSearch.findGuests(searchTextField.getText());
            System.out.println(list.size()+": ");
            try {
                refreshRooms(list);
            } catch (Exception x) {
                x.printStackTrace();
            }
        });

        searchMenu.add(searchTextField);
        searchMenu.add(searchButton);
    }

    private void createReservation() {
        new TravelAgentCreateReservationPage(this);
    }

    public void show() {
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) throws UserNotFoundException {
        TravelAgent ta = new TravelAgent("s","s","s","s","s");
        new GuestsWithReservationPage(new TravelAgentLandingPage(ta));
    }
}
