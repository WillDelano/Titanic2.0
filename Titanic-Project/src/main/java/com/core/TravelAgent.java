package com.core;

/**
 * Representation of a travel agent in the cruise reservation system.
 *
 * This class provides specific functionalities and attributes related to a travel agent who assists guests in cruise booking and other related services.
 *
 * @author Vincent Dinh
 * @version 1.0
 * @see User
 */
public class TravelAgent extends User {

    /**
     * Constructor for creating a new Travel Agent.
     *
     * @param username   The username of the travel agent.
     * @param password   The password of the travel agent.
     * @param id         The unique ID of the travel agent.
     * @param firstName  The first name of the travel agent.
     * @param lastName   The last name of the travel agent.
     */
    public TravelAgent(String username, String password, Long id, String firstName, String lastName) {
        super(username, password, id, firstName, lastName);
    }

    /**
     * This changes the password of the Travel Agent.
     *
     * @param newPassword The new password that replaces the initial password
     */
    public void changePassword(String newPassword){
        //TODO
    }

    /**
     * creates the account of the Travel Agent.
     *
     * @param username   The username of the Travel Agent.
     * @param password   The password of the Travel Agent.
     * @param id         The unique ID of the Travel Agent.
     * @param firstName  The first name of the Travel Agent.
     * @param lastName   The last name of the Travel Agent.
     * @return if the account has successfully been created
     */
    public boolean createAccount(String username, String password, Long id, String firstName, String lastName){
        //TODO
        return false;
    }

    /**
     * This logs the Travel Agent into the system.
     *
     * @param username The username of the Travel Agent.
     * @param password The password of the Travel Agent.
     * @return if the Travel Agent has successfully been logged into the system
     */
    public boolean login(String username, String password){
        //TODO
        return false;
    }

    /**
     * This logs the Travel Agent out of the system.
     *
     * @return if the Travel Agent has successfully been logged out of the system
     */
    public boolean logout(){
        //TODO
        return false;
    }

    /**
     * Allows the travel agent to handle a dispute raised by a guest.
     *
     * @param bill The disputed bill that needs to be resolved.
     * @param acceptDispute Boolean indicating if the dispute is accepted (true) or rejected (false).
     * @param resolutionNotes Notes detailing the reason for accepting or rejecting the dispute.
     */
    public void handleDispute(Billing bill, boolean acceptDispute, String resolutionNotes) {
        if (acceptDispute) {
            bill.resolveDispute(resolutionNotes);
        } else {
            bill.resolveDispute(resolutionNotes);
        }
    }
}