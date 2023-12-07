package edu.ui.adminCreateTravelAgent;

import edu.core.users.User;
import edu.databaseAccessors.AccountDatabase;

/**
 * Controller class for finishing the creation or updating of a travel agent account.
 * This class provides a method to update the account information of a travel agent,
 * including their email, first name, and last name. It interacts with the
 * {@link AccountDatabase} for database operations.
 *
 * @version 1.0
 * @see AccountDatabase
 * @author William Delano
 */
public class FinishTravelAgentController {
    /**
     * Updates the account information for a travel agent.
     *
     * @param account The User object representing the travel agent account.
     * @param email   The new email for the travel agent account.
     * @param first   The new first name for the travel agent account.
     * @param last    The new last name for the travel agent account.
     */
    public static void updateAccount(User account, String email, String first, String last) {
        new AccountDatabase();

        AccountDatabase.updateAccount(account, email, account.getPassword(), account.getUsername(), first, last);
    }
}
