package edu.core.users;

import edu.core.billingmanagement.PaymentInfo;
import edu.core.reservation.Reservation;

import java.util.List;
import edu.core.reservation.Room;

/**
 * Representation of a guest user in the cruise reservation system.
 *
 * This class provides specific functionalities and attributes related to a guest user.
 *
 * @author Vincent Dinh
 * @version 1.2
 * @see User
 */
public class Guest extends User {
    private int rewardPoints;
    private List<Reservation> reservations;
    private PaymentInfo paymentInfo;
    private boolean isDeclined = false;

    /**
     * Constructor for creating a new Guest.
     *
     * @param username   The username of the guest.
     * @param password   The password of the guest.
     * @param id         The unique ID of the guest.
     * @param firstName  The first name of the guest.
     * @param lastName   The last name of the guest.
     * @param rewardPoints Initial reward points of the guest.
     */
    public Guest(String username, String password, int id, String firstName, String lastName, int rewardPoints) {
        super(username, password, id, firstName, lastName);
        this.rewardPoints = rewardPoints;
    }

    public PaymentInfo getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(PaymentInfo paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public void setRewardPoints(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    public int getRewardPoints() {
        return rewardPoints;
    }

    /**
     * Requests a reservation for a specific room.
     *
     * @param room Room to be reserved.
     */
    public void requestReservation(Room room) {
        Reservation reservation = new Reservation(room);
        ReservationDatabase database = new ReservationDatabase();

        database.add(reservation);
    }

    /**
     * Requests modification to an existing reservation.
     *
     * @param reservationID The ID of the reservation to be modified.
     */
    public void requestModifyReservation(int reservationID) {
        //TODO Implement method
    }

    /**
     * Requests cancellation of an existing reservation.
     *
     * @param reservationID The ID of the reservation to be canceled.
     */
    public void requestCancelReservation(int reservationID) {
        //TODO Implement method
    }



    /**
     * Method that allows for the guest to make a payment for their cruise
     *
     * @param bill The bill that the guest is making a payment towards
     */

    public void makePayment(Billing bill) {

        PaymentInfo pInfo = this.getPaymentInfo();

        if (pInfo == null || isDeclined) {
            System.out.println("Payment did not go through, exiting the system.");
            return;
        }

        double amountToPay = bill.getTotalAmount();
        System.out.println("Payment of $" + amountToPay + " was successful using " + pInfo.getCardType()
                + " ending in " + pInfo.getCardNumber().substring(pInfo.getCardNumber().length() - 4));

        String receipt = bill.generateReceipt();
        System.out.println(receipt);

    }

    /**
     * Allows the guest to raise a dispute against a specific bill.
     *
     * @param bill The bill that the guest is disputing.
     * @param reason The reason the guest is raising the dispute.
     */
    public void raiseDispute(Billing bill, String reason) {
        bill.disputeCharge(reason);
    }

    /**
     * Allows the guest to request an invoice for their bill
     *
     * @param bill The bill that the guest is requesting an invoice
     */
    public void requestInvoice(Billing bill) {
        //TODO
    }
}