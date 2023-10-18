package edu;

import edu.core.users.Guest;
import org.junit.Test;

public class ReserveRoomTester {
    @Test
    void makeReservation() {
        Guest guest = new Guest("username", "password", 0L, "john", "doe", 0);

        guest.requestReservation(0);
    }
}