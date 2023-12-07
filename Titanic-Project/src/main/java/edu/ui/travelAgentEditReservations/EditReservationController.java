package edu.ui.travelAgentEditReservations;

import edu.core.reservation.Reservation;
import edu.core.reservation.Room;
import edu.databaseAccessors.ReservationDatabase;
import edu.databaseAccessors.RoomDatabase;

import java.util.List;
import edu.core.cruise.Country;
import edu.core.cruise.Cruise;
import edu.core.reservation.Reservation;
import edu.core.reservation.Room;
import edu.databaseAccessors.CruiseDatabase;
import edu.databaseAccessors.ReservationDatabase;
import edu.databaseAccessors.RoomDatabase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for handling travel agent interactions related to reservation editing.
 *
 * This class provides methods for obtaining information needed for reservation editing,
 * such as available rooms, checkout dates, and cruise details.
 * It also handles the process of deleting and updating reservations.
 *
 * @see EditReservationPage
 */
public class EditReservationController {

    /**
     * Retrieves all available rooms for the specified cruise.
     *
     * @param cruiseName The name of the cruise for which available rooms are requested.
     * @return A list of available rooms.
     */
    public static List<Room> getAllRooms(String cruiseName) {
        return RoomDatabase.getRoomsForCruise(cruiseName);
    }

    /**
     * Deletes the given reservation from the database.
     *
     * @param reservation The reservation to be deleted.
     */
    public static void deleteReservation(Reservation reservation) {
        ReservationDatabase.deleteReservation(reservation);
    }

    /**
     * Updates the details of the given reservation, such as checkout date and room number.
     *
     * @param reservation The reservation to be updated.
     * @param checkout The new checkout date.
     * @param roomNumber The new room number.
     */
    public static void updateReservation(Reservation reservation, String checkout, String roomNumber) {
        ReservationDatabase.updateReservation(reservation, checkout, roomNumber);
    }

    /**
     * Retrieves details of a specific room by room ID.
     *
     * @param roomID The ID of the room.
     * @return The room details.
     */
    public static Room getRoom(int roomID) {
        return RoomDatabase.getRoom(roomID);
    }

    /**
     * Retrieves all checkout dates for the given reservation's cruise.
     *
     * @param reservation The reservation for which checkout dates are requested.
     * @return A list of checkout dates.
     */
    public static List<LocalDate> getAllCheckoutDates(Reservation reservation) {
        List<LocalDate> times = new ArrayList<>();
        Cruise cruise = CruiseDatabase.getCruise(reservation.getRoom().getCruise());

        for (Country c : cruise.getTravelPath()) {
            times.add(c.getArrivalTime());
        }

        return times;
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
}
