package edu.ui.travelAgentCreateReservations;

import edu.core.cruise.Cruise;
import edu.core.reservation.Reservation;
import edu.core.reservation.Room;
import edu.core.users.User;
import edu.databaseAccessors.AccountDatabase;
import edu.databaseAccessors.CruiseDatabase;
import edu.databaseAccessors.ReservationDatabase;
import edu.databaseAccessors.RoomDatabase;
import edu.exceptions.UserNotFoundException;

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
        return RoomDatabase.getRoomsForCruise(cruise.getName());
    }

    public static Cruise getCruise(String cruiseName) {
        return CruiseDatabase.getCruise(cruiseName);
    }

    public static User getUser(String username) throws UserNotFoundException {
        return AccountDatabase.getUser(username);
    }

    public static Room getRoom(int roomNumber, String cruiseName) {
        //TODO getRoom use cruise and roomNumber
        return RoomDatabase.getRoom(roomNumber);
    }

    public static boolean createReservation(Reservation reservation) {
        return ReservationDatabase.addReservation(reservation);
    }

    public static boolean usernameExists(String username) {
        AccountDatabase.getUser(username);

        return true;
    }
}
