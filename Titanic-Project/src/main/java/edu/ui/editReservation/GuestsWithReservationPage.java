package edu.ui.editReservation;

import edu.core.cruise.Cruise;
import edu.core.reservation.Room;
import edu.core.users.Guest;
import edu.core.users.GuestSearch;
import edu.ui.landingPage.LandingPage;
import edu.ui.landingPage.TravelAgentLandingPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JList<String> guestList;
    private JButton selectButton, searchButton;
    private JButton backButton;
    private JTextArea detailsTextArea;
    private JTextField searchTextField;
    private JMenuBar searchMenu;
    private GuestSearch guestSearch;
    JPanel mainPanel, detailsPanel;



    public GuestsWithReservationPage(TravelAgentLandingPage landingPage) {
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

        List<Guest> guestsWithReservations = GuestsWithReservationController.getGuestsWithReservations();
        guestSearch = new GuestSearch(guestsWithReservations);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        generateSearchMenu();

        updateGuestList(guestsWithReservations);
        /*
        detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));

        for (int i = 0; i < guestsWithReservations.size(); i++) {
            // get a guest from the list
            Guest guest = guestsWithReservations.get(i);

            String username = guest.getUsername();
            String first = guest.getFirstName();
            String last = guest.getLastName();
            String numReservations = Integer.toString(guest.getReservations().size());

            String guestDetails = "Guest username: " + username + "\n" +
                    "First name: " + first + "\n" +
                    "Last name: " + last + "\n" +
                    "Reservations: " + numReservations + " \n";

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

        mainPanel.add(detailsPanel, BorderLayout.CENTER);

        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            mainFrame.dispose(); // Close the Edit Reservation page
            landingPage.show(); // Go back to the landingPage
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainFrame.add(titlePanel,BorderLayout.NORTH);
        mainFrame.setJMenuBar(searchMenu);
        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
    }

    private void handleReservationList(Guest guest) {
        mainFrame.setVisible(false);
        new ReservationListPage(this, guest);
    }

    public void show() {
        mainFrame.setVisible(true);
    }

    private void generateSearchMenu() {
        searchMenu = new JMenuBar();
        searchMenu.setPreferredSize(new Dimension(1000, 25));
        searchTextField = new JTextField();
        searchButton = new JButton("search");

        searchButton.addActionListener(e -> {
            List<Guest> list = guestSearch.findGuests(searchTextField.getText());
            System.out.println(list.size()+": ");

            mainPanel.remove(detailsPanel);
            updateGuestList(list);

            mainPanel.add(detailsPanel, BorderLayout.CENTER);
            mainPanel.repaint();
            mainPanel.revalidate();
            mainFrame.revalidate();
            mainFrame.repaint();
        });

        searchMenu.add(searchTextField);
        searchMenu.add(searchButton);
    }
    private void updateGuestList(List<Guest> guestsWithReservations){
        detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));

        for (int i = 0; i < guestsWithReservations.size(); i++) {
            // get a guest from the list
            Guest guest = guestsWithReservations.get(i);

            String username = guest.getUsername();
            String first = guest.getFirstName();
            String last = guest.getLastName();
            String numReservations = Integer.toString(guest.getReservations().size());

            String guestDetails = "Guest username: " + username + "\n" +
                    "First name: " + first + "\n" +
                    "Last name: " + last + "\n" +
                    "Reservations: " + numReservations + " \n";

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
        }
    }

}
