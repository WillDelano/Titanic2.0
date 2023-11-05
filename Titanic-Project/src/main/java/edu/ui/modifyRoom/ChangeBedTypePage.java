package edu.ui.modifyRoom;

import edu.core.reservation.Room;

import javax.swing.*;
import java.awt.*;

public class ChangeBedTypePage {
    private JLabel titleLabel;


    //modify roomNumber(if doesnt exist), number of beds, bedType, smoking available, and price

    private JList<Room> roomList;
    private JButton backButton;
    private JButton selectRoomButton;

    public ChangeBedTypePage(String roomNumber) {
        prepareGUI(roomNumber);
    }

    public static void main(String[] args){
        ChangeBedTypePage test = new ChangeBedTypePage("123");
    }


    private void prepareGUI(String room) {
        DetailOptionsController controller = new DetailOptionsController();
        JFrame frame = new JFrame("Modify Bed Type");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        JLabel label = new JLabel("Enter new bed type: ");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 10, 10, 10);
        panel.add(label, constraints);


        // Set data in the drop-down list
        String[] numberOfBeds = {"Twin", "Full", "Queen", "King"};

        // Create combobox
        JComboBox cb = new JComboBox(numberOfBeds);
        panel.add(cb);


        JButton submitButton = new JButton("Submit");
        constraints.gridx = 1; // Place the button in column 0
        constraints.gridy = 1; // Place the button in row 1
        panel.add(submitButton, constraints);
        frame.add(panel);

        frame.setVisible(true);
    }
}
