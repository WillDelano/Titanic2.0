package edu.ui.modifyRoom;

import edu.core.reservation.Room;
import edu.core.users.TravelAgent;
import edu.core.users.User;
import edu.databaseAccessors.RoomDatabase;
import edu.ui.editReservation.EditReservationController;
import edu.ui.landingPage.LandingPage;
import edu.ui.landingPage.TravelAgentLandingPage;
import edu.uniqueID.UniqueID;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private static LandingPage previousPage;
    private static Room room;


    public EditRoomPage(Room room,LandingPage prevPage) {
        this.previousPage = prevPage;
        this.room = room;
        createGUI();
    }

    public static void main(String[]args){
        boolean tester = true;
        EditRoomPage testing = new EditRoomPage(new Room(101, 2, "Queen", false, 250, "Caribbean Adventure"),previousPage);
    }
    private void createGUI() {
        frame = new JFrame("Edit Room");
        frame.setSize(400, 300);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(8, 3, 15, 20));

        /*String firstName = account.getFirstName();
        String lastName = account.getLastName();
        titleLabel = new JLabel(firstName + " " + lastName, JLabel.CENTER);*/

        Integer i  = room.getRoomNumber();
        String roomNumber = i.toString();
        titleLabel = new JLabel("Room "+roomNumber+" Details:");

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
        mainPanel.add(new JLabel());
        mainPanel.add(submitButton);
        mainPanel.add(new JLabel());

        frame.add(mainPanel);
        frame.setVisible(true);


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

            if (newBedType.equals(room.getBedTypeStr()) &&
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

    public void show() {
        frame.setVisible(true);
    }

    private void updateRoom( String bedType, int numOfBeds, boolean smokingChoice, double price) {
        //Fixme: Fully implement this in roomDatabase
        EditRoomController.editRoom(room,bedType,numOfBeds,smokingChoice,price);
    }


    public boolean validateDecision(String bedType, int numOfBeds, boolean smokingChoice, double price) {
        UIManager.put("OptionPane.yesButtonText", "Confirm");
        UIManager.put("OptionPane.noButtonText", "Cancel");

        String smokingString = "No";
        if(smokingChoice==true){
            smokingString= "Yes";
        }

        int dialogResult = JOptionPane.showConfirmDialog(mainPanel,"You are changing your room details to: " +
                "Bed Type- "+bedType+"\nBed #- "+numOfBeds+"\nSmoking Status"+smokingString
                +"\nPrice- "+price,"Review Changes", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        return dialogResult == JOptionPane.YES_OPTION;
    }


    public void invalidDecision(){
        JOptionPane.showMessageDialog(mainPanel, "Invalid Input for Room Modification", "Error!", JOptionPane.PLAIN_MESSAGE);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean noChangesDecision() {
        UIManager.put("OptionPane.yesButtonText", "Yes, quit");
        UIManager.put("OptionPane.noButtonText", "No, continue");

        int dialogResult = JOptionPane.showConfirmDialog(mainPanel, "No changes have been made. Would you like to quit?",
                "Room details are unchanged", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (dialogResult == JOptionPane.YES_OPTION) {
            frame.dispose(); //close this frame
            previousPage.show(); // Go back to the previous page
            return true;
        }
        else {
            frame.dispose(); //close this instance of frame
            createGUI(); //restart the frame
            return false;
        }
    }
}
