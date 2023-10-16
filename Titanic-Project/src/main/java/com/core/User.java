package com.core;

import com.authentication.Authentication;

/**
 * Abstract representation of a user in the cruise reservation system.
 *
 * This class provides a basic blueprint for all types of users in the system like Guest, Travel Agent, and Admin.
 *
 * @author Your Name (replace this with your actual name)
 * @version 1.0
 */
public abstract class User implements Authentication {
    private String username;
    private String password;
    private Long id;
    private String firstName;
    private String lastName;

    /**
     * Constructor for creating a new User.
     *
     * @param username   The username of the user.
     * @param password   The password of the user.
     * @param id         The unique ID of the user.
     * @param firstName  The first name of the user.
     * @param lastName   The last name of the user.
     */
    public User(String username, String password, Long id, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}