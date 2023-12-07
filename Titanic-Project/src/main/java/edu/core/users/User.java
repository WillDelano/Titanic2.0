package edu.core.users;

import java.util.Objects;


/**
 * Abstract representation of a user in the cruise reservation system.
 *
 * This class provides a basic blueprint for all types of users in the system like Guest, Travel Agent, and Admin.
 *
 * @author Vincent Dinh
 * @version 1.1
 */
public abstract class User  {
    private String username;
    private String password;
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private int rewardPoints;

    /**
     * Constructor for creating a new User.
     *
     * @param username   The username of the user.
     * @param password   The password of the user.
     * @param firstName  The first name of the user.
     * @param lastName   The last name of the user.
     * @param email      The email of the user.
     */
    public User(String username, String password, String firstName, String lastName, String email) {
        this.username = username;
        this.password = password;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    /**
     * This function returns the password of the user
     *
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * This function returns the last name of the user
     *
     * @return the last name of the user
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * This function returns the first name of the user
     *
     * @return the first name of the user
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * This function returns the username of the user
     *
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * This function returns the id of the user
     *
     * @return the id of the user
     */
    public int getId() {
        return id;
    }

    /**
     * This function returns the email of the user
     *
     * @return the email of the user
     */
    public String getEmail() { return email; }

    /**
     * This function sets the password of a user
     *
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * This function sets the last name of a user
     *
     * @param lastName the last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * This function sets the first name of a user
     *
     * @param firstName the first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * This function sets the username of a user
     *
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * This function sets the id of a user
     *
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * This function overrides the equals function
     *
     * @return true if the users are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(id, user.id) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName);
    }

    /**
     * This function returns the reward points of a user
     *
     * @return the reward points
     */
    public int getRewardPoints() {
        return rewardPoints;
    }

    /**
     * This sets the email for a user
     *
     * @param email to assign to the user
     */
    public void setEmail(String email) {
        this.email = email;
    }
}