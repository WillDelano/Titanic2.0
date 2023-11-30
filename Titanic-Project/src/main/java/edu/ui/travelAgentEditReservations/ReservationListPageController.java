package edu.ui.travelAgentEditReservations;

import edu.core.reservation.Reservation;
import edu.core.users.Guest;
import edu.databaseAccessors.ReservationDatabase;

import java.util.Set;

public class ReservationListPageController {
    public static Set<Reservation> getReservationList(Guest guest) {
       return ReservationDatabase.getReservations(guest);
    }
}
