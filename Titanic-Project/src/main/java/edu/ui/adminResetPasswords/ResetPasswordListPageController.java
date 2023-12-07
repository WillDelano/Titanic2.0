package edu.ui.adminResetPasswords;

import edu.core.users.User;
import edu.databaseAccessors.AccountDatabase;

import java.util.List;

/**
 * Controller class for managing user-related operations in the ResetPasswordListPage UI.
 *
 * <p>
 * This class provides methods to interact with the {@link AccountDatabase} to retrieve a list of users
 * for display in the ResetPasswordListPage UI.
 * </p>
 *
 * @version 1.0
 * @see AccountDatabase
 */
public class ResetPasswordListPageController {
    /**
     * Retrieves a list of all users from the database.
     *
     * @return A list of User instances representing all users in the system.
     */
    public static List<User> getAllUsers() {
        new AccountDatabase();

        //get all the users
        return AccountDatabase.getAllUsers();
    }
}
