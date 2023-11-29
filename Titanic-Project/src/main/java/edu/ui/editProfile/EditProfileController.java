package edu.ui.editProfile;

import edu.core.reservation.Room;
import edu.core.users.User;
import edu.databaseAccessors.AccountDatabase;
import edu.databaseAccessors.CruiseDatabase;
import edu.databaseAccessors.RoomDatabase;


import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the edit profile ui
 *
 * This class receives calls from the edit profile ui and calls backend functions to do the work
 *
 * @author William Delano
 * @version 1.1
 */
public class EditProfileController {

    /**
     * Operation to edit the details of an account
     *
     * @param account User account to edit
     * @param email New email to change to
     * @param password New password to change to
     *
     */
    public static void editAccount(User account, String email, String password) {
        /* Pass empty strings as the remaining parameters to show that this isn't a travel agent finishing their account.
        * For more information, read the updateAccount function */
        AccountDatabase.updateAccount(account, email, password, "", "", "");
    }

    /**
     * Operation to delete an account
     *
     * @param account User account to delete
     *
     */
    public static void deleteAccount(User account) throws IOException {

        AccountDatabase.removeUser(account);
    }
}