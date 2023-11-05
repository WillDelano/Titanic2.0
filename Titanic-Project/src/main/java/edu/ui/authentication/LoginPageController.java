package edu.ui.authentication;

import edu.authentication.Authentication;
import edu.databaseAccessors.AccountDatabase;
import edu.ui.landingPage.GuestLandingPage;
import edu.ui.landingPage.TravelAgentLandingPage;

import java.util.Objects;

/**
 * Controller for the LoginPage ui
 *
 * This class is an intermediary between the login ui and backend
 *
 * @author Gabriel Choi
 * @version 1.0
 * @see LoginPage
 */
public class LoginPageController {
    /**
     * This connects to backend and logs the user into the system.
     *
     * @param username The username of the account.
     * @param password The password of the account
     * @return Returns true if the login is successful.
     */
    public static boolean loginUser(String username, String password){
        boolean flag = false;
        Authentication a = new Authentication();
        new AccountDatabase();

        if (a.login(username, password)) {
            flag = true;

            if (Objects.equals(AccountDatabase.getAccountType(username), "Guest")) {
                GuestLandingPage landingPage = new GuestLandingPage();
                landingPage.showLandingPage(AccountDatabase.getUser(username));
            }
            else if (Objects.equals(AccountDatabase.getAccountType(username), "Agent")) {
                TravelAgentLandingPage landingPage = new TravelAgentLandingPage();
                landingPage.showLandingPage(AccountDatabase.getUser(username));
            }
        }
        return flag;
    }
}
