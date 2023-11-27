package edu.ui.landingPage;

import edu.core.reservation.Room;
import edu.core.users.TravelAgent;
import edu.core.users.User;
import edu.databaseAccessors.AccountDatabase;
import edu.databaseAccessors.RoomDatabase;
import edu.ui.addRoom.AddRoomPage;
import edu.ui.editProfile.EditProfile;
import edu.ui.editReservation.GuestsWithReservationPage;
import edu.ui.modifyRoom.EditRoomPage;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;

/**
 * Creates the landing page
 *
 * This class creates the landing page for the cruise reservation application
 *
 * @author William Delano
 * @version 1.0
 * @see LandingPageController
 */
public class TravelAgentLandingPage extends LandingPage {

    private JFrame mainFrame;
    private JPanel headerPanel;
    private JLabel headerLabel;
    private User account;

    private Room room;

    /**
     * Constructor for the landing page that creates the GUI
     *
     */
    public TravelAgentLandingPage() {
        prepareGUI();
    }

    /**
     * Creates the GUI for the landing page
     *
     */
    private void prepareGUI() {
        mainFrame = new JFrame("Cruise Reservation Application");
        mainFrame.setSize(1000, 700);
        mainFrame.setLayout(new BorderLayout());

        headerLabel = new JLabel("", JLabel.CENTER);
        headerPanel = new JPanel(new GridLayout(2, 5));

        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel middlePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton browseCruisesButton = new JButton("View/Edit Reservations");
        browseCruisesButton.addActionListener(e -> {
            try {
                navigateToEditReservations();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        JButton myReservationsButton = new JButton("Add Rooms");
        myReservationsButton.addActionListener(e -> navigateToAddRooms());

        JButton editRoomButton = new JButton("Edit Rooms");
        myReservationsButton.addActionListener(e ->
                 askRoom());

        JButton supportButton = new JButton("Edit Profile");
        supportButton.addActionListener(e -> navigateToEditProfile());



        headerPanel.add(browseCruisesButton);
        headerPanel.add(myReservationsButton);
        headerPanel.add(editRoomButton);
        headerPanel.add(supportButton, BorderLayout.EAST);

        headerPanel.add(topPanel);
        headerPanel.add(new JPanel());
        headerPanel.add(middlePanel);

        mainFrame.add(headerLabel, BorderLayout.CENTER);
        mainFrame.add(headerPanel, BorderLayout.NORTH);
        mainFrame.setVisible(true);
    }

    /**
     * Sets the landing page to visible and displays the center text with a user's name and email
     *
     * @param account The user who is logged in
     */
    public void showLandingPage(User account) {
        this.account = account;

        String name = account.getFirstName() + " " + account.getLastName();
        int count = AccountDatabase.getUserCount();

        headerLabel.setText(String.format("<html>" +
                "<div style='text-align: center; font-size: 24px;'>Cruise Reservation</div>" +
                "<div style='text-align: center; font-size: 11px;'>Logged in as travel agent %s</div>" +
                "<div style='text-align: center;'>%s</div></html>", name, "Total Account Population is " + count));

        mainFrame.setVisible(true);
    }

    /**
     * Loads an image from the internet
     *
     * @param path URL to get the image from
     */
    private ImageIcon createImageIcon(String path) {
        try {
            URL imgURL = new URL(path);
            if (imgURL != null) {
                return new ImageIcon(new ImageIcon(imgURL).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
            } else {
                System.err.println("Couldn't find file: " + path);
                return null;
            }
        } catch (MalformedURLException e) {
            return null;
        }
    }
    private void navigateToEditReservations() throws SQLException {
        mainFrame.setVisible(false);   // hide the current landing page
        new GuestsWithReservationPage(this);
    }

    private void navigateToAddRooms() {
        //does not pass the landing page instance because it is a pop-up
        new AddRoomPage();
    }

    private void navigateToModifyRooms(Room room){
        //get specified room in room database then modify it if necessary
        mainFrame.setVisible(false);
        RoomDatabase roomList = new RoomDatabase();
        //Fixme: when editroom page is fully implemeneted, error will go away
        new EditRoomPage(room,this);

    }

    private void navigateToEditProfile() {
        mainFrame.setVisible(false);   // hide the current landing page
        new EditProfile(account, this);
    }

    private void askRoom(){
        RoomDatabase roomList = new RoomDatabase();
        JTextField roomConfirmation = new JTextField();

        int option = JOptionPane.showConfirmDialog(mainFrame,roomConfirmation,"Room Number",
                JOptionPane.OK_CANCEL_OPTION);

        if(option == JOptionPane.OK_OPTION){
            String roomChoice = roomConfirmation.getText();

            //if room number is invalid

            //fixme: when isvalidRoom is created in Room Database then this will work
            if(!roomList.isValidRoom(Integer.parseInt(roomChoice))){
                invalidDecision();
                mainFrame.dispose();
                prepareGUI();
                return;
            }
            else{
                //get room from database then use this to pass to edit room page

                //Fixme: make a functional getRoom that takes in a room number
                room = roomList.getRoom(Integer.parseInt(roomChoice));
                navigateToModifyRooms(room);
            }
        }
        else{
            //if cancel is selected then back to main page
            mainFrame.dispose();
            prepareGUI();
            return;
        }
    }

    public void invalidDecision(){
        JOptionPane.showMessageDialog(mainFrame
                , "Invalid Input for Room Modification", "Error!", JOptionPane.OK_OPTION);
    }

    public void show() {
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new TravelAgentLandingPage();
    }
}
