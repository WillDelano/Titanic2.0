package edu.database;

import edu.core.reservation.Reservation;
import edu.core.users.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Database to record reservations
 *
 * This class documents a collection of reservations mapped with users
 *
 * @author William Delano
 * @version 1.0
 */
public class ReservationDatabase {
    private static Map<User, Set<Reservation>> reservationDatabase;

    /**
     * Creates the database for reservations
     *
     */
    public ReservationDatabase() {
        reservationDatabase = new HashMap<>();
    }

    /**
     * Returns the reservation database
     *
     * @return The reservation database
     */
    public static Map<User, Set<Reservation>> getReservationDatabase() {
        return reservationDatabase;
    }
}
