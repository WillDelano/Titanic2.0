package edu.ui.editReservation;

import edu.core.reservation.Reservation;
import edu.core.users.Guest;
import edu.database.ReservationDatabase;

import java.util.Set;

public class ReservationListPageController {
    public static Set<Reservation> getReservationList(Guest guest) {
       return ReservationDatabase.getReservations(guest);
    }
}
