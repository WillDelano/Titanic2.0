package edu.ui.landingPage;

import edu.core.cruise.Cruise;
import edu.core.users.User;

/**
 * Controller for the landing page UI
 *
 * This class serves as an intermediary between the landing page UI and the backend.
 *
 * @author William Delano
 * @version 1.0
 * @see User
 * @see LandingPage
 */
public class LandingPageController {
    User account;

    /**
     * Constructs a LandingPageController with the specified user account.
     *
     * @param account The user account associated with the landing page.
     */
    public LandingPageController(User account) {
        this.account = account;
    }

    /**
     * Gets the user account associated with the landing page.
     *
     * @return The user account.
     */
    public User getAccount() {
        return account;
    }
}
