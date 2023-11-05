package edu.ui.modifyRoom;

import edu.core.reservation.Room;

import javax.swing.*;
import java.awt.*;

public class ChangeRoomNumberPage {
    private JFrame roomChangeFrame;
    private JLabel titleLabel;


    //modify roomNumber(if doesnt exist), number of beds, bedType, smoking available, and price

    private JList<Room> roomList;
    private JButton backButton;
    private JButton selectRoomButton;

    public ChangeRoomNumberPage(String roomNumber) {
        prepareGUI(roomNumber);
    }

    public static void main(String[] args){
        ChangeRoomNumberPage test = new ChangeRoomNumberPage("123");
    }


    private void prepareGUI(String room) {
        DetailOptionsController controller = new DetailOptionsController();
        JFrame frame = new JFrame("Modify Room Number");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        JLabel label = new JLabel("Enter New Room Number: ");

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

