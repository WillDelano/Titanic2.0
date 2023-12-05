package edu.ui.billingProcessing;

import edu.core.reservation.Reservation;
import edu.databaseAccessors.ReservationDatabase;
import edu.ui.guestReservationList.MyReservationsPage;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.sql.SQLException;

public class ProcessBillingPage extends JFrame {
    private JTextField firstNameField, lastNameField, addressField, zipCodeField, creditCardField, expiryDateField, cvcField;
    private JComboBox<String> countryComboBox, stateComboBox;
    private JLabel totalAmountLabel;
    private JButton payButton;
    private double totalAmount;
    JPanel panel;
    Reservation reservation;
    MyReservationsPage page;

    public ProcessBillingPage(double totalAmount, Reservation r, MyReservationsPage page) {
        this.page = page;
        this.reservation = r;
        this.totalAmount = totalAmount;
        setTitle("Process Billing");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        panel = new JPanel(new GridLayout(10, 2, 10, 10));

        panel.add(new JLabel("First Name:"));
        firstNameField = new JTextField(20);
        panel.add(firstNameField);

        panel.add(new JLabel("Last Name:"));
        lastNameField = new JTextField(20);
        panel.add(lastNameField);

        panel.add(new JLabel("Address:"));
        addressField = new JTextField(20);
        panel.add(addressField);

        panel.add(new JLabel("Zip Code:"));
        zipCodeField = new JTextField(20);
        panel.add(zipCodeField);

        panel.add(new JLabel("Country:"));
        countryComboBox = new JComboBox<>(getAllCountries());

        // Initialize stateComboBox before calling toggleStateField
        stateComboBox = new JComboBox<>(getAllStates());
        stateComboBox.setVisible(false); // Initially hidden

        // Now safely call toggleStateField
        toggleStateField("United States");

        countryComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                toggleStateField(e.getItem().toString());
            }
        });

        countryComboBox.addItemListener(e -> {
            if (e.getItem().toString() == "United States") {
                stateComboBox.setVisible(true);
            }
        });

        countryComboBox.setSelectedItem("United States");
        panel.add(countryComboBox);

        // Initialize the state field based on the default country
        toggleStateField("United States");


        panel.add(countryComboBox);
        //set country to canada so they have to refresh the states and click on US
        countryComboBox.setSelectedItem(countryComboBox.getItemAt(1));

        panel.add(new JLabel("State:"));
        stateComboBox = new JComboBox<>(getAllStates());
        stateComboBox.setVisible(false); // Initially hidden
        panel.add(stateComboBox);

        panel.add(new JLabel("Credit Card Number:"));
        creditCardField = new JTextField(20);
        creditCardField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                formatCreditCardField();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                formatCreditCardField();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                formatCreditCardField();
            }

            private void formatCreditCardField() {
                SwingUtilities.invokeLater(() -> {
                    String text = creditCardField.getText().replaceAll("[^\\d]", "");
                    StringBuilder formattedText = new StringBuilder();

                    for (int i = 0; i < text.length(); i++) {
                        if (i > 0 && i % 4 == 0 && i < 16) {
                            formattedText.append("-");
                        }
                        formattedText.append(text.charAt(i));
                        if (formattedText.length() >= 19) break; // Limit to 19 characters
                    }

                    if (!creditCardField.getText().equals(formattedText.toString())) {
                        creditCardField.setText(formattedText.toString());
                    }
                });
            }
        });

        panel.add(creditCardField);

        panel.add(new JLabel("Expiry Date (MM/YY):"));

        expiryDateField = new JTextField(5);
        ((AbstractDocument) expiryDateField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                String newText = currentText.substring(0, offset) + text + currentText.substring(offset);

                if (newText.matches("^(0[1-9]|1[0-2])?/?[0-9]{0,2}$") && newText.length() <= 5) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
        panel.add(expiryDateField);
        panel.add(new JLabel("CVC:"));
        cvcField = new JTextField(4);
        ((AbstractDocument) cvcField.getDocument()).setDocumentFilter(new DocumentFilter() {
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                String string = fb.getDocument().getText(0, fb.getDocument().getLength()) + text;
                if ((fb.getDocument().getLength() + text.length() - length) <= 3 && string.matches("\\d*")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
        panel.add(cvcField);

        totalAmountLabel = new JLabel("Total Amount: $" + totalAmount);
        panel.add(totalAmountLabel);

        payButton = new JButton("Pay Now");
        payButton.addActionListener((ActionEvent e) -> processPayment());
        panel.add(payButton);

        add(panel);
    }

    private void toggleStateField(String country) {
        if ("United States".equals(country)) {
            stateComboBox.setVisible(true);
        } else {
            stateComboBox.setVisible(false);
        }
    }

    private String[] getAllCountries() {
        // TODO: Replace with actual countries list
        return new String[]{"United States", "Canada", "United Kingdom", "Australia"};
    }

    private String[] getAllStates() {
        // TODO: Replace with actual states list
        return new String[] {
                "Alabama", "Alaska", "Arizona", "Arkansas", "California",
                "Colorado", "Connecticut", "Delaware", "Florida", "Georgia",
                "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa",
                "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland",
                "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri",
                "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey",
                "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio",
                "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina",
                "South Dakota", "Tennessee", "Texas", "Utah", "Vermont",
                "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"
        };
    }

    // validation for each field
    private boolean validateFields() {

        if (firstNameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your first name.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }


        if (lastNameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your last name.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }


        if (addressField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your address.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }


        if (!zipCodeField.getText().matches("\\d{5}")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid 5-digit ZIP code.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }


        if (countryComboBox.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select a country.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }


        if ("United States".equals(countryComboBox.getSelectedItem()) && (stateComboBox.getSelectedItem() == null || stateComboBox.isVisible() && stateComboBox.getSelectedItem().toString().isEmpty())) {
            JOptionPane.showMessageDialog(this, "Please select a state.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }


        if (!creditCardField.getText().matches("\\d{4}-\\d{4}-\\d{4}-\\d{4}")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid credit card number in the format XXXX-XXXX-XXXX-XXXX.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }


        if (!expiryDateField.getText().matches("(0[1-9]|1[0-2])/\\d{2}")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid expiry date in the format MM/YY.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }


        if (!cvcField.getText().matches("\\d{3}")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid 3-digit CVC number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void processPayment() {
        if (!validateFields()) {
            return;
        }
        JOptionPane.showMessageDialog(this, "Payment processed for $" + totalAmount);
        ReservationDatabase.deleteReservation(reservation);
        remove(panel);
        try {
            page.refreshReservations();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        //SwingUtilities.invokeLater(() -> new ProcessBillingPage(100.00).setVisible(true));
    }
}
