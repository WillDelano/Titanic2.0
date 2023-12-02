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

public class GuestCreateReservationPageController {
    public static String getDeparture(String cruiseName) {
        Cruise cruise = CruiseDatabase.getCruise(cruiseName);

        return String.valueOf(cruise.getDeparture());
    }

    public static List<LocalDate> getAllCheckoutDates(Room roomToReserve) {
        Cruise cruise = CruiseDatabase.getCruise(roomToReserve.getCruise());
        List<LocalDate> times = new ArrayList<>();

        for (Country c : cruise.getTravelPath()) {
            times.add(c.getArrivalTime());
        }

        return times;
    }

    public static boolean reserveRoom(Guest guest, Room room, LocalDate checkout) {
        //get the cruise
        Cruise cruise = CruiseDatabase.getCruise(room.getCruise());

        // Use the first and last countries from the cruise's travel path
        List<Country> travelPath = cruise.getTravelPath();
        Country startCountry = travelPath.get(0);
        Country endCountry = null;

        //find the country with a checkout date that matches
        for (Country c : cruise.getTravelPath()) {
            if (c.getArrivalTime() == checkout) {
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

    public static Cruise getCruise(String cruise) {
        return CruiseDatabase.getCruise(cruise);
    }
}
