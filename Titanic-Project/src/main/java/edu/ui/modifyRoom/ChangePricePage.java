package edu.ui.modifyRoom;

import edu.core.reservation.Room;

import javax.swing.*;
import java.awt.*;

public class ChangePricePage {
    private JLabel titleLabel;


    //modify roomNumber(if doesnt exist), number of beds, bedType, smoking available, and price

    private JList<Room> roomList;
    private JButton backButton;
    private JButton selectRoomButton;

    public ChangePricePage(String roomNumber) {
        prepareGUI(roomNumber);
    }

    public static void main(String[] args){
        ChangePricePage test = new ChangePricePage("123");
    }


    private void prepareGUI(String room) {
        DetailOptionsController controller = new DetailOptionsController();
        JFrame frame = new JFrame("Modify room price");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        JLabel label = new JLabel("Enter Price: ");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 10, 10, 10);
        panel.add(label, constraints);


        JTextField newRoomField = new JTextField(20);
        JButton submitButton = new JButton("Submit");
        panel.add(newRoomField);
        constraints.gridx = 1; // Place the button in column 0
        constraints.gridy = 1; // Place the button in row 1
        panel.add(submitButton, constraints);
        frame.add(panel);

        frame.setVisible(true);
    }
}
