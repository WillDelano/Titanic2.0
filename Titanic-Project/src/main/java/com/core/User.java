package com.core;

import com.authentication.Authentication;

import java.util.Objects;

/**
 * Abstract representation of a user in the cruise reservation system.
 *
 * This class provides a basic blueprint for all types of users in the system like Guest, Travel Agent, and Admin.
 *
 * @author Vincent Dinh
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(id, user.id) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName);
    }


}