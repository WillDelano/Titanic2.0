package edu.core.users;

import java.util.Objects;

/**
 * Representation of an admin user in the cruise reservation system.
 *
 * This class provides specific functionalities and attributes related to an admin user responsible for managing system settings, travel agent accounts, etc.
 *
 * @author Vincent Dinh
 * @version 1.0
 * @see User
 */
public class Admin extends User {

    /**
     * Constructor for creating a new Admin.
     *
     * @param username   The username of the admin.
     * @param password   The password of the admin.
     * @param firstName  The first name of the admin.
     * @param lastName   The last name of the admin.
     * @param email      The email of the admin.
     */
    public Admin(String username, String password, String firstName, String lastName, String email) {
        super(username, password, firstName, lastName, email);
    }

    /**
     * Admin creates an account for a travel agent
     *
     * @param username   The username of the travel agent.
     * @param password   The password of the travel agent.
     * @param firstName  The first name of the travel agent.
     * @param lastName   The last name of the travel agent.
     * @param email      The email of the travel agent.
     */
    /*public void createTravelAgent(String username, String password, String firstName, String lastName, String email){
        AccountDatabase d = new AccountDatabase();

        if(!d.accountExists(username)){
            TravelAgent agent = new TravelAgent(username,password,new UniqueID().getId(),firstName,lastName, email);
            d.addUser(agent);
        }
    }*/

    /**
     * Admin changes the password for a travel agent
     *
     * @param agent   TThe travel agent.
     * @param newPassword   The new password of the travel agent.
     */
    public void resetTravelAgentPassword(TravelAgent agent, String newPassword){
        if (agent == null || newPassword == null) {
            throw new IllegalArgumentException("Agent and password cannot be null.");
        }

        if (Objects.equals(agent.getPassword(), newPassword)) {
            throw new IllegalArgumentException("New password cannot be the same as the old password.");
        }

        agent.setPassword(newPassword);
    }

}