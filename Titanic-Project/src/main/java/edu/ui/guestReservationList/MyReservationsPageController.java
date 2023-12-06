package edu.ui.guestReservationList;

import edu.core.reservation.Reservation;
import edu.databaseAccessors.ReservationDatabase;
import edu.exceptions.NoMatchingReservationException;
import edu.ui.reservationListInterface.ReservationListInterface;
import edu.ui.travelAgentEditReservations.EditReservationPage;

/**
 * Controller class for managing actions related to the MyReservationsPage.
 *
 * This class provides methods to edit a reservation and retrieve a reservation by room number.
 *
 * @see MyReservationsPage
 * @see EditReservationPage
 * @see ReservationListInterface
 * @see ReservationDatabase
 * @see NoMatchingReservationException
 */
public class MyReservationsPageController {
    /**
     * Opens the Edit Reservation Page for the given reservation.
     *
     * @param prevPage The previous page, which implements ReservationListInterface.
     * @param r The reservation to be edited.
     */
    public static void editReservation(ReservationListInterface prevPage, Reservation r) {
        new EditReservationPage(prevPage, r.getRoom().getCruise(), r);
    }

    /**
     * Retrieves a reservation based on the room number.
     *
     * @param roomNumber The room number associated with the reservation.
     * @return The reservation with the specified room number.
     * @throws NoMatchingReservationException If no matching reservation is found.
     */
    public static Reservation getReservation(int roomNumber) throws NoMatchingReservationException {
        return ReservationDatabase.getReservationByRoom(roomNumber);
    }
}
