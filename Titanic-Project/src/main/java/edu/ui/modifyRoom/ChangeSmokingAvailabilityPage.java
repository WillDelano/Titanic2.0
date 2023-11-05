package edu.ui.modifyRoom;

import edu.core.reservation.Room;

import javax.swing.*;
import java.awt.*;

public class ChangeSmokingAvailabilityPage {
    private JLabel titleLabel;


    //modify roomNumber(if doesnt exist), number of beds, bedType, smoking available, and price

    private JList<Room> roomList;
    private JButton backButton;
    private JButton selectRoomButton;

    public ChangeSmokingAvailabilityPage(String roomNumber) {
        prepareGUI(roomNumber);
    }

    public static void main(String[] args){
        ChangeSmokingAvailabilityPage test = new ChangeSmokingAvailabilityPage("123");
    }


    private void prepareGUI(String room) {
        DetailOptionsController controller = new DetailOptionsController();
        JFrame frame = new JFrame("Modify Availibility");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        JLabel label = new JLabel("Smoking Status : ");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 10, 10, 10);
        panel.add(label, constraints);

        // Create Radio buttons
        ButtonGroup bg = new ButtonGroup();
        JRadioButton rb1 = new JRadioButton("Smoking", true);
        JRadioButton rb2 = new JRadioButton("No-Smoking", false);
        bg.add(rb1);
        bg.add(rb2);

        panel.add(rb1);
        panel.add(rb2);


       // JTextField newRoomField = new JTextField(20);
        JButton submitButton = new JButton("Submit");
       // panel.add(newRoomField);
        constraints.gridx = 1; // Place the button in column 0
        constraints.gridy = 1; // Place the button in row 1
        panel.add(submitButton, constraints);
        frame.add(panel);
        frame.pack();

        frame.setVisible(true);
    }
}
