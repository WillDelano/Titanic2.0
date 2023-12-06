package edu.ui.travelAgentCreateReservations;

import edu.core.cruise.Country;
import edu.core.cruise.Cruise;
import edu.core.reservation.Reservation;
import edu.core.reservation.Room;
import edu.core.users.User;
import edu.databaseAccessors.AccountDatabase;
import edu.databaseAccessors.CruiseDatabase;
import edu.databaseAccessors.ReservationDatabase;
import edu.databaseAccessors.RoomDatabase;
import edu.exceptions.UserNotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for handling travel agent interactions related to reservation creation.
 *
 * This class provides methods for obtaining information needed for reservation creation,
 * such as checkout dates, available cruises, available rooms, and user details.
 * It also handles the process of creating reservations and checking the existence of a username.
 *
 * @see TravelAgentCreateReservationPage
 */
public class TravelAgentCreateReservationController {

    /**
     * Retrieves all checkout dates for the given cruise.
     *
     * @param cruise The cruise for which checkout dates are requested.
     * @return A list of checkout dates.
     */
    public static List<LocalDate> getAllCheckoutDates(Cruise cruise) {
        List<LocalDate> times = new ArrayList<>();

        for (Country c : cruise.getTravelPath()) {
            times.add(c.getArrivalTime());
            System.out.println(c.getName());
        }

        return times;
    }

    /**
     * Retrieves names of all available cruises.
     *
     * @return A list of cruise names.
     */
    public static List<String> getAllCruises() {
        List<Cruise> cruises = CruiseDatabase.getAllCruises();
        List<String> cruiseNames = new ArrayList<>();

        for (Cruise c : cruises) {
            cruiseNames.add(c.getName());
        }

        return cruiseNames;
    }

    /**
     * Retrieves all available rooms for the given cruise.
     *
     * @param cruise The cruise for which available rooms are requested.
     * @return A list of available rooms.
     */
    public static List<Room> getAllRooms(Cruise cruise) {
        return RoomDatabase.getRoomsForCruise(cruise.getName());
    }

    /**
     * Retrieves details of a specific cruise by name.
     *
     * @param cruiseName The name of the cruise.
     * @return The cruise details.
     */
    public static Cruise getCruise(String cruiseName) {
        return CruiseDatabase.getCruise(cruiseName);
    }

    /**
     * Retrieves user details by username.
     *
     * @param username The username of the user.
     * @return The user details.
     * @throws UserNotFoundException If the user is not found.
     */
    public static User getUser(String username) throws UserNotFoundException {
        return AccountDatabase.getUser(username);
    }

    /**
     * Retrieves details of a specific room by room number and cruise name.
     *
     * @param roomNumber The room number.
     * @param cruiseName The name of the cruise.
     * @return The room details.
     */
    public static Room getRoom(int roomNumber, String cruiseName) {
        //TODO getRoom use cruise and roomNumber
        return RoomDatabase.getRoom(roomNumber);
    }

    /**
     * Creates a reservation.
     *
     * @param reservation The reservation to be created.
     * @return True if the reservation is successfully created, false otherwise.
     */
    public static boolean createReservation(Reservation reservation) {
        return ReservationDatabase.addReservation(reservation);
    }

    /**
     * Checks if a username exists.
     *
     * @param username The username to be checked.
     * @return True if the username exists, false otherwise.
     */
    public static boolean usernameExists(String username) {
        try {
            AccountDatabase.getUser(username);
            return true;
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
