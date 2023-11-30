package edu.ui.travelAgentEditRooms;

import edu.core.reservation.Room;
import edu.databaseAccessors.RoomDatabase;

public class EditRoomController {
    public static void editRoom(Room room, String bedType, int numOfBeds, boolean smokingStatus, double price) {
        //Fixme: Need some function to incorporate editing within SQL.
        RoomDatabase r =new RoomDatabase();
        r.updateRoom(room,bedType,numOfBeds,smokingStatus,price);
    }
}
