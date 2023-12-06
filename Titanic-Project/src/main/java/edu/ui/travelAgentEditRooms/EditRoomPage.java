package edu.ui.travelAgentEditRooms;

import edu.core.reservation.Room;
import edu.core.users.User;

import javax.swing.*;
import java.awt.*;

/**
 * GUI class for editing room details.
 * Allows the travel agent to modify the information of a specific room.
 * Provides options for updating bed type, number of beds, smoking availability, and price.
 * Displays the current details of the room and prompts the user for confirmation before making changes.
 * Provides feedback on invalid inputs and handles the decision-making process.
 * Utilizes the {@link EditRoomController} for updating room information in the database.
 *
 * @author Michael Okonkwo
 * @version 1.0
 * @see Room, User, LandingPage, ViewAllRoomsPage, EditRoomController
 */
public class EditRoomPage {
    private JFrame frame;
    private JLabel titleLabel;
    private JPanel mainPanel;
    private JLabel bedTypeLabel;
    private JComboBox bedTypeCombo;
    private JLabel numOfBedsLabel;
    private  JComboBox numOfBedsCombo;
    private JLabel smokingLabel;
    private    JRadioButton smokingYes;
    private  JRadioButton smokingNo;
    private JLabel priceLabel;
    private JTextField priceField;
    private JButton submitButton;
    private User account;
    private static ViewAllRoomsPage previousPage;
    private static Room room;


    /**
     * Constructor for the EditRoomPage class.
     *
     * @param room      The room to be edited.
     * @param prevPage  The previous page to return to after editing.
     */
    public EditRoomPage(Room room, ViewAllRoomsPage prevPage) {
        this.previousPage = prevPage;
        this.room = room;
        createGUI();
    }

    /**
     * The main method used for testing the EditRoomPage class.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[]args){
        boolean tester = true;
        EditRoomPage testing = new EditRoomPage(new Room(101, 2, "Queen", false, 250, "Caribbean Adventure"),previousPage);
    }

    /**
     * Creates the graphical user interface (GUI) for editing room details.
     * Sets up components such as labels, combo boxes, buttons, and event listeners.
     * Handles user input, validates decisions, and updates the room information.
     */
    private void createGUI() {
        frame = new JFrame("Edit Room");
        frame.setSize(600, 400);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(8, 3, 15, 20));

        /*String firstName = account.getFirstName();
        String lastName = account.getLastName();
        titleLabel = new JLabel(firstName + " " + lastName, JLabel.CENTER);*/

        Integer i  = room.getRoomNumber();
        String roomNumber = i.toString();
        titleLabel = new JLabel("Room "+roomNumber+" Details:", SwingConstants.CENTER);

        bedTypeLabel = new JLabel("Bed Type: ");
        // Set data in the drop-down list
        String[] typeOfBeds = {"Twin", "Full", "Queen", "King"};
        // Create combobox
        bedTypeCombo = new JComboBox(typeOfBeds);

        numOfBedsLabel = new JLabel("# of Beds:");
        // Set data in the drop-down list
        String[] numberOfBeds = {"1", "2", "3", "4", "5"};

        // Create combobox
        numOfBedsCombo = new JComboBox(numberOfBeds);

        smokingLabel = new JLabel("Smoking available? ");
        smokingYes = new JRadioButton("Yes");
        smokingNo = new JRadioButton("No");
        ButtonGroup  smokingChoiceGroup = new ButtonGroup();
        smokingChoiceGroup.add(smokingYes);
        smokingChoiceGroup.add(smokingNo);

        priceLabel = new JLabel("Price: ");
        priceField = new JTextField();
        Double d = room.getRoomPrice();
        priceField.setText(d.toString());

        submitButton = new JButton("Submit");
        JButton backButton = new JButton("Back");

        //first row
        mainPanel.add(new JLabel());
        mainPanel.add(titleLabel);
        mainPanel.add(new JLabel());

        //second row
        mainPanel.add(new JLabel());
        mainPanel.add(new JLabel());
        mainPanel.add(new JLabel());

        //third row
        mainPanel.add(bedTypeLabel);
        mainPanel.add(bedTypeCombo);
        mainPanel.add(new JLabel());

        //fourth row
        mainPanel.add(numOfBedsLabel);
        mainPanel.add(numOfBedsCombo);
        mainPanel.add(new JLabel());

        //fifth row
        mainPanel.add(smokingLabel);
        mainPanel.add(smokingYes);
        mainPanel.add(smokingNo);

        //sixth row
        mainPanel.add(priceLabel);
        mainPanel.add(priceField);
        mainPanel.add(new JLabel());

        //seventh row
        mainPanel.add(submitButton);
        mainPanel.add(new JLabel());
        mainPanel.add(backButton);

        frame.add(mainPanel);
        frame.setVisible(true);

        backButton.addActionListener(e -> {
            frame.dispose();
            previousPage.show();
        });

        submitButton.addActionListener(e -> {
            //get bed type
            String newBedType = (String) bedTypeCombo.getSelectedItem();

            //get Number of beds
            int newNumberOfBeds = Integer.parseInt((String) numOfBedsCombo.getSelectedItem());
            boolean newSmokingAvailability = room.getSmokingAvailable();
            if(smokingYes.isSelected()){
                newSmokingAvailability= true;

            }
            else if(smokingNo.isSelected()){
                newSmokingAvailability= false;
            }

            //get Smoking availibility
            ButtonModel selected  = smokingChoiceGroup.getSelection();


            //get price
            double newPrice = Double.parseDouble(priceField.getText());


            //now that you have all the new input, check if inputs are valid(if room number is valid)
            //if roomNumber was not valid, room already exists on a specified cruise, or price is invalid then
            //specify to user that an invalid decision was made

            if (newPrice < 0) {
                invalidDecision();
                frame.dispose();
                createGUI();
                return;
            }

            //if no changes were made, remind the agent
            if (newBedType.equals(room.getBedType()) &&
                    newNumberOfBeds == room.getNumberOfBeds() &&
                    newSmokingAvailability == room.getSmokingAvailable()
                    &&
                    (Math.abs(newPrice-room.getRoomPrice()) < 0.0001)) {
                noChangesDecision();
            }
            //if there is a non-duplicate value, update the room
            else{

                //if the user confirms their decision, update and go back to landing page
                if (validateDecision(newBedType,newNumberOfBeds,newSmokingAvailability,newPrice)) {
                    updateRoom(newBedType,newNumberOfBeds,newSmokingAvailability,newPrice);
                    frame.dispose();
                    previousPage.show(); // Go back to the landing page
                }
                //otherwise restart the edit frame
                else {
                    frame.dispose();
                    createGUI();
                }
            }


        });
    }

    /**
     * Displays the EditRoomPage frame.
     */
    public void show() {
        frame.setVisible(true);
    }

    /**
     * Updates the room information using the {@link EditRoomController}.
     *
     * @param bedType        The new bed type.
     * @param numOfBeds      The new number of beds.
     * @param smokingChoice  The new smoking availability.
     * @param price          The new price.
     */
    private void updateRoom( String bedType, int numOfBeds, boolean smokingChoice, double price) {
        EditRoomController.editRoom(room,bedType,numOfBeds,smokingChoice,price);
    }

    /**
     * Validates the user's decision before updating the room.
     *
     * @param bedType        The new bed type.
     * @param numOfBeds      The new number of beds.
     * @param smokingChoice  The new smoking availability.
     * @param price          The new price.
     * @return True if the user confirms the decision, false otherwise.
     */
    public boolean validateDecision(String bedType, int numOfBeds, boolean smokingChoice, double price) {
        UIManager.put("OptionPane.yesButtonText", "Confirm");
        UIManager.put("OptionPane.noButtonText", "Cancel");

        String smokingString = "No";
        if(smokingChoice==true){
            smokingString= "Yes";
        }

        int dialogResult = JOptionPane.showConfirmDialog(mainPanel,"You are changing your room details to: \n" +
                "Bed Type: " + bedType + "\nNumber of beds: " + numOfBeds + "\nSmoking: "+ smokingString
                + "\nPrice: " + price,"Review Changes", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        return dialogResult == JOptionPane.YES_OPTION;
    }

    /**
     * Displays an error message for invalid input during room modification.
     */
    public void invalidDecision(){
        JOptionPane.showMessageDialog(mainPanel, "Invalid Input for Room Modification", "Error!", JOptionPane.PLAIN_MESSAGE);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the decision-making process when no changes have been made.
     */
    public void noChangesDecision() {
        UIManager.put("OptionPane.yesButtonText", "Yes, quit");
        UIManager.put("OptionPane.noButtonText", "No, continue");

        int dialogResult = JOptionPane.showConfirmDialog(mainPanel, "No changes have been made. Would you like to quit?",
                "Room details are unchanged", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (dialogResult == JOptionPane.YES_OPTION) {
            frame.dispose(); //close this frame
            previousPage.show(); // Go back to the previous page
        }
        else {
            frame.dispose(); //close this instance of frame
            createGUI(); //restart the frame
        }
    }
}
