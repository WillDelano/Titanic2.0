package edu.ui.modifyRoom;

import edu.core.reservation.Room;

import javax.swing.*;
import java.awt.*;

public class ChangeNumberOfBedsPage {
    private JFrame roomChangeFrame;
    private JLabel titleLabel;


    //modify roomNumber(if doesnt exist), number of beds, bedType, smoking available, and price

    private JList<Room> roomList;
    private JButton backButton;
    private JButton selectRoomButton;

    public ChangeNumberOfBedsPage(String roomNumber) {
        prepareGUI(roomNumber);
    }

    public static void main(String[] args){
        ChangeNumberOfBedsPage test = new ChangeNumberOfBedsPage("123");
    }


    private void prepareGUI(String room) {
        DetailOptionsController controller = new DetailOptionsController();
        JFrame frame = new JFrame("Modify Bed Count");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(250, 100);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        JLabel label = new JLabel("Enter Number of Beds: ");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 10, 10, 10);
        panel.add(label, constraints);

        // Set data in the drop-down list
        String[] numberOfBeds = {"1", "2", "3", "4", "5"};

        // Create combobox
        JComboBox cb = new JComboBox(numberOfBeds);
        panel.add(cb);


        JButton submitButton = new JButton("Submit");
        constraints.gridx = 1; // Place the button in column 0
        constraints.gridy = 1; // Place the button in row 1
        panel.add(submitButton, constraints);
        frame.add(panel);
        frame.pack();


        frame.setVisible(true);
    }
}
