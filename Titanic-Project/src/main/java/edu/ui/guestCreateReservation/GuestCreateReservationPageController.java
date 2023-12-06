package edu.ui.guestCreateReservation;

import edu.core.cruise.Country;
import edu.core.cruise.Cruise;
import edu.core.reservation.Room;
import edu.core.users.Guest;
import edu.databaseAccessors.CruiseDatabase;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for managing reservation creation for guests.
 * Handles interactions between the guest reservation UI and the underlying data model.
 * Provides methods to retrieve information and create reservations.
 *
 * @author [Your Name]
 * @version 1.0
 * @see GuestCreateReservationPage, CruiseDatabase, Guest, Room, Cruise, Country
 */
public class GuestCreateReservationPageController {

    /**
     * Retrieves the departure date of a cruise.
     *
     * @param cruiseName The name of the cruise.
     * @return The departure date of the specified cruise.
     */
    public static String getDeparture(String cruiseName) {
        Cruise cruise = CruiseDatabase.getCruise(cruiseName);

        return String.valueOf(cruise.getDeparture());
    }

    /**
     * Retrieves all checkout dates for a given room's associated cruise.
     *
     * @param roomToReserve The room for which to retrieve checkout dates.
     * @return A list of checkout dates for the associated cruise.
     */
    public static List<LocalDate> getAllCheckoutDates(Room roomToReserve) {
        Cruise cruise = CruiseDatabase.getCruise(roomToReserve.getCruise());
        List<LocalDate> times = new ArrayList<>();

        System.out.println(times.size());

        for (Country c : cruise.getTravelPath()) {
            times.add(c.getArrivalTime());
        }

        return times;
    }

    /**
     * Reserves a room for a guest based on the specified checkout date.
     *
     * @param guest The guest making the reservation.
     * @param room The room to be reserved.
     * @param checkout The checkout date for the reservation.
     * @return True if the reservation is successfully made, false otherwise.
     */
    public static boolean reserveRoom(Guest guest, Room room, LocalDate checkout) {
        //get the cruise
        Cruise cruise = CruiseDatabase.getCruise(room.getCruise());

        // Use the first and last countries from the cruise's travel path
        List<Country> travelPath = cruise.getTravelPath();
        Country startCountry = travelPath.get(0);
        Country endCountry = null;

        //find the country with a checkout date that matches
        for (Country c : cruise.getTravelPath()) {
            if (c.getArrivalTime().equals(checkout)) {
                endCountry = c;
            }
        }

        if (endCountry == null) {
            System.err.println("Could not find a matching country for the checkout. See GuestCreateReservationPageController");
        }

        LocalDate startDate = cruise.getDeparture(); // Use current date as start date for reservation

        // Make the reservation
        assert endCountry != null;
        guest.makeReservation(room, startDate, checkout, startCountry.getName(), endCountry.getName());
        return true;
    }

    /**
     * Retrieves a Cruise object based on the provided cruise name.
     *
     * @param cruise The name of the cruise.
     * @return The Cruise object associated with the specified cruise name.
     */
    public static Cruise getCruise(String cruise) {
        return CruiseDatabase.getCruise(cruise);
    }
}
