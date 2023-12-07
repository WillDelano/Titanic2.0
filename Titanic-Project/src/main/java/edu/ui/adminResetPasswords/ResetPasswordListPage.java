package edu.ui.adminResetPasswords;

import edu.core.cruise.Cruise;
import edu.core.users.User;
import edu.ui.editProfile.EditProfile;
import edu.ui.landingPage.LandingPage;

import javax.swing.*;
import edu.databaseAccessors.AccountDatabase;
import edu.exceptions.NoMatchingReservationException;
import edu.ui.editProfile.EditProfile;
import edu.ui.landingPage.LandingPage;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;

/**
 * UI class for displaying a list of users and allowing the selection of a user to reset their password.
 *
 * <p>
 * This class provides a graphical user interface to view a list of users, including their usernames,
 * first names, and last names. It allows the selection of a user to initiate the password reset process.
 * The class interacts with the {@link ResetPasswordListPageController} for user-related operations.
 * </p>
 *
 * @version 1.0
 * @see ResetPasswordListPageController
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

    /**
     * Constructor for the ResetPasswordListPage class.
     *
     * @param landingPage The LandingPage instance to navigate back.
     */
    public ResetPasswordListPage(LandingPage landingPage) {
        this.landingPage = landingPage;
        prepareGUI();
    }

    /**
     * Prepares the graphical user interface for displaying a list of users and initiating password reset.
     */
    private void prepareGUI() {
        mainFrame = new JFrame("Choose an Account");
        mainFrame.setSize(1000, 700);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        titleLabel = new JLabel("Users", JLabel.CENTER);
        mainFrame.add(titleLabel, BorderLayout.NORTH);

        List<User> userList = ResetPasswordListPageController.getAllUsers();

        System.out.println(userList.size());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));

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

    /**
     * Handles the selection of a row in the user table and initiates the password reset process.
     *
     * @param table The JTable representing the list of users.
     */
    private void selectRow(JTable table) {
        User u = null;
        int selectedRowForDeletion;
        selectedRowForDeletion = table.getSelectedRow();

        //check if a row is selected
        if (selectedRowForDeletion >= 0) {
            //int modelRow = table.convertRowIndexToModel(selectedRowForDeletion);
            //DefaultTableModel model = (DefaultTableModel) table.getModel();

            //you can't cast the object to an int in case it's null, so you have to cast to string, then cast to int
            try {
                u = AccountDatabase.getUser(table.getValueAt(selectedRowForDeletion, 1).toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

            navigateToResetPassword(u);
            mainFrame.setVisible(false);
        }
        else {
            JOptionPane.showMessageDialog(null, "No user is selected.");
        }
    }

    /**
     * Refreshes the displayed list of users in the user table.
     */
    public void refreshUsers() {
        List<User> userList = ResetPasswordListPageController.getAllUsers();

        System.out.println("Users:");
        for (User q : userList) {
            System.out.println("\t" + q.getUsername());
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

    /**
     * Navigates to the EditProfile page for the selected user to reset their password.
     *
     * @param user The selected User instance for password reset.
     */
    private void navigateToResetPassword(User user) {
        mainFrame.setVisible(false);
        new EditProfile(user, null, this, false);
    }

    /**
     * Displays the ResetPasswordListPage frame and refreshes the list of users.
     */
    public void show() {
        refreshUsers();
        mainFrame.setVisible(true);
    }

    /**
     * The main method to create an instance of the ResetPasswordListPage class.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        new ResetPasswordListPage(null);
    }
}
