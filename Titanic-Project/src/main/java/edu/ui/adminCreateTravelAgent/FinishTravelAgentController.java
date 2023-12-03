package edu.ui.adminCreateTravelAgent;

import edu.core.users.User;
import edu.databaseAccessors.AccountDatabase;

public class FinishTravelAgentController {
    public static void updateAccount(User account, String email, String first, String last) {
        new AccountDatabase();

        AccountDatabase.updateAccount(account, email, account.getPassword(), account.getUsername(), first, last);
    }
}
