package edu.ui.roomDetails;

import edu.core.reservation.Room;
import edu.core.users.Guest;
import edu.database.RoomDatabase;

import java.time.LocalDate;
import java.util.List;

public class BrowseRoomController {

    public static List<Room> getRooms() {
        return RoomDatabase.getAllRooms();
    }
    public void reserveRoom(Guest guest, Room room) {
        //adding hard coded values of the guest's reservation dates and countreis because we haven't added that ui feature
        LocalDate testTime = LocalDate.of(2020, 2, 1);
        LocalDate laterTestTime = LocalDate.of(2020, 2, 2);
        guest.makeReservation(room, testTime, laterTestTime, "Iceland", "Norway");
    }
}
