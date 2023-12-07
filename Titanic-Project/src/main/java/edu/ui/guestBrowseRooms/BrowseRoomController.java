package edu.ui.guestBrowseRooms;

import edu.core.cruise.Country;
import edu.core.cruise.Cruise;
import edu.core.reservation.Room;
import edu.core.users.Guest;
import edu.databaseAccessors.CruiseDatabase;
import edu.databaseAccessors.RoomDatabase;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for browsing the rooms of a cruise.
 * This class serves as an intermediary between the cruise room's UI and backend logic.
 *
 * @author William Delano
 * @version 1.0
 * @see RoomDatabase
 * @see Room
 * @see BrowseRoomPage
 */
public class BrowseRoomController {
    /**
     * Retrieves a list of rooms available for a specific cruise.
     *
     * @param cruise The name or identifier of the cruise.
     * @return A list of available rooms for the specified cruise.
     */
    public static List<Room> getRooms(String cruise) {
        List<Room> unbooked = new ArrayList<>();
        List<Room> rooms = RoomDatabase.getRoomsForCruise(cruise);
        for(Room r : rooms){
            if(!r.isBooked()){
                unbooked.add(r);
            }
        }
        return unbooked;
    }

    /**
     * Retrieves information about a specific room based on its room number.
     *
     * @param roomNumber The room number of the requested room.
     * @return The Room object representing the specified room.
     */
    public static Room getRoom(int roomNumber) {
        return RoomDatabase.getRoom(roomNumber);
    }
}
