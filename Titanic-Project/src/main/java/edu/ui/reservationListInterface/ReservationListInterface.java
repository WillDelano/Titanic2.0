package edu.ui.reservationListInterface;

import edu.ui.guestReservationList.MyReservationsPage;
import edu.ui.travelAgentEditReservations.ReservationListPage;

/**
 * Allows the edit reservation ui to be called from different classes
 *
 * This class is implemented by two different ui classes. This allows them
 * to both call the editReservation ui and pass ReservationListInterface as the
 * prevPage object. Without this, the constructor in editReservation would only
 * be able to implement a specific class, meaning it would not be possible for
 * any other class besides the explicitly declared class to create an editReservation
 * page.
 *
 * @author William Delano
 * @version 1.0
 * @see MyReservationsPage
 * @see ReservationListPage
 */
public interface ReservationListInterface {
    void show();
}
