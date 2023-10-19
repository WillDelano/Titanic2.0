package edu.core.users;

import edu.authentication.Authentication;

import java.util.Objects;

/**
 * Abstract representation of a user in the cruise reservation system.
 *
 * This class provides a basic blueprint for all types of users in the system like Guest, Travel Agent, and Admin.
 *
 * @author Vincent Dinh
 * @version 1.0
 */
public abstract class User  {
    private String username;
    private String password;
    private int id;
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
    public User(String username, String password, int id, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public String getPassword() {
        return password;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(int id) {
        this.id = id;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(id, user.id) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName);
    }


}