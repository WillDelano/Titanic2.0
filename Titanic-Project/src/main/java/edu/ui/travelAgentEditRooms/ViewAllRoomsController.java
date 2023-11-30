package edu.ui.travelAgentEditRooms;

import edu.core.cruise.Cruise;
import edu.core.reservation.Room;
import edu.databaseAccessors.RoomDatabase;

import java.util.List;
import java.util.Set;

public class ViewAllRoomsController {
    public static Room getRoom(int intRow) {
        return RoomDatabase.getRoom(intRow);
    }

    public static void editRoom(ViewAllRoomsPage prevPage, Room r) {
        new EditRoomPage(r, prevPage);
    }

    public static List<Room> getAllRooms(Cruise cruise) {
        return RoomDatabase.getAllRooms(cruise.getName());
    }
}
