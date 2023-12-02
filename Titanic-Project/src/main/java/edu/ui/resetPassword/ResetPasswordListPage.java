package edu.ui.resetPassword;

import edu.core.cruise.Cruise;
import edu.core.users.Guest;
import edu.core.users.User;
import edu.ui.editProfile.EditProfile;
import edu.ui.landingPage.LandingPage;
import edu.ui.landingPage.TravelAgentLandingPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;

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

        for (int i = 0; i < userList.size(); i++) {
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
//            selectButton.addActionListener(new ActionListener() {
//                public void actionPerformed(ActionEvent e) {
//                    navigateToResetPassword(user);
//                }
//            });

            detailsPanel.add(selectButton);
        }

        mainPanel.add(detailsPanel, BorderLayout.CENTER);

        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            mainFrame.dispose(); // Close the Edit Reservation page
            landingPage.show(); // Go back to the landingPage
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
    }

//    private void navigateToResetPassword(User user) {
//        mainFrame.setVisible(false);
//        new EditProfile(user, null, this, false);
//    }

    public void show() {
        mainFrame.setVisible(true);
    }
}
