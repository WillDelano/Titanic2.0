package edu.ui.landingPage;

import edu.core.cruise.Cruise;
import edu.core.users.User;

/**
 * Controller for the landing page ui
 *
 * This class is an intermediary between the landing page ui and backend
 *
 * @author William Delano
 * @version 1.0
 * @see User, LandingPage
 */
public class LandingPageController {
    User account;

    public LandingPageController(User account) {
        this.account = account;
    }

    public User getAccount() {
        return account;
    }
}
