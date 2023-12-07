package edu;

import edu.core.cruise.Country;
import edu.core.reservation.Reservation;
import edu.core.reservation.Room;
import edu.core.users.Guest;
import edu.core.users.User;
import edu.databaseAccessors.DatabaseProperties;
import edu.databaseAccessors.ReservationDatabase;
import edu.databaseAccessors.RoomDatabase;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class RoomDBTester {



    @Test
    public void testAddRoom() {
        Room testRoom = new Room(10001, 2, "Single", false, 150.0, "CruiseA");
        RoomDatabase.addRoom(testRoom);

        Room retrievedRoom = RoomDatabase.getRoom(101);
        Assertions.assertNotNull(retrievedRoom);
        assertEquals(101, retrievedRoom.getRoomNumber());
        assertEquals(2, retrievedRoom.getNumberOfBeds());

    }

    @Test
    public void roomExistsFails(){
        Room testRoom = new Room(20001, 3,"Double", false,150.0,"CruiseB");
        assertNotEquals(RoomDatabase.roomExists(20001),true);
    }

    @Test
    public void roomExistsTrue(){
        assertTrue(RoomDatabase.roomExists(102));
    }

    @Test
    public void getRoomForCruiseTrue(){
        assertEquals(RoomDatabase.getRoomsForCruise("Alaskan").size(),57);
    }

    @Test
    public void testGetAllRooms(){
        assertEquals(RoomDatabase.getAllRooms().size(),148);
    }

    @AfterEach
    public void tearDown() {
        String url = DatabaseProperties.url;
        try (Connection connection = DriverManager.getConnection(url)) {
            String deleteQuery = "DELETE FROM ROOMS WHERE roomNumber = ?";
            try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
                statement.setInt(1, 10001);
                int deleted;
                deleted = statement.executeUpdate();

                if (deleted <= 0) {
                    System.out.println("Failed to delete data");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
