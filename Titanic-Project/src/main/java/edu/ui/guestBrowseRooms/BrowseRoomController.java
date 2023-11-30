package edu.ui.guestBrowseRooms;

import edu.core.cruise.Country;
import edu.core.cruise.Cruise;
import edu.core.reservation.Room;
import edu.core.users.Guest;
import edu.databaseAccessors.CruiseDatabase;
import edu.databaseAccessors.RoomDatabase;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Controller for browsing the rooms of a cruise
 *
 * This class is an intermediary between the cruise room's ui and backend
 *
 * @author William Delano
 * @version 1.0
 * @see RoomDatabase, Room, BrowseRoomPage
 */
public class BrowseRoomController {
    public static List<Room> getRooms(String cruise) {
        return RoomDatabase.getRoomsForCruise(cruise);
    }

    public void reserveRoom(Guest guest, Room room) {
        // Fetch the cruise name from the room
        String cruiseName = room.getCruise();

        // Retrieve all cruises and find the matching one
        List<Cruise> cruises = CruiseDatabase.getAllCruises();
        Cruise matchingCruise = cruises.stream()
                .filter(cruise -> cruise.getName().equals(cruiseName))
                .findFirst()
                .orElse(null);

        if (matchingCruise == null || matchingCruise.getTravelPath().size() < 2) {
            // Handle the case where the cruise is not found or doesn't have enough countries
            JOptionPane.showMessageDialog(null, "Error: Cruise data is not properly set up.");
            return;
        }

        // Use the first and last countries from the cruise's travel path
        List<Country> travelPath = matchingCruise.getTravelPath();
        Country startCountry = travelPath.get(0);
        Country endCountry = travelPath.get(travelPath.size() - 1);

        LocalDate startDate = LocalDate.now(); // Use current date as start date for reservation
        LocalDate endDate = startDate.plusDays(1); // Example end date (one day after start date)

        // Make the reservation
        guest.makeReservation(room, startDate, endDate, startCountry.getName(), endCountry.getName());
    }

}