package com.core;

public class Billing {
    private Reservation reservation;
    private double totalAmount;
    private boolean isDisputed;
    private String receipt;
    private boolean disputeResolved;

    public Bill(Reservation reservation, double totalAmount, boolean isDisputed) {
        this.reservation = reservation;
        this.totalAmount = reservation.calculateTotal();
        this.isDisputed = false;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void disputeCharge() {
        this.isDisputed = true;
    }

    public boolean isDisputed() {
        return isDisputed;
    }

    public void adjustAmount(double newAmount) {
        this.totalAmount = newAmount;
    }

    public String generateReceipt() {
        this.receipt = "Digital Receipt for Reservation ID: " + reservation.getReservationId() +
                "\nTotal Amount: $" + totalAmount;
        return receipt;
    }

    /**
     * Resolves the current dispute on the bill, either by accepting or rejecting it.
     *
     * @param resolutionNotes Notes detailing the resolution of the dispute.
     */
    public void resolveDispute(String resolutionNotes) {
        this.isDisputed = false;
        this.disputeResolutionNotes = resolutionNotes;
        this.disputeResolved = true;
    }

    /**
     * Checks if the dispute on the bill has been resolved.
     *
     * @return True if the dispute has been resolved, otherwise false.
     */
    public boolean isDisputeResolved() {
        return disputeResolved;
    }

}