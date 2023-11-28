package edu.ui.travelAgentCreateReservations;

import edu.core.cruise.Cruise;
import edu.core.reservation.Room;
import edu.databaseAccessors.CruiseDatabase;
import edu.databaseAccessors.RoomDatabase;

import java.util.ArrayList;
import java.util.List;

public class TravelAgentCreateReservationController {
    public static List<Cruise> getAllCheckoutDates(Cruise cruise) {
        return new ArrayList<>();
    }

    public static List<String> getAllCruises() {
        List<Cruise> cruises = CruiseDatabase.getAllCruises();
        List<String> cruiseNames = new ArrayList<>();

        for (Cruise c : cruises) {
            cruiseNames.add(c.getName());
        }

        return cruiseNames;
    }

    public static List<Room> getAllRooms(Cruise cruise) {
        return RoomDatabase.getAllRooms(cruise.getName());
    }
}
