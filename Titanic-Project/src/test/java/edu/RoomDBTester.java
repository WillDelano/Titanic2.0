package edu;

import edu.core.cruise.Country;
import edu.core.reservation.Reservation;
import edu.core.reservation.Room;
import edu.core.users.Guest;
import edu.core.users.User;
import edu.databaseAccessors.ReservationDatabase;
import edu.databaseAccessors.RoomDatabase;
import edu.uniqueID.UniqueID;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class RoomDBTester {

    @Test
    public void testAddRoom() {
        Room testRoom = new Room(101, 2, "Single", false, 150.0, "CruiseA");
        RoomDatabase.addRoom(testRoom);

        Room retrievedRoom = RoomDatabase.getRoom(101);
        Assertions.assertNotNull(retrievedRoom);
        assertEquals(101, retrievedRoom.getRoomNumber());
        assertEquals(2, retrievedRoom.getNumberOfBeds());

    }
}
