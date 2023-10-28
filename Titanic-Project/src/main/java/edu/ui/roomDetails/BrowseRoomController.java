package edu.ui.roomDetails;

import edu.core.cruise.Cruise;
import edu.core.reservation.Room;
import edu.core.users.Guest;
import edu.database.CruiseDatabase;
import edu.database.RoomDatabase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for browsing the rooms of a cruise
 *
 * This class is an intermediary between the cruise room's ui and backend
 *
 * @author William Delano
 * @version 1.0
 * @see RoomDatabase, Room, BrowseRoomPage
 */
public class BrowseRoomController {
    public static List<Room> getRooms(String cruise) {
        return RoomDatabase.getAllRooms(cruise);
    }

    public void reserveRoom(Guest guest, Room room) {
        //adding hard coded values of the guest's reservation dates and countries because we haven't added that ui feature
        LocalDate testTime = LocalDate.of(2020, 2, 1);
        LocalDate laterTestTime = LocalDate.of(2020, 2, 2);
        guest.makeReservation(room, testTime, laterTestTime, "Iceland", "Norway");
    }
}
