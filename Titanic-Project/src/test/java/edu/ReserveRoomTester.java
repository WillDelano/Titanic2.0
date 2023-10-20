package edu;

import edu.core.cruise.Country;
import edu.core.reservation.Reservation;
import edu.core.reservation.Room;
import edu.core.users.Guest;
import edu.core.users.User;
import edu.database.ReservationDatabase;
import org.junit.Test;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * This class Tests the functionality of reserving a room
 *
 * Tests the functions involved with reserving a room
 *
 * @author William Delano
 * @version 1.0
 * @see Reservation, Room, Guest, ReservationDatabase
 */
public class ReserveRoomTester {
    ReservationDatabase database = new ReservationDatabase();

    /**
     * Tester for the database
     *
     * The test creates a reservation and adds it to the database. Assertions are made to ensure that the database
     * contains the correct reservation and guest.
     *
     */
    @Test
    public void reservationDatabaseTest() {
        //ensure database is empty
        Assertions.assertTrue(ReservationDatabase.getReservationDatabase().isEmpty());

        //initializing
        Room room = new Room();
        Country usa = new Country("USA", LocalDate.of(2001, 9, 11), LocalDate.of(2011, 5, 2));
        Country germany = new Country("Germany", LocalDate.of(1939, 9, 1), LocalDate.of(1945, 7, 5));
        Guest guest = new Guest("username", "password", 0, "john", "doe", 0, "email");
        Reservation testRes = new Reservation(guest, room, LocalDate.of(2002, 9, 29), LocalDate.of(2023, 9, 29), germany, usa);

        Set<Reservation> testSet = new LinkedHashSet<>();
        testSet.add(testRes);

        //adding reservation
        ReservationDatabase.getReservationDatabase().put(guest, testSet);

        //testing that the database correctly contains the reservation
        Assertions.assertTrue(ReservationDatabase.getReservationDatabase().containsKey(guest));
        Assertions.assertTrue(ReservationDatabase.getReservationDatabase().get(guest).contains(testRes));
    }

    /**
     * Tester for makeReservation function
     *
     * The test calls makeReservation on a guest and checks if the created reservation is correctly inserted in the database.
     *
     */
    @Test
    public void makeSingleReservation() {
        //ensure database is empty
        Assertions.assertTrue(ReservationDatabase.getReservationDatabase().isEmpty());

        /*
        USA:
            Arrival Date: 09-11-2001
            Departure Date: 05-2-2011

        Germany:
            Arrival Date: 09-1-1939
            Departure Date: 05-7-1945
        */

        //initializing
        Guest guest = new Guest("username", "password", 0, "john", "doe", 0, "email");
        Room room = new Room();

        Country usa = new Country("USA", LocalDate.of(2001, 9, 11), LocalDate.of(2011, 5, 2));
        Country germany = new Country("Germany", LocalDate.of(1939, 9, 1), LocalDate.of(1945, 7, 5));

        //making reservation
        Reservation testReservation = guest.makeReservation(room, LocalDate.of(2002, 9, 29), LocalDate.of(2023, 9, 29), germany, usa);

        //Check if the database contains the user
        Assertions.assertTrue(ReservationDatabase.getReservationDatabase().containsKey(guest));

        //Check if the database contains the user's reservation
        Assertions.assertTrue(ReservationDatabase.getReservationDatabase().get(guest).contains(testReservation));
    }

    /**
     * Tester for makeReservation function
     *
     * The test calls makeReservation multiple times on a guest and checks if the created reservations are correctly
     * inserted in the database.
     *
     */
    @Test
    public void makeMultipleReservations() {
        //ensure database is empty
        Assertions.assertTrue(ReservationDatabase.getReservationDatabase().isEmpty());

        /*
        USA:
            Arrival Date: 09-11-2001
            Departure Date: 05-2-2011

        Germany:
            Arrival Date: 09-1-1939
            Departure Date: 05-7-1945
        */

        //initializing reservation 1
        Guest guest = new Guest("username", "password", 0, "john", "doe", 0, "email");
        Room room = new Room();
        Country usa = new Country("USA", LocalDate.of(2001, 9, 11), LocalDate.of(2011, 5, 2));
        Country germany = new Country("Germany", LocalDate.of(1939, 9, 1), LocalDate.of(1945, 7, 5));
        //making reservation1
        Reservation testReservation = guest.makeReservation(room, LocalDate.of(2002, 9, 29), LocalDate.of(2023, 9, 29), germany, usa);


        //initializing reservation 2
        Room room2 = new Room();
        Country france = new Country("France", LocalDate.of(2001, 9, 11), LocalDate.of(2011, 5, 2));
        Country algeria = new Country("Algeria", LocalDate.of(1939, 9, 1), LocalDate.of(1945, 7, 5));
        //making reservation2
        Reservation testReservation2 = guest.makeReservation(room, LocalDate.of(2002, 9, 29), LocalDate.of(2023, 9, 29), france, algeria);


        //initializing reservation 3
        Room room3 = new Room();
        Country cuba = new Country("Cuba", LocalDate.of(2001, 9, 11), LocalDate.of(2011, 5, 2));
        Country bahamas = new Country("Bahamas", LocalDate.of(1939, 9, 1), LocalDate.of(1945, 7, 5));
        //making reservation3
        Reservation testReservation3 = guest.makeReservation(room, LocalDate.of(2002, 9, 29), LocalDate.of(2023, 9, 29), cuba, bahamas);


        //attempting a duplicate reservation1
        Room room1 = new Room();
        Country usa1 = new Country("USA", LocalDate.of(2001, 9, 11), LocalDate.of(2011, 5, 2));
        Country germany1 = new Country("Germany", LocalDate.of(1939, 9, 1), LocalDate.of(1945, 7, 5));
        //making reservationDup
        Reservation testReservationDup = guest.makeReservation(room, LocalDate.of(2002, 9, 29), LocalDate.of(2023, 9, 29), germany1, usa1);

        //Check if the database contains the user
        Assertions.assertTrue(ReservationDatabase.getReservationDatabase().containsKey(guest));

        //Check if the database contains all three reservations
        Assertions.assertTrue(ReservationDatabase.getReservationDatabase().get(guest).contains(testReservation));
        Assertions.assertTrue(ReservationDatabase.getReservationDatabase().get(guest).contains(testReservation2));
        Assertions.assertTrue(ReservationDatabase.getReservationDatabase().get(guest).contains(testReservation3));

        //Check for duplicate
        Assertions.assertFalse(ReservationDatabase.getReservationDatabase().get(guest).contains(testReservationDup));
    }

    // Test to validate date range of reservation
    @Test
    public void testInvalidDateRange() {
        Guest guest = new Guest("user", "pass", 0, "first", "last", 0, "email");

        Room room = new Room();

        Country usa = new Country("USA", LocalDate.of(2001, 9, 11), LocalDate.of(2011, 5, 2));

        // 1. End Date is before Start Date
        Reservation invalidReservation1 = new Reservation(guest, room, LocalDate.of(2023, 9, 29), LocalDate.of(2002, 9, 29), usa, usa);
        Assertions.assertFalse(isValidDateRange(invalidReservation1.getStartDate(), invalidReservation1.getEndDate()));

        // 2. Start and End Date are the same
        Reservation invalidReservation2 = new Reservation(guest, room, LocalDate.of(2022, 9, 29), LocalDate.of(2022, 9, 29), usa, usa);
        Assertions.assertFalse(isValidDateRange(invalidReservation2.getStartDate(), invalidReservation2.getEndDate()));

        // 3. Checking a valid date range for comparison
        Reservation validReservation = new Reservation(guest, room, LocalDate.of(2002, 9, 29), LocalDate.of(2023, 9, 29), usa, usa);
        Assertions.assertTrue(isValidDateRange(validReservation.getStartDate(), validReservation.getEndDate()));
    }

    public boolean isValidDateRange(LocalDate startDate, LocalDate endDate) {
        //checking if the end date is after the start date for validity
        // and if they aren't the same
        return !endDate.isBefore(startDate) && !startDate.isEqual(endDate);
    }
}