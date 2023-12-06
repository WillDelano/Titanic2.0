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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReservationDBTester {
private String url = DatabaseProperties.url;
    private Guest c = new Guest("chogan","baylor","cole","hogan",100,"yo@gmail.com");
    Reservation r;
    int id;
    @BeforeEach
    public void setUp() {

        try (Connection connection = DriverManager.getConnection(url)) {
            //command to insert all information
            String insert = "INSERT INTO Reservation (username, duration, startDate, endDate, startCountry, endCountry, roomNum) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(insert)) {
                statement.setString(1,"chogan" );
                statement.setLong(2, 0);
                statement.setString(3, "2023-12-12");
                statement.setString(4, "2023-12-12");
                statement.setString(5, "Kingston, Jamaica");
                statement.setString(6, "Kingston, Jamaica");
                statement.setInt(7, 9999);

                int inserted = statement.executeUpdate();
                r = ReservationDatabase.getReservationByRoom(9999);
                id = r.getId();
                if (inserted <= 0) {
                    System.out.println("Failed to insert data");
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
            String deleteQuery = "DELETE FROM Reservation WHERE roomNumber = 9999";
            try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
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
    public void getReservations(){
        assertTrue(ReservationDatabase.hasReservation(r));
    }

    @Test
    public void deleteTest() {
        //setting values to null and hardcoding id because delete only uses the id to match
        LocalDate date = LocalDate.now();
        Room room = new Room(0, 0, null, false, 0, null);
        Country u = new Country("Kingston, Jamaica", date, date);
        Reservation r2 = new Reservation(id+1,c, room, date, date,u,u);
        r.setId(1);

        ReservationDatabase.deleteReservation(r);
        Assertions.assertFalse(ReservationDatabase.hasUser(c));
    }

    @Test
    public void getSizeTest() {
        //should only have the init data
        assertEquals(ReservationDatabase.getReservationDatabaseSize(), 1);

        //setting values to null and hardcoding id because delete only uses the id to match
        LocalDate date = LocalDate.now();
        Room room = new Room(0, 0, null, false, 0, null);
        Reservation r2 = new Reservation(id+1,c, room, date, date, null, null);


        ReservationDatabase.deleteReservation(r2);

        //should have no data
        assertEquals(ReservationDatabase.getReservationDatabaseSize(), 0);
    }

    @Test
    public void addReservationTest() {
        Room room = new Room(0, 0, "", false, 0, "test");
        LocalDate date = LocalDate.of(2023,12,12);
        Country u= new Country("Test", date, date);

        Reservation r2 = new Reservation(id+1,c, room, date, date, u, u);

        ReservationDatabase.addReservation(r2);

        //should have init data and function data
        assertEquals(ReservationDatabase.getReservationDatabaseSize(), 2);
        assertTrue((ReservationDatabase.hasReservation(r2)));
    }

    @Test
    public void getReservationsTest() {
        LocalDate date = LocalDate.now();
        Room room = new Room(0, 0, "", false, 0, "test");
        Country u = new Country("Kingston, Jamaica", date, date);
        Reservation r2 = new Reservation(id+1,c, room, date, date, u, u);

        ReservationDatabase.addReservation(r2);

        Set<Reservation> set = ReservationDatabase.getReservations(c);

        assertEquals(set.size(), 2);

        for (Reservation res : set) {
            assertEquals(res.getUser().getUsername(), "chogan");
        }
        ReservationDatabase.deleteReservation(r2);
    }

    public static void shutdownDatabase() {
        String url = "jdbc:derby:;shutdown=true";
        try {
            DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("Database shut down successfully");
        }
    }
}
