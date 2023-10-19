package edu;

import edu.core.reservation.Room;
import edu.core.users.Guest;
import org.junit.Test;

public class ReserveRoomTester {
    @Test
    void makeReservation() {
        Guest guest = new Guest("username", "password", 0, "john", "doe", 0);

        Room test = new Room();
        guest.makeReservation(test);
    }
}