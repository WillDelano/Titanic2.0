package edu.ui.createTravelAgent;

import edu.databaseAccessors.AccountDatabase;
import edu.ui.authentication.RegisterPageController;

import javax.swing.*;
import java.util.Objects;

public class CreateTravelAgentController {

    public static void createAccount(String username, String password) {
        new AccountDatabase();

        AccountDatabase.addUser(username, password, "", "", 0, "", "Agent");
    }

    public static boolean checkForDuplicate(String username) {
        new AccountDatabase();
        return AccountDatabase.accountExists(username);
    }
}
