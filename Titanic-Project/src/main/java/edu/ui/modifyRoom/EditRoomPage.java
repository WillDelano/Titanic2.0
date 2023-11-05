package edu.ui.modifyRoom;

import edu.core.reservation.Room;
import edu.core.users.TravelAgent;
import edu.core.users.User;
import edu.databaseAccessors.RoomDatabase;
import edu.ui.editReservation.EditReservationController;
import edu.ui.landingPage.LandingPage;
import edu.ui.landingPage.TravelAgentLandingPage;
import edu.ui.modifyRoom.DetailOptionsLandingPage;
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
    private JLabel deleteAccountLabel;
    private JRadioButton deleteAccountYes;
    private JRadioButton deleteAccountNo;

    private JLabel roomNumberLabel;
    private JTextField roomNumberField;
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
    private LandingPage previousPage;
    private Room room;


    public EditRoomPage(Room room/*TravelAgentLandingPage prevPage*/) {
      //  this.previousPage = prevPage;
        this.room = room;
        createGUI();
    }

    public static void main(String[]args){
        boolean tester = true;
        EditRoomPage testing = new EditRoomPage(new Room(123,3,"Twin",true,21.21,"USA"));
    }
    private void createGUI() {
        frame = new JFrame("Add Room");
        frame.setSize(400, 300);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(8, 3, 15, 20));

        /*String firstName = account.getFirstName();
        String lastName = account.getLastName();
        titleLabel = new JLabel(firstName + " " + lastName, JLabel.CENTER);*/

        Integer i  = room.getRoomNumber();
        String roomNumber = i.toString();
        titleLabel = new JLabel("Room "+roomNumber+" Details:");
        roomNumberLabel = new JLabel("New Number:");
        roomNumberField = new JTextField();
        roomNumberField.setText(roomNumber);

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
        mainPanel.add(roomNumberLabel);
        mainPanel.add(roomNumberField);

        //prefill the correct account information to display
        /*String email = account.getEmail();
        roomNumberField.setText(email);
*/
        mainPanel.add(new JLabel());

        //third row
        mainPanel.add(bedTypeLabel);
        mainPanel.add(bedTypeCombo);

        //prefill the correct account information to display
        /*String password = account.getPassword();
        passwordField.setText(password);*/

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

        //sixth row
        mainPanel.add(new JLabel());
        mainPanel.add(submitButton);
        mainPanel.add(new JLabel());

        frame.add(mainPanel);
        frame.setVisible(true);


        submitButton.addActionListener(e -> {

            //get new number
            String newRoomNumber = (String) roomNumberField.getText();

            //get bed type
            String newBedType = (String) bedTypeCombo.getSelectedItem();

            //get Number of beds
            String newNumberOfBeds = (String) numOfBedsCombo.getSelectedItem();


            //get Smoking availibility
            ButtonModel selected  = smokingChoiceGroup.getSelection();
            String newSmokingChoice = null;
            if (selected != null) {
                newSmokingChoice = selected.getActionCommand();
            }

            //get price
            String newPrice = (String) priceField.getText();


            room.setRoomNumber(Integer.parseInt(newRoomNumber));
            room.setBedType(newBedType);
            room.setNumberOfBeds(Integer.parseInt(newNumberOfBeds));


            if(selected  != null){
                if(newSmokingChoice.equals("Yes")){
                    room.setSmokingAvailable(true);
                }
                else{
                    room.setSmokingAvailable(false);
                }
            }

            room.setRoomPrice(Double.parseDouble(newPrice));

            /*//get delete account choice
            boolean deleteAccount = deleteAccountYes.isSelected();

            //if the choice was not to delete the account
            if (!deleteAccount) {

                //if email was not valid, start over
                if (!validateEmail(newEmail)) {
                    frame.dispose();
                    createGUI();
                    return;
                }

                //if no changes were made, remind the user
                if (Objects.equals(newEmail, account.getEmail()) && Objects.equals(newPassword, account.getPassword())) {

                    noChangesDecision();
                }
                //if there is a non-duplicate value, update the account
                else {

                    //if the user confirms their decision, update and go back to landing page
                    if (validateDecision(newEmail, newPassword)) {
                        updateAccount(newEmail, newPassword);
                        frame.dispose();
                        previousPage.show(); // Go back to the landing page
                    }
                    //otherwise restart the edit frame
                    else {
                        frame.dispose();
                        createGUI();
                    }
                }
            }
            //ask for confirmation before deleting the account
            else {

                //if they decided not to delete their account, restart
                if (!deleteAccount()) {
                    frame.dispose();
                    createGUI();
                }
                //if they did delete, return to landing page
                else {
                    frame.dispose();
                    previousPage.show();
                }
            }*/
        });
    }

    public void show() {
        frame.setVisible(true);
    }

    /*private void updateAccount(String email, String password) {
        EditProfileController.editAccount(account, email, password);
    }

    public boolean deleteAccount() {
        UIManager.put("OptionPane.yesButtonText", "Confirm");
        UIManager.put("OptionPane.noButtonText", "Cancel");

        int dialogResult = JOptionPane.showConfirmDialog(mainPanel, "Are you sure you want to delete your account?",
                "This action cannot be undone!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (dialogResult == JOptionPane.YES_OPTION) {
            EditProfileController.deleteAccount(account);
            return true;
        }
        return false;
    }

    public boolean validateDecision(String email, String password) {
        UIManager.put("OptionPane.yesButtonText", "Confirm");
        UIManager.put("OptionPane.noButtonText", "Cancel");

        //if both values to be changed are different
        if (!Objects.equals(account.getEmail(), email) && !Objects.equals(account.getPassword(), password)) {
            int dialogResult = JOptionPane.showConfirmDialog(mainPanel, "You are changing your email to: "
                    + email + " and your password to " + password, "Review Changes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            return dialogResult == JOptionPane.YES_OPTION;
        }
        //if only the email was changed
        else if (!Objects.equals(account.getEmail(), email)) {
            int dialogResult = JOptionPane.showConfirmDialog(mainPanel, "You are changing your email to: "
                    + email,"Review Changes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            return dialogResult == JOptionPane.YES_OPTION;
        }
        //only the password was changed
        else {
            int dialogResult = JOptionPane.showConfirmDialog(mainPanel, "You are changing your password to: "
                    + password, "Review Changes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            return dialogResult == JOptionPane.YES_OPTION;
        }
    }

    public boolean validateEmail(String email) {
        //Checking email format
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            JOptionPane.showMessageDialog(frame, "Invalid email format", "Oops!", JOptionPane.WARNING_MESSAGE);
        }

        return matcher.matches();
    }

    public boolean noChangesDecision() {
        UIManager.put("OptionPane.yesButtonText", "Yes, quit");
        UIManager.put("OptionPane.noButtonText", "No, continue");

        int dialogResult = JOptionPane.showConfirmDialog(mainPanel, "No changes have been made. Would you like to quit?",
                "Reservation is unchanged", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (dialogResult == JOptionPane.YES_OPTION) {
            frame.dispose(); //close this frame
            previousPage.show(); // Go back to the landingPage
            return true;
        }
        else {
            frame.dispose(); //close this instance of frame
            createGUI(); //restart the frame
            return false;
        }
    }*/
}
