package edu.ui.modifyRoom;

import edu.core.reservation.Room;
import edu.ui.editReservation.GuestsWithReservationPage;

import javax.swing.*;
import java.awt.*;

public class DetailOptionsLandingPage {
    private JFrame frame;
    private JLabel titleLabel;


    //modify roomNumber(if doesnt exist), number of beds, bedType, smoking available, and price

    private JList<Room> roomList;
    private JButton backButton;
    private JButton selectRoomButton;

    public DetailOptionsLandingPage(String roomNumber) {
        prepareGUI(roomNumber);
    }

    public static void main(String[] args){
        DetailOptionsLandingPage  test = new DetailOptionsLandingPage("123");
    }

    public void navigateToChangeRoomNumber(String num){
        frame.setVisible(false);   // hide the current landing page
        new ChangeRoomNumberPage(num);
    }

    public void navigateToChangeBedType(String num){
        frame.setVisible(false);   // hide the current landing page
        new ChangeBedTypePage(num);
    }

    public void navigateToChangeNumberOfBeds(String num){
        frame.setVisible(false);   // hide the current landing page
        new ChangeNumberOfBedsPage(num);
    }

    public void navigateToChangeSmokingAvailability(String num){
        frame.setVisible(false);   // hide the current landing page
        new ChangeSmokingAvailabilityPage(num);
    }

    public void navigateToChangePrice(String num){
        frame.setVisible(false);   // hide the current landing page
        new ChangePricePage(num);
    }


    private void prepareGUI(String room) {
        DetailOptionsController controller = new DetailOptionsController();
        frame = new JFrame("Modify Room Options");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        JPanel panel = new JPanel();

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Use BoxLayout with vertical alignment


        JButton roomNumButton = new JButton("Room Number");
        roomNumButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        roomNumButton.addActionListener(e -> navigateToChangeRoomNumber(room));
       /* browseCruisesButton.addActionListener(e -> navigateToEditReservations());*/

        JButton  numBedsButton = new JButton("Number of Beds");
        numBedsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        numBedsButton.addActionListener(e -> navigateToChangeNumberOfBeds(room));

        JButton bedTypeButton = new JButton("Bed Type");
        bedTypeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        bedTypeButton.addActionListener(e -> navigateToChangeBedType(room));

        JButton smokingAvailableButton = new JButton("Smoking Availability");
        smokingAvailableButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        smokingAvailableButton.addActionListener(e -> navigateToChangeSmokingAvailability(room));

        JButton priceButton = new JButton("Price");
        priceButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        priceButton.addActionListener(e -> navigateToChangePrice(room));



        panel.add(Box.createVerticalGlue());
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Add 10 pixels of space
        panel.add(roomNumButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Add 10 pixels of space
        panel.add(numBedsButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Add 10 pixels of space
        panel.add(bedTypeButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Add 10 pixels of space
        panel.add(smokingAvailableButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Add 10 pixels of space
        panel.add(priceButton);
      panel.add(Box.createVerticalGlue());


        frame.add(panel);
       /* JPanel panel = new JPanel();
        frame.add(panel);

        SpringLayout layout = new SpringLayout();
        panel.setLayout(layout);

        // Create an array to hold labels and text fields
        JLabel[] labels = new JLabel[6];
        JTextField[] textFields = new JTextField[6];


        SpringLayout.Constraints lastLabelConstraints = null; // To track the constraints of the previous label

        for (int i = 0; i < 6; i++) {
            labels[i] = new JLabel("Label " + (i + 1) + ":");
            textFields[i] = new JTextField(5); // Adjust the width of the text fields as needed

            panel.add(labels[i]);
            panel.add(textFields[i]);

            SpringLayout.Constraints labelConstraints = layout.getConstraints(labels[i]);
            SpringLayout.Constraints textFieldConstraints = layout.getConstraints(textFields[i]);

            if (i == 0) {
                labelConstraints.setX(Spring.constant(5)); // Horizontal position for the first label
                labelConstraints.setY(Spring.constant(5)); // Vertical position for the first label
            } else {
                labelConstraints.setX(Spring.constant(5)); // Horizontal position for subsequent labels
                labelConstraints.setY(Spring.sum(Spring.constant(5), lastLabelConstraints.getConstraint(SpringLayout.SOUTH))); // Vertical position below the previous label
            }

            textFieldConstraints.setX(Spring.sum(Spring.constant(5), labelConstraints.getConstraint(SpringLayout.EAST))); // Horizontal position for text field
            textFieldConstraints.setY(labelConstraints.getConstraint(SpringLayout.NORTH)); // Vertical position align with the label

            lastLabelConstraints = labelConstraints;
        }

        // Set the frame size based on the content
        SpringLayout.Constraints panelConstraints = layout.getConstraints(panel);
        panelConstraints.setConstraint(SpringLayout.EAST, Spring.sum(Spring.constant(5), layout.getConstraints(textFields[5]).getConstraint(SpringLayout.EAST)));
*/

        frame.setVisible(true);
    }


}
