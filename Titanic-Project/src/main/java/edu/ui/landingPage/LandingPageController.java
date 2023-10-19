package edu.ui.landingPage;

import edu.core.users.User;

public class LandingPageController {
    User account;

    public LandingPageController(User account) {
        this.account = account;
    }

    public User getAccount() {
        return account;
    }
}
