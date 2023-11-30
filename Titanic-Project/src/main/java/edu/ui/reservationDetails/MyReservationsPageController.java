package edu.ui.reservationDetails;


import edu.core.reservation.Reservation;
import edu.databaseAccessors.ReservationDatabase;
import edu.exceptions.NoMatchingReservationException;
import edu.ui.reservationListInterface.ReservationListInterface;
import edu.ui.editReservation.EditReservationPage;

public class MyReservationsPageController {
    public static void selectReservation(ReservationListInterface prevPage, Reservation r) {
        new EditReservationPage(prevPage, r.getRoom().getCruise(), r);
        System.out.println(r.getId());
    }

    public static Reservation getReservation(int roomNumber) throws NoMatchingReservationException {
        return ReservationDatabase.getReservationByRoom(roomNumber);
    }
}

