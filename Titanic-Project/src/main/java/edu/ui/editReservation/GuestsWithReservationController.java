package edu.ui.editReservation;

import edu.core.users.Guest;
import edu.core.users.User;
import edu.databaseAccessors.AccountDatabase;
import edu.databaseAccessors.ReservationDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GuestsWithReservationController {
    public static List<Guest> getGuestsWithReservations() {
        new AccountDatabase();
        new ReservationDatabase();

        List<Guest> guestsWithReservations = new ArrayList<>();

        //get all the usernames of accounts with a reservation
        Set<String> usernames = ReservationDatabase.getAllUsernames();

        //for all the account usernames, get their account information and add it to the set to return
        for (String accountUsername : usernames) {
            User user = AccountDatabase.getUser(accountUsername);

            //if the user obtained is a guest, add it to the list of people with reservations
            if (user instanceof Guest) {
                guestsWithReservations.add((Guest) user);
                System.err.println(AccountDatabase.getUser(accountUsername).getUsername());
            }
        }

        return guestsWithReservations;
    }

    public static int getNumberOfReservations() {
        return ReservationDatabase.getReservationDatabaseSize();
    }
}
