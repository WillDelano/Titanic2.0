package edu;

import edu.core.reservation.Reservation;
import edu.core.uniqueID.UniqueID;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.HashSet;
import java.util.Set;

/**
 * This class Tests the functionality of creating a uniqueID
 *
 * Tests the functions involved with creating a uniqueID
 *
 * @author William Delano
 * @version 1.0
 * @see Reservation , Room, Guest, ReservationDatabase
 */
public class UniqueIDTester {
    /**
     * Tester for the uniqueID
     *
     * The test creates a reservation and adds it to the database. Assertions are made to ensure that the database
     * contains the correct reservation and guest.
     *
     */
    @Test
    public void UniqueIDTest() {
        Set<Integer> idSet = new HashSet<>();

        //for 1000 iterations, assert that no duplicate is added
        for (int i = 0; i < 1000; i++) {
           Assertions.assertTrue(idSet.add(new UniqueID().getId()), "A duplicate id has occured.");
        }
    }
}
