package edu.ui.travelAgentEditReservations;

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

public class EditReservationController {
    public static List<Room> getAllRooms(String cruiseName) {
        return RoomDatabase.getRoomsForCruise(cruiseName);
    }

    public static void deleteReservation(Reservation reservation) {
        ReservationDatabase.deleteReservation(reservation);
    }

    public static void updateReservation(Reservation reservation, String checkout, String roomNumber) {
        ReservationDatabase.updateReservation(reservation, checkout, roomNumber);
    }

    public static Room getRoom(int roomID) {
        return RoomDatabase.getRoom(roomID);
    }

    public static List<LocalDate> getAllCheckoutDates(Reservation reservation) {
        List<LocalDate> times = new ArrayList<>();
        Cruise cruise = CruiseDatabase.getCruise(reservation.getRoom().getCruise());

        for (Country c : cruise.getTravelPath()) {
            times.add(c.getArrivalTime());
        }

        return times;
    }

    public static Cruise getCruise(String cruiseName) {
        return CruiseDatabase.getCruise(cruiseName);
    }
}
