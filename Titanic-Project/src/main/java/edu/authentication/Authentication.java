package edu.authentication;

/**
 * Interface for any authentication features
 *
 * <p>
 * Contains all the Authentication methods each user class must implement.
 * </p>
 *
 * @author Gabriel Choi
 * @version 1.0
 */
public interface Authentication {
    /**
     * This changes the password of the user.
     *
     * @param newPassword The new password that replaces the initial password
     */
    public void changePassword(String newPassword);

    /**
     * creates the account of the user.
     *
     * @param username   The username of the user.
     * @param password   The password of the user.
     * @param id         The unique ID of the user.
     * @param firstName  The first name of the user.
     * @param lastName   The last name of the user.
     * @return if the account has successfully been created
     */
    public boolean createAccount(String username, String password, Long id, String firstName, String lastName);

    /**
     * This logs the user into the system.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return if the user has successfully been logged into the system
     */
    public boolean login(String username, String password);

    /**
     * This logs the user out of the system.
     *
     * @return if the user has successfully been logged out of the system
     */
    public boolean logout();
}
