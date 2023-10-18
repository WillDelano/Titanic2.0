package com.core;

/**
 * Represents the billing information associated with a reservation.
 * This class provides methods to manage, dispute, and adjust the bill,
 * as well as generate a receipt for a successful payment.
 *
 * @author Vincent Dinh
 * @version 1.0
 */
public class Billing {

    private Reservation reservation;
    private double totalAmount;
    private boolean isDisputed = false;
    private String receipt;
    private boolean disputeResolved = false;
    private String resolutionNotes;

    /**
     * Constructs a new Billing object with the given reservation details,
     * total amount, and receipt.
     *
     * @param reservation The reservation associated with this bill.
     * @param totalAmount The total amount to be billed.
     * @param receipt The receipt for the bill.
     */
    public Billing(Reservation reservation, double totalAmount, String receipt) {
        this.reservation = reservation;
        this.totalAmount = totalAmount;
        this.receipt = receipt;
    }

    /**
     * Gets the total amount to be billed.
     *
     * @return The total billing amount.
     */
    public double getTotalAmount() {
        return totalAmount;
    }

    /**
     * Marks the current bill as disputed.
     */
    public void disputeCharge(String reason) {
        this.isDisputed = true;
    }

    /**
     * Checks if the current bill has been disputed.
     *
     * @return True if the bill is disputed, otherwise false.
     */
    public boolean isDisputed() {
        return isDisputed;
    }

    /**
     * Allows adjusting the total billing amount.
     *
     * @param newAmount The new amount to be billed.
     */
    public void adjustAmount(double newAmount) {
        this.totalAmount = newAmount;
    }

    /**
     * Generates and returns a receipt for a successful payment.
     * The receipt includes details such as the reservation ID and total amount paid.
     *
     * @return A string representation of the digital receipt.
     */
    public String generateReceipt() {
        this.receipt = "Digital Receipt for Reservation ID: " + reservation.getReservationId() +
                "\nTotal Amount: $" + totalAmount;
        return receipt;
    }

    /**
     * Resolves the current dispute associated with this bill.
     *
     * @param resolutionNotes Notes or comments detailing the resolution of the dispute.
     */
    public void resolveDispute(String resolutionNotes) {
        this.isDisputed = false;
        this.resolutionNotes = resolutionNotes;
        this.disputeResolved = true;
    }

    /**
     * Checks if the dispute associated with this bill has been resolved.
     *
     * @return True if the dispute has been resolved, otherwise false.
     */
    public boolean isDisputeResolved() {
        return disputeResolved;
    }

}
