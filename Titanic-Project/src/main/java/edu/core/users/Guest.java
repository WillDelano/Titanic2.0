package edu.core.users;

import edu.core.billingmanagement.Billing;
import edu.core.billingmanagement.PaymentInfo;
import edu.core.cruise.Country;
import edu.core.reservation.Reservation;

import java.time.LocalDate;
import java.util.*;

import edu.core.reservation.Room;
import edu.database.ReservationDatabase;

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
     * @param email      The email of the guest.
     */
    public Guest(String username, String password, int id, String firstName, String lastName, int rewardPoints, String email) {
        super(username, password, id, firstName, lastName, email);
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
     *
     * @return Returns reservation for testing purposes
     */
    public Reservation makeReservation(Room room, LocalDate startDate, LocalDate endDate, Country startCountry, Country endCountry) {
        // if end date is before or equal to start date, throw error message
        if (endDate.isBefore(startDate) || startDate.equals(endDate)) {
            throw new RuntimeException("Invalid Date Range. Please make sure start date is before end date.");
        }

        Reservation reservation = new Reservation(this, room, startDate, endDate, startCountry, endCountry);

        // fetch or create the reservation set for the user.
        Set<Reservation> reservationSet = ReservationDatabase.getReservationDatabase().getOrDefault(this, new LinkedHashSet<>());

        // add the reservation. If it's a duplicate, the Set will handle it.
        reservationSet.add(reservation);

        // update the database with the reservation set.
        ReservationDatabase.getReservationDatabase().put(this, reservationSet);

        return reservation;
    }

    /**
     * Returns the reservations for a guest
     *
     * @return Set of reservations associated with a guest.
     */
    public Set<Reservation> getReservations() {
        if (ReservationDatabase.getReservationDatabase().containsKey(this)) {
            return ReservationDatabase.getReservationDatabase().get(this);
        }
        else {
            throw new NoSuchElementException("User has no reservation");
        }
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