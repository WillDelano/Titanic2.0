package edu.core.users;

import edu.core.billingmanagement.Billing;

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
     * @param firstName  The first name of the travel agent.
     * @param lastName   The last name of the travel agent.
     * @param email      The email of the travel agent.
     */
    public TravelAgent(String username, String password, String firstName, String lastName, String email) {
        super(username, password, firstName, lastName, email);
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