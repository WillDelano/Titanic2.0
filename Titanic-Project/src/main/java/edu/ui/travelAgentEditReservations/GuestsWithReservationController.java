package edu.ui.travelAgentEditReservations;

import edu.core.users.Guest;
import edu.core.users.User;
import edu.databaseAccessors.AccountDatabase;
import edu.databaseAccessors.ReservationDatabase;
import edu.exceptions.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * The GuestsWithReservationController class provides functionality to retrieve information
 * about guests with reservations and obtain the total number of reservations.
 *
 * @author William Delano
 * @see GuestsWithReservationPage
 */
public class GuestsWithReservationController {

    /**
     * Retrieves a list of guests who have reservations.
     *
     * @return A list of Guest objects representing guests with reservations.
     * @throws UserNotFoundException If a user associated with a reservation is not found.
     */
    public static List<Guest> getGuestsWithReservations() throws UserNotFoundException {
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

    /**
     * Retrieves the total number of reservations.
     *
     * @return The total number of reservations.
     */
    public static int getNumberOfReservations() {
        return ReservationDatabase.getReservationDatabaseSize();
    }
}
