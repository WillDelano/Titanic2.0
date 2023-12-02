package edu.ui.travelAgentAddRoom;

import edu.core.reservation.Room;
import edu.databaseAccessors.RoomDatabase;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * The class for the add room ui
 *
 * This class creates the add room page and calls the controller to implement functionality
 *
 * @author William Delano
 * @version 1.1
 */
public class AddRoomPage {
    private JFrame frame;
    private JLabel titleLabel;
    private JPanel mainPanel;
    private JLabel smokingLabel;
    private JRadioButton smokingYes;
    private JRadioButton smokingNo;
    private JLabel bedTypeLabel;
    private JLabel bedNumberLabel;
    private JComboBox<String> bedTypeBox;
    private JComboBox<String> bedNumberBox;
    private JLabel cruiseLabel;
    private JComboBox<String> cruiseComboBox;
    private JLabel priceLabel;
    private JTextField priceField;
    private JButton submitButton;

    /**
     * Operation to call the creation of the GUI
     *
     */
    public AddRoomPage() {
        createGUI();
    }

    /**
     * Operation to create the GUI, list the available data, and implement functionality
     *
     */
    private void createGUI() {
        frame = new JFrame("Add Room");
        frame.setSize(400, 300);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(7, 3, 15, 20));

        titleLabel = new JLabel("Add Room", JLabel.CENTER);

        smokingLabel = new JLabel("Smoking:");
        smokingYes = new JRadioButton("Yes");
        smokingNo = new JRadioButton("No");
        ButtonGroup smokingGroup = new ButtonGroup();
        smokingGroup.add(smokingYes);
        smokingGroup.add(smokingNo);

        bedTypeLabel = new JLabel("Bed Type:");
        String[] bedTypes = {"Single", "Double", "King", "Queen"};
        bedTypeBox = new JComboBox<>(bedTypes);

        bedNumberLabel = new JLabel("Number of Beds:");
        String[] bedNumbers = {"1", "2", "3", "4"};
        bedNumberBox = new JComboBox<>(bedNumbers);

        cruiseLabel = new JLabel("Cruise:");
        List<String> cruiseList = AddRoomController.getCruises();
        String[] cruises = cruiseList.toArray(new String[0]);
        cruiseComboBox = new JComboBox<>(cruises);

        priceLabel = new JLabel("Price:");
        priceField = new JTextField();

        submitButton = new JButton("Submit");

        //top row
        mainPanel.add(new JLabel());
        mainPanel.add(titleLabel);
        mainPanel.add(new JLabel());

        //second row
        mainPanel.add(smokingLabel);
        mainPanel.add(smokingYes);
        mainPanel.add(smokingNo);

        //third row
        mainPanel.add(bedTypeLabel);
        mainPanel.add(bedTypeBox);
        mainPanel.add(new JLabel());

        //fourth row
        mainPanel.add(bedNumberLabel);
        mainPanel.add(bedNumberBox);
        mainPanel.add(new JLabel());

        //fifth row
        mainPanel.add(cruiseLabel);
        mainPanel.add(cruiseComboBox);
        mainPanel.add(new JLabel());

        //sixth row
        mainPanel.add(priceLabel);
        mainPanel.add(priceField);
        mainPanel.add(new JLabel());

        //last row
        mainPanel.add(new JLabel());
        mainPanel.add(submitButton);
        mainPanel.add(new JLabel());

        frame.add(mainPanel);
        frame.setVisible(true);

        submitButton.addActionListener(e -> {
            boolean smokingAvailable = smokingYes.isSelected();
            String bedType = (String) bedTypeBox.getSelectedItem();
            int bedNumber = Integer.parseInt((String) Objects.requireNonNull(bedNumberBox.getSelectedItem()));
            String cruise = (String) cruiseComboBox.getSelectedItem();
            double price = Double.parseDouble(priceField.getText());

            int roomNumber;

            //if the room number already exists, get a new unique id
            do {
                roomNumber = new Random().nextInt(1000) + 1;
            } while (RoomDatabase.getRoom(roomNumber).getRoomNumber() != -1);

            addRoomToSystem(new Room(roomNumber, bedNumber, bedType, smokingAvailable, price, cruise));

            JOptionPane.showMessageDialog(mainPanel, "Room created.");
        });
    }

    /**
     * Operation to call the controller for adding a room
     *
     * @param room Room to add to the database
     *
     */
    private void addRoomToSystem(Room room) {
        new AddRoomController();

        AddRoomController.addRoomToSystem(room);
    }

    /**
     * The function that sets the frame to visible
     *
     */
    public void show() {
        frame.setVisible(true);
    }

    /**
     * Main function to allow testability of the page on its own
     *
     * @param args *Not to be used*
     *
     */
    public static void main(String[] args) {
        new AddRoomPage();
    }
}
