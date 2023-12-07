package edu.ui.travelAgentEditRooms;

import edu.core.reservation.Room;
import edu.databaseAccessors.RoomDatabase;

import java.util.List;
import java.util.Set;

/**
 * Controller class for managing actions related to viewing and editing rooms.
 * Provides methods to retrieve room details, initiate room editing, and get a list of all rooms.
 *
 * @author William Delano
 * @version 1.0
 * @see Room, RoomDatabase, ViewAllRoomsPage, EditRoomPage
 */
public class ViewAllRoomsController {
    /**
     * Retrieves room details based on the specified row index.
     *
     * @param intRow The row index of the room in the database.
     * @return The Room object representing the specified room.
     * @see RoomDatabase#getRoom(int)
     */
    public static Room getRoom(int intRow) {
        return RoomDatabase.getRoom(intRow);
    }

    /**
     * Initiates the editing of a room by creating a new EditRoomPage instance.
     *
     * @param prevPage The previous page to return to after editing.
     * @param r        The Room object to be edited.
     * @see EditRoomPage
     */
    public static void editRoom(ViewAllRoomsPage prevPage, Room r) {
        new EditRoomPage(r, prevPage);
    }

    /**
     * Retrieves a list of all rooms from the database.
     *
     * @return A List containing all Room objects representing the rooms.
     * @see RoomDatabase#getAllRooms()
     */
    public static List<Room> getAllRooms() {
        return RoomDatabase.getAllRooms();
    }
}
