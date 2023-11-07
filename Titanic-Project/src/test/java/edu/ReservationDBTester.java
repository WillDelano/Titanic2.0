package edu;

import edu.core.cruise.Country;
import edu.core.reservation.Reservation;
import edu.core.reservation.Room;
import edu.core.users.Guest;
import edu.core.users.User;
import edu.databaseAccessors.ReservationDatabase;
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

public class ReservationDBTester {
    private String url = "jdbc:derby:./src/main/java/edu/Database";
    private Guest u = new Guest("wdelano", "baylor", new UniqueID().getId(), "Will", "Delano", 0, "wdelano2002@gmail.com");
    @BeforeEach
    public void setUp() {
        new ReservationDatabase();

        try (Connection connection = DriverManager.getConnection(url)) {
            String insertQuery = "INSERT INTO Reservation (username, duration, startCountry, endCountry, roomNum, startDate, endDate)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                statement.setString(1, "wdelano");
                statement.setInt(2, 10);
                statement.setString(3, "Cambodia");
                statement.setString(4, "Kyrgyzstan");
                statement.setInt(5, 4327);
                statement.setString(6, "2002-29-09");
                statement.setString(7, "2002-30-09");

                int result = statement.executeUpdate();

                if (result <= 0) {
                    System.out.println("Failed to insert test data");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void tearDown() {
        try (Connection connection = DriverManager.getConnection(url)) {
            //clear the table
            String deleteQuery = "DELETE FROM Reservation";
            try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
                statement.executeUpdate();
            }

            //reset the id incrementer to 1
            String resetId = "ALTER TABLE Reservation ALTER COLUMN id RESTART WITH 1";
            try (PreparedStatement statement = connection.prepareStatement(resetId)) {
                statement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    public static void finalCleanUp() {
        shutdownDatabase();
    }

    @Test
    public void findTest() {
        assertTrue(ReservationDatabase.hasUser(u));
    }

    @Test
    public void deleteTest() {
        //setting values to null and hardcoding id because delete only uses the id to match
        LocalDate date = LocalDate.now();
        Room room = new Room(0, 0, null, false, 0, null);
        Reservation r = new Reservation(null, room, date, date, null, null);
        r.setId(1);

        ReservationDatabase.deleteReservation(r);
        Assertions.assertFalse(ReservationDatabase.hasUser(u));
    }

    @Test
    public void getSizeTest() {
        //should only have the init data
        assertEquals(ReservationDatabase.getReservationDatabaseSize(), 1);

        //setting values to null and hardcoding id because delete only uses the id to match
        LocalDate date = LocalDate.now();
        Room room = new Room(0, 0, null, false, 0, null);
        Reservation r = new Reservation(null, room, date, date, null, null);
        r.setId(1);

        ReservationDatabase.deleteReservation(r);

        //should have no data
        assertEquals(ReservationDatabase.getReservationDatabaseSize(), 0);
    }

    @Test
    public void addReservationTest() {
        Room room = new Room(0, 0, "", false, 0, "test");
        LocalDate date = LocalDate.now();
        Country c = new Country("Test", date, date);

        Reservation r = new Reservation(u, room, date, date, c, c);

        ReservationDatabase.addReservation(r);

        //should have init data and function data
        assertEquals(ReservationDatabase.getReservationDatabaseSize(), 2);
        assertTrue((ReservationDatabase.hasReservation(r)));
    }

    @Test
    public void getReservationsTest() {
        LocalDate date = LocalDate.now();
        Room room = new Room(0, 0, "", false, 0, "test");
        Country c = new Country("Test", date, date);
        Reservation r = new Reservation(u, room, date, date, c, c);

        ReservationDatabase.addReservation(r);

        Set<Reservation> set = ReservationDatabase.getReservations(u);

        //assertEquals(set.size(), 2);

        for (Reservation res : set) {
            assertEquals(res.getUser().getUsername(), "wdelano");
        }
    }

    public static void shutdownDatabase() {
        String url = "jdbc:derby:;shutdown=true";
        try {
            DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("Database successfully shut down.");
        }
    }
}
