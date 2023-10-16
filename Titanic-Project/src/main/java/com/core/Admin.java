package com.core;

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
     * @param id         The unique ID of the admin.
     * @param firstName  The first name of the admin.
     * @param lastName   The last name of the admin.
     */
    public Admin(String username, String password, Long id, String firstName, String lastName) {
        super(username, password, id, firstName, lastName);
    }
}