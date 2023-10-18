package com.core.users;

import com.core.users.User;

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

    /**
     * This changes the password of the admin.
     *
     * @param newPassword The new password that replaces the initial password
     */
    public void changePassword(String newPassword){
        //TODO
    }

    /**
     * creates the account of the admin.
     *
     * @param username   The username of the admin.
     * @param password   The password of the admin.
     * @param id         The unique ID of the admin.
     * @param firstName  The first name of the admin.
     * @param lastName   The last name of the admin.
     * @return if the account has successfully been created
     */
    public boolean createAccount(String username, String password, Long id, String firstName, String lastName){
        //TODO
        return false;
    }

    /**
     * This logs the admin into the system.
     *
     * @param username The username of the admin.
     * @param password The password of the admin.
     * @return if the admin has successfully been logged into the system
     */
    public boolean login(String username, String password){
        //TODO
        return false;
    }

    /**
     * This logs the admin out of the system.
     *
     * @return if the admin has successfully been logged out of the system
     */
    public boolean logout(){
        //TODO
        return false;
    }

}