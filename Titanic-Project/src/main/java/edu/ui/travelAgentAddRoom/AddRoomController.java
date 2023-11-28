package edu.ui.travelAgentAddRoom;

import edu.core.reservation.Room;
import edu.databaseAccessors.CruiseDatabase;
import edu.databaseAccessors.RoomDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the add room ui
 *
 * This class receives calls from the add room ui and calls backend functions to do the work,
 * returning any necessary values
 *
 * @author William Delano
 * @version 1.1
 */
public class AddRoomController {
    /**
     * Controller function to add a room to the database
     *
     * @param room Room to add to the database
     *
     */
    public static void addRoomToSystem(Room room) {
        RoomDatabase.addRoom(room);
    }

    /**
     * Controller function to get all the rooms on all cruises
     *
     * @return A list of all the rooms on every cruise
     */
    public static List<String> getCruises() {
        return new ArrayList<String>(CruiseDatabase.getAllCruiseNames());
    }
}
