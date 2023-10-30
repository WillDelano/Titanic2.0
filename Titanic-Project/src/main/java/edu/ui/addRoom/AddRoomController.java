package edu.ui.addRoom;

import edu.core.reservation.Room;
import edu.database.CruiseDatabase;
import edu.database.RoomDatabase;

import java.util.ArrayList;
import java.util.List;

public class AddRoomController {
    public static void addRoomToSystem(Room room) {
        RoomDatabase.addRoom(room);
    }

    public static List<String> getCruises() {
        return new ArrayList<String>(CruiseDatabase.getAllCruiseNames());
    }
}
