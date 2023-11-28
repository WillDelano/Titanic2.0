package edu.ui.travelAgentEditReservations;

import edu.core.reservation.Reservation;
import edu.core.reservation.Room;
import edu.databaseAccessors.ReservationDatabase;
import edu.databaseAccessors.RoomDatabase;

import java.util.List;

public class EditReservationController {
    public static List<Room> getAllRooms(String cruise) {
        return RoomDatabase.getAllRooms(cruise);
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
}
