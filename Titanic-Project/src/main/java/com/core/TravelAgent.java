package com.core;

/**
 * Representation of a travel agent in the cruise reservation system.
 *
 * This class provides specific functionalities and attributes related to a travel agent who assists guests in cruise booking and other related services.
 *
 * @author Your Name (replace this with your actual name)
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
}