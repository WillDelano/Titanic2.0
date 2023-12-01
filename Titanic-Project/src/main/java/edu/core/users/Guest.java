package edu.core.users;

import edu.core.billingmanagement.Billing;
import edu.core.billingmanagement.PaymentInfo;
import edu.core.cruise.Country;
import edu.core.reservation.Reservation;

import java.time.LocalDate;
import java.util.*;

import edu.core.reservation.Room;
import edu.databaseAccessors.AccountDatabase;
import edu.databaseAccessors.CountryDatabase;
import edu.databaseAccessors.ReservationDatabase;
import edu.databaseAccessors.RoomDatabase;
import edu.exceptions.NoMatchingReservationException;

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
     * @param firstName  The first name of the guest.
     * @param lastName   The last name of the guest.
     * @param rewardPoints Initial reward points of the guest.
     * @param email      The email of the guest.
     */
    public Guest(String username, String password, String firstName, String lastName, int rewardPoints, String email) {
        super(username, password, firstName, lastName, email);
        this.rewardPoints = rewardPoints;
        this.reservations = new ArrayList<>();
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
     * Makes a reservation for a specific room.
     *
     * @param room          Room to be reserved.
     * @param startDate     Start date of the reservation.
     * @param endDate       End date of the reservation.
     * @param startCountry  Starting country of the cruise.
     * @param endCountry    Ending country of the cruise.
     * @return              The created reservation.
     */
    public Reservation makeReservation(Room room, LocalDate startDate, LocalDate endDate, String startCountry, String endCountry) {
        if (endDate.isBefore(startDate) || startDate.equals(endDate)) {
            throw new RuntimeException("Invalid Date Range. Please make sure start date is before end date.");
        }

        Country startCountryObject = CountryDatabase.getCountry(startCountry);
        System.out.println("Start Country Object: " + startCountryObject);

        Country endCountryObject = CountryDatabase.getCountry(endCountry);
        System.out.println("End Country Object: " + endCountryObject);

        Reservation reservation = new Reservation(-1, this, room, startDate, endDate, startCountryObject, endCountryObject);


        if (!ReservationDatabase.hasReservation(reservation)) {
            ReservationDatabase.addReservation(reservation);
            room.bookRoom();


            /* -1 was initially passed as the id so the DB could create its own, so now this is needed to update the reservation
            variable to have the correct id */
            try {
                reservation = ReservationDatabase.getReservationByRoom(reservation.getRoom().getRoomNumber());
            } catch (NoMatchingReservationException e) {
                e.printStackTrace();
            }

            this.reservations.add(reservation);
            
            RoomDatabase.bookRoom(room.getRoomNumber());
        } else {
            System.err.println("Attempting to add duplicate reservation - Cancelled.");
        }

        return reservation;
    }

    /**
     * Returns the reservations for a guest
     *
     * @return Set of reservations associated with a guest.
     */
    public Set<Reservation> getReservations() {
        return ReservationDatabase.getReservations(this);
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