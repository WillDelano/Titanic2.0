package edu.ui.travelAgentEditReservations;

import edu.core.reservation.Reservation;
import edu.core.users.Guest;
import edu.databaseAccessors.CheckInDatabase;
import edu.databaseAccessors.ReservationDatabase;
import edu.exceptions.NoMatchingReservationException;

import java.sql.SQLException;
import java.util.Set;

public class ReservationListPageController {
    public static Set<Reservation> getReservationList(Guest guest) throws SQLException {
       return ReservationDatabase.getReservations(guest);
    }

    public static Reservation getReservation(int RoomNumber) throws NoMatchingReservationException {
        return ReservationDatabase.getReservationByRoom(RoomNumber);
    }

    public static boolean CheckInGuest(Reservation reservation){
        boolean checkedIn;

        checkedIn = CheckInDatabase.checkInGuest(reservation);
        return checkedIn;
    }

    public static void CheckOutGuest(Reservation reservation){
        CheckInDatabase.checkOutGuest(reservation);

    }

}
