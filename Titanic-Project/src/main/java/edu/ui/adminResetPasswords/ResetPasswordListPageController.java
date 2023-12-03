package edu.ui.adminResetPasswords;

import edu.core.users.User;
import edu.databaseAccessors.AccountDatabase;

import java.util.List;

public class ResetPasswordListPageController {
    public static List<User> getAllUsers() {
        new AccountDatabase();

        //get all the users
        return AccountDatabase.getAllUsers();
    }
}
