package edu.ui.adminCreateTravelAgent;

import edu.databaseAccessors.AccountDatabase;

/**
 * Controller class for creating travel agent accounts and checking for duplicates.
 * This class provides methods to create a new travel agent account and check if
 * a username already exists in the account database.
 *
 * <p>
 * Note: The class uses the {@link AccountDatabase} for database interactions.
 * </p>
 *
 * @version 1.0
 * @see AccountDatabase
 * @author William Delano
 */
public class CreateTravelAgentController {
    /**
     * Creates a new travel agent account with the provided username and password.
     *
     * @param username The username for the new travel agent account.
     * @param password The password for the new travel agent account.
     */
    public static void createAccount(String username, String password) {
        new AccountDatabase();

        AccountDatabase.addUser(username, password, "", "", 0, "", "Agent");
    }

    /**
     * Checks if a username already exists in the account database.
     *
     * @param username The username to check for duplicates.
     * @return {@code true} if the username already exists, {@code false} otherwise.
     */
    public static boolean checkForDuplicate(String username) {
        new AccountDatabase();
        return AccountDatabase.accountExists(username);
    }
}
