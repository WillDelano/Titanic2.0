package edu.ui.travelAgentEditReservations;

import edu.core.cruise.Cruise;
import edu.core.reservation.Room;
import edu.core.users.CurrentGuest;
import edu.core.users.Guest;
<<<<<<< HEAD:Titanic-Project/src/main/java/edu/ui/editReservation/GuestsWithReservationPage.java
import edu.core.users.TravelAgent;
import edu.exceptions.NoMatchingRoomException;
import edu.ui.landingPage.LandingPage;
import edu.ui.landingPage.TravelAgentLandingPage;
import edu.ui.roomDetails.BrowseRoomController;
=======
import edu.exceptions.UserNotFoundException;
import edu.ui.landingPage.TravelAgentLandingPage;
import edu.ui.travelAgentCreateReservations.TravelAgentCreateReservationPage;
>>>>>>> main:Titanic-Project/src/main/java/edu/ui/travelAgentEditReservations/GuestsWithReservationPage.java

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
<<<<<<< HEAD:Titanic-Project/src/main/java/edu/ui/editReservation/GuestsWithReservationPage.java
    private JTextArea detailsTextArea;
    private JTable table;
=======
    private JButton newReservation;
>>>>>>> main:Titanic-Project/src/main/java/edu/ui/travelAgentEditReservations/GuestsWithReservationPage.java

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

<<<<<<< HEAD:Titanic-Project/src/main/java/edu/ui/editReservation/GuestsWithReservationPage.java
        //List<Guest> guestsWithReservations = GuestsWithReservationController.getGuestsWithReservations();
=======
        List<Guest> guestsWithReservations = null;

        try {
            guestsWithReservations = GuestsWithReservationController.getGuestsWithReservations();
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
>>>>>>> main:Titanic-Project/src/main/java/edu/ui/travelAgentEditReservations/GuestsWithReservationPage.java

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));

        /*for (int i = 0; i < guestsWithReservations.size(); i++) {
            // get a guest from the list
            Guest guest = guestsWithReservations.get(i);

            String username = guest.getUsername();
            String first = guest.getFirstName();
            String last = guest.getLastName();
            String numReservations = Integer.toString(guest.getReservations().size());

            String guestDetails = "Guest username: " + username + "\n" +
                    "First name: " + first + "\n" +
                    "Last name: " + last + "\n" +
                    "Reservations: " + numReservations +" \n";

            JTextArea detailsTextArea = new JTextArea(guestDetails);
            detailsTextArea.setEditable(false);
            JScrollPane textScrollPane = new JScrollPane(detailsTextArea);
            detailsPanel.add(textScrollPane);

            JButton selectButton = new JButton("Select " + guest.getUsername());
            selectButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            selectButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    handleReservationList(guest);
                }
            });
            detailsPanel.add(selectButton);
        }*/

        //mainPanel.add(detailsPanel, BorderLayout.CENTER);
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

<<<<<<< HEAD:Titanic-Project/src/main/java/edu/ui/editReservation/GuestsWithReservationPage.java
=======
        newReservation = new JButton("Create a new reservation");
        newReservation.addActionListener(e -> createReservation());

        JPanel buttonPanel = new JPanel();
>>>>>>> main:Titanic-Project/src/main/java/edu/ui/travelAgentEditReservations/GuestsWithReservationPage.java
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
        refreshRooms();

        //mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
    }

    public void refreshRooms() {
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
        List<Guest> guestsWithReservations = GuestsWithReservationController.getGuestsWithReservations();
        //GuestsWithReservationController controller = new GuestsWithReservationController();

        //Guest r;
        int selectedRowForDeletion;
        selectedRowForDeletion = roomTable.getSelectedRow();

        //check if a row is selected
        if (selectedRowForDeletion >= 0) {
            /*int modelRow = roomTable.convertRowIndexToModel(selectedRowForDeletion);
            DefaultTableModel model = (DefaultTableModel) roomTable.getModel();*/
            handleReservationList(guestsWithReservations.get(selectedRowForDeletion));
            //you can't cast the object to an int in case it's null, so you have to cast to string, then cast to int
            /*String row = (String) model.getValueAt(modelRow, 1);
            int test = Integer.parseInt(row);*/

            /*int r = GuestsWithReservationController.getNumberOfReservations();
            if (r > 0) {
                controller.reserveRoom(CurrentGuest.getCurrentGuest(), r);
                JOptionPane.showMessageDialog(mainFrame, "Room " + r.getRoomNumber() + " reserved.");
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Please select a room first.");
            }*/


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
