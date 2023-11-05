package edu.ui.authentication;

import edu.authentication.Authentication;
import edu.databaseAccessors.AccountDatabase;

/**
 * Controller for the RegisterPage ui
 *
 * This class is an intermediary between the register ui and backend
 *
 * @author Gabriel Choi
 * @version 1.0
 * @see RegisterPage
 */
public class RegisterPageController {
    /**
     * This connects to backend and checks if the account exists.
     *
     * @param username The username of the account.
     * @return Returns true if the account exists.
     */
    public static boolean accountExists(String username){
        return AccountDatabase.accountExists(username);
    }

    /**
     * connects to backend and creates the account of the user.
     *
     * @param username   The username of the user.
     * @param password   The password of the user.
     * @param first      The first name of the user.
     * @param last       The last name of the user.
     * @param email      The email of the user.
     *
     */
    public static void createAccount(String username, String password, String first, String last, String email){
        Authentication a = new Authentication();
        a.createAccount(username, password, first, last, email);
    }
}
