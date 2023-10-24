package edu.ui.roomDetails;

import edu.core.cruise.Country;
import edu.core.reservation.Room;
import edu.core.users.Guest;
import edu.core.users.User;

import java.time.LocalDate;

public class BrowseRoomController {
    public void reserveRoom(Guest guest, Room room) {
        LocalDate testTime = LocalDate.of(2020, 2, 1);
        LocalDate laterTestTime = LocalDate.of(2020, 2, 2);
        Country testCountry = new Country("TEST", testTime, laterTestTime);
        guest.makeReservation(room, testTime, laterTestTime, testCountry, testCountry);
    }
}
