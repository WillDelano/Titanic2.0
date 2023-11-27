package edu.ui.resetPassword;

import edu.core.users.Guest;
import edu.core.users.User;
import edu.databaseAccessors.AccountDatabase;
import edu.databaseAccessors.ReservationDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ResetPasswordListPageController {
    public static List<User> getAllUsers() {
        new AccountDatabase();

        //get all the users
        return AccountDatabase.getAllUsers();
    }
}
