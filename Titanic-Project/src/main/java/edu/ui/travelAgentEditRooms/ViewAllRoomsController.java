package edu.ui.travelAgentEditRooms;


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


    public static List<Room> getAllRooms() {
        return RoomDatabase.getAllRooms();
    }

}
