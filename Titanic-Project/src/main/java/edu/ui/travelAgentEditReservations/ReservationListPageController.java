package edu.ui.travelAgentEditReservations;

import edu.core.reservation.Reservation;
import edu.core.users.Guest;
import edu.databaseAccessors.ReservationDatabase;

import java.sql.SQLException;
import java.util.Set;

public class ReservationListPageController {
    public static Set<Reservation> getReservationList(Guest guest) throws SQLException {
       return ReservationDatabase.getReservations(guest);
    }
}
