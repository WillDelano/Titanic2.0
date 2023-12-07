package edu.ui.travelAgentEditReservations;

import edu.core.reservation.Reservation;
import edu.core.users.Guest;

import edu.databaseAccessors.ReservationDatabase;
import edu.exceptions.NoMatchingReservationException;

import java.sql.SQLException;
import java.util.Set;

/**
 * Controller class for managing reservation-related operations in the ReservationListPage UI.
 * Provides methods for retrieving reservation lists, getting individual reservations,
 * checking in guests, and checking out guests.
 *
 * @author William Delano
 * @version 1.0
 * @see Reservation, Guest, ReservationDatabase
 */
public class ReservationListPageController {

    /**
     * Retrieves a set of reservations associated with a specific guest.
     *
     * @param guest The guest for whom reservations are to be retrieved.
     * @return A set of reservations associated with the specified guest.
     * @throws SQLException If there is an issue accessing the database.
     */
    public static Set<Reservation> getReservationList(Guest guest) throws SQLException {
        return ReservationDatabase.getReservations(guest);
    }

    /**
     * Retrieves a specific reservation based on the room number.
     *
     * @param roomNumber The room number for which the reservation is to be retrieved.
     * @return The reservation associated with the specified room number.
     * @throws NoMatchingReservationException If no matching reservation is found.
     */
    public static Reservation getReservation(int roomNumber) throws NoMatchingReservationException {
        return ReservationDatabase.getReservationByRoom(roomNumber);
    }

    /**
     * Checks in a guest for the specified reservation.
     *
     * @param reservation The reservation for which the guest is to be checked in.
     * @return True if the check-in operation is successful, false otherwise.
     */
    public static boolean CheckInGuest(Reservation reservation){
        boolean checkedIn;

        checkedIn = ReservationDatabase.checkInGuest(reservation);
        return checkedIn;
    }

    /**
     * Checks out a guest from the specified reservation.
     *
     * @param reservation The reservation from which the guest is to be checked out.
     */
    public static void CheckOutGuest(Reservation reservation){
        ReservationDatabase.deleteReservation(reservation);
    }
}
