package edu.ui.guestReservationList;


import edu.core.reservation.Reservation;
import edu.databaseAccessors.ReservationDatabase;
import edu.exceptions.NoMatchingReservationException;
import edu.ui.reservationListInterface.ReservationListInterface;
import edu.ui.travelAgentEditReservations.EditReservationPage;

public class MyReservationsPageController {
    public static void editReservation(ReservationListInterface prevPage, Reservation r) {
        new EditReservationPage(prevPage, r.getRoom().getCruise(), r);
    }

    public static Reservation getReservation(int roomNumber) throws NoMatchingReservationException {
        return ReservationDatabase.getReservationByRoom(roomNumber);
    }
}

