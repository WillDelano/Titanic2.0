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
        Reservation reservation = new Reservation(this, room, startDate, endDate, startCountry, endCountry);

        //if user already exists in reservation database (i.e. they have an outstanding reservation)
        if (ReservationDatabase.getReservationDatabase().containsKey(this)) {

            //assign set to reservation set of a user
            Set<Reservation> reservationSet = new LinkedHashSet<>();
            reservationSet = ReservationDatabase.getReservationDatabase().get(this);

            /*
            I have no idea why I need this to prevent duplicates in the set. I've doubled checked the hashcodes and equals
            for room, country, and reservation and tested in every possible way. The equals operation finds them to be
            equal but the set will still add them??? No clue so added this scuffed fix
             */
            boolean duplicate = false;

            //if any reservation (q) in the set equals the new reservation, set duplicate to true
            for (Reservation q : reservationSet) {
                if (q.equals(reservation)) {
                    duplicate = true;
                }
            }

            //if it is not a duplicate, add it to the set
            if (!duplicate) {
                reservationSet.add(reservation);
            }

            //put set back in database, replacing the outdated key-value pair
            ReservationDatabase.getReservationDatabase().put(this, reservationSet);
        }
        //user doesn't have an outstanding reservation
        else {
            //Create the new set of reservations for the user
            Set<Reservation> reservationSet = new LinkedHashSet<>();

            //add their reservation
            reservationSet.add(reservation);

            //put reservation in map
            ReservationDatabase.getReservationDatabase().put(this, reservationSet);
        }
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