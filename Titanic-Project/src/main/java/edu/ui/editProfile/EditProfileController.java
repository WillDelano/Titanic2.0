package edu.ui.editProfile;

import edu.core.users.User;
import edu.databaseAccessors.AccountDatabase;
import edu.databaseAccessors.CruiseDatabase;
import edu.databaseAccessors.RoomDatabase;

import java.util.ArrayList;
import java.util.List;

public class EditProfileController {
    public static void editAccount(User account, String email, String password) {
        //Need some function to incorporate editing both email and password. If only one was changed it wouldn't affect
        //the other so updating both is acceptable
        //AccountDatabase.updateAccount(account, email, password);
    }

    public static void deleteAccount(User account) {
        //AccountDatabase.removeUser(account);
    }
}
