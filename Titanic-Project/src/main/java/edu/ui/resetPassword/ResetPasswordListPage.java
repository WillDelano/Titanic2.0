package edu.ui.resetPassword;

import edu.core.cruise.Cruise;
import edu.core.reservation.Reservation;
import edu.core.users.CurrentGuest;
import edu.core.users.Guest;
import edu.core.users.User;
import edu.databaseAccessors.AccountDatabase;
import edu.exceptions.NoMatchingReservationException;
import edu.ui.editProfile.EditProfile;
import edu.ui.landingPage.LandingPage;
import edu.ui.landingPage.TravelAgentLandingPage;
import edu.ui.reservationDetails.MyReservationsPageController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Controller for displaying and selecting a cruise on the ui
 *
 * This class allows a user to browse and select cruises
 *
 * @author Vincent Dinh
 * @version 1.0
 * @see Cruise, CruiseDatabase, CruiseDetailsPage
 */
public class ResetPasswordListPage {
    private LandingPage landingPage;
    private JFrame mainFrame;
    private JLabel titleLabel;
    private JList<String> guestList;
    private JButton selectButton;
    private JButton backButton;
    private JTextArea detailsTextArea;
    private JTable userTable;
    private JPanel contentPanel;

    public ResetPasswordListPage(LandingPage landingPage) {
        this.landingPage = landingPage;
        prepareGUI();
    }

    private void prepareGUI() {
        mainFrame = new JFrame("Choose an Account");
        mainFrame.setSize(1000, 700);
        mainFrame.setLayout(new BorderLayout());

        titleLabel = new JLabel("Users", JLabel.CENTER);
        mainFrame.add(titleLabel, BorderLayout.NORTH);

        List<User> userList = ResetPasswordListPageController.getAllUsers();

        System.out.println(userList.size());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));

        /*for (int i = 0; i < userList.size(); i++) {
            // get a guest from the list
            User user = userList.get(i);
            String firstname;
            String lastname;

            //if the user to display is a travel agent that hasn't finished their account
            if (Objects.equals(user.getFirstName(), "")) {
                firstname = "NEW TRAVEL AGENT";
                lastname = firstname;
            }
            else {
                firstname = user.getUsername();
                lastname = user.getLastName();
            }

            String details = "Username: " + user.getUsername() + "\n" +
                    "First name: " + firstname + "\n" +
                    "Last name: " + lastname + "\n";

            JTextArea detailsTextArea = new JTextArea(details);
            detailsTextArea.setEditable(false);
            JScrollPane textScrollPane = new JScrollPane(detailsTextArea);
            detailsPanel.add(textScrollPane);

            JButton selectButton = new JButton("Select " + user.getUsername());
            selectButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            selectButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    navigateToResetPassword(user);
                }
            });

            detailsPanel.add(selectButton);
        }*/

        //mainPanel.add(detailsPanel, BorderLayout.CENTER);

        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            mainFrame.dispose(); // Close the Edit Reservation page
            landingPage.show(); // Go back to the landingPage
        });

        JButton select = new JButton("Select");
        select.addActionListener(e -> {
            selectRow(userTable);
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(select);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);


        userTable = new JTable();
        userTable.setAutoCreateRowSorter(true);
        userTable.setFillsViewportHeight(true);

        mainPanel.add(new JScrollPane(userTable), BorderLayout.CENTER);

        mainFrame.add(mainPanel);

        refreshUsers();
        mainFrame.setVisible(true);
    }

    private void selectRow(JTable table) {
        User u;
        int selectedRowForDeletion;
        selectedRowForDeletion = table.getSelectedRow();

        //check if a row is selected
        if (selectedRowForDeletion >= 0) {
            //int modelRow = table.convertRowIndexToModel(selectedRowForDeletion);
            //DefaultTableModel model = (DefaultTableModel) table.getModel();

            //you can't cast the object to an int in case it's null, so you have to cast to string, then cast to int
            u = AccountDatabase.getUser(table.getValueAt(selectedRowForDeletion, 1).toString());

            navigateToResetPassword(u);
            mainFrame.setVisible(false);
        }
        else {
            JOptionPane.showMessageDialog(null, "No user is selected.");
        }
    }

    public void refreshUsers() {
        List<User> userList = ResetPasswordListPageController.getAllUsers();

        for (User q : userList) {
            System.err.println("\t" + q.getUsername());
        }

        int numUsers = userList.size();
        String[][] data = new String[numUsers][4];
        int i = 0;

        for (User temp : userList) {
            data[i][0] = String.valueOf(i + 1);
            data[i][1] = String.valueOf(temp.getUsername());
            if (Objects.equals(temp.getFirstName(), "")) {
                data[i][2] = "NEW TRAVEL AGENT";
                data[i][3] = "NEW TRAVEL AGENT";
            }
            else {
                data[i][2] = String.valueOf(temp.getFirstName());
                data[i][3] = String.valueOf(temp.getLastName());
            }
            i++;
        }

        String[] columnNames = {"#", "Username", "First Name", "Last Name"};
        userTable.setModel(new DefaultTableModel(data, columnNames));

        TableColumnModel columnModel = userTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(1);
        columnModel.getColumn(1).setPreferredWidth(100);
    }

    private void navigateToResetPassword(User user) {
        mainFrame.setVisible(false);
        new EditProfile(user, null, this, false);
    }

    public void show() {
        mainFrame.setVisible(true);
    }
}
