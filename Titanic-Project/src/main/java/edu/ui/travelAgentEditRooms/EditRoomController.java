package edu.ui.travelAgentEditRooms;

import edu.core.reservation.Room;
import edu.databaseAccessors.RoomDatabase;

/**
 * Controller class for editing room information.
 * Provides a method for updating the details of a room in the database.
 *
 * @author William Delano
 * @version 1.0
 * @see Room, RoomDatabase
 */
public class EditRoomController {

    /**
     * Edits the details of a room in the database.
     *
     * @param room           The room to be edited.
     * @param bedType        The new bed type for the room.
     * @param numOfBeds      The new number of beds in the room.
     * @param smokingStatus  The new smoking status for the room.
     * @param price          The new price for the room.
     */
    public static void editRoom(Room room, String bedType, int numOfBeds, boolean smokingStatus, double price) {
        RoomDatabase r = new RoomDatabase();
        r.updateRoom(room,bedType,numOfBeds,smokingStatus,price);
    }
}
