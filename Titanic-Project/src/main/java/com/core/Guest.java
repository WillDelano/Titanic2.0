package com.core;

/**
 * Representation of a guest user in the cruise reservation system.
 *
 * This class provides specific functionalities and attributes related to a guest user.
 *
 * @author Vincent Dinh
 * @version 1.0
 * @see User
 */
public class Guest extends User {
    private int rewardPoints;

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
    public Guest(String username, String password, Long id, String firstName, String lastName, int rewardPoints) {
        super(username, password, id, firstName, lastName);
        this.rewardPoints = rewardPoints;
    }

    // The getters and setters, along with their documentation...

    public void setRewardPoints(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    public int getRewardPoints() {
        return rewardPoints;
    }

    /**
     * Requests a reservation for a specific cruise.
     *
     * @param cruiseID The ID of the cruise to be reserved.
     */
    public void requestReservation(int cruiseID) {
        //TODO Implement method
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
     * This changes the password of the guest.
     *
     * @param newPassword The new password that replaces the initial password
     */
    public void changePassword(String newPassword){
        //TODO
    }

    /**
     * creates the account of the guest.
     *
     * @param username   The username of the guest.
     * @param password   The password of the guest.
     * @param id         The unique ID of the guest.
     * @param firstName  The first name of the guest.
     * @param lastName   The last name of the guest.
     * @return if the account has successfully been created
     */
    public boolean createAccount(String username, String password, Long id, String firstName, String lastName){
        //TODO
        return false;
    }

    /**
     * This logs the guest into the system.
     *
     * @param username The username of the guest.
     * @param password The password of the guest.
     * @return if the guest has successfully been logged into the system
     */
    public boolean login(String username, String password){
        //TODO
        return false;
    }

    /**
     * This logs the guest out of the system.
     *
     * @return if the guest has successfully been logged out of the system
     */
    public boolean logout(){
        //TODO
        return false;
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
}