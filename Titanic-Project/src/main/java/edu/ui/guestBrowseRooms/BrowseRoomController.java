package edu.ui.guestBrowseRooms;

import edu.core.cruise.Country;
import edu.core.cruise.Cruise;
import edu.core.reservation.Room;
import edu.core.users.Guest;
import edu.databaseAccessors.CruiseDatabase;
import edu.databaseAccessors.RoomDatabase;

import javax.swing.*;
import java.time.LocalDate;
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
        return RoomDatabase.getRoomsForCruise(cruise);
    }
}