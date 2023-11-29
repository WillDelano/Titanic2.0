package edu;

import edu.core.cruise.Country;
import edu.core.reservation.Reservation;
import edu.core.reservation.Room;
import edu.core.users.Guest;
import edu.databaseAccessors.ReservationDatabase;
import edu.databaseAccessors.CheckInDatabase;
import edu.databaseAccessors.driver;
import edu.uniqueID.UniqueID;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class CheckInDBTester implements driver {
    CheckInDatabase checkInDB = null;

    int testId;
    private Guest u = new Guest("wdelano", "baylor", new UniqueID().getId(), "Will", "Delano", 0, "wdelano2002@gmail.com");
    LocalDate date = null;
    Room room = null;
    Country c = null;
    public static Reservation r;
    @BeforeEach
    public void setUp() throws SQLException {
        LocalDate date = LocalDate.now();
        Room room = new Room(0, 0, "", false, 0, "test");
        Country c = new Country("Test", date, date);
        r = new Reservation(u, room, date, date, c, c);
    }

    @AfterEach
    public void tearDown() {
        try (Connection connection =driver.getDBConnection()) {
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

    @Test
    public void checkInTest() throws SQLException {
        ReservationDatabase.addReservation(r);
        CheckInDatabase.checkInGuest(r);
        assertEquals(CheckInDatabase.guestIsCheckedIn(u),true);
    }
    @Test
    public void checkInTestFail() throws SQLException {
        ReservationDatabase.addReservation(r);
        CheckInDatabase.checkInGuest(r);
        LocalDate date = LocalDate.now();
        Room room = new Room(2, 6, "", false, 0, "test");
        Country c = new Country("Test", date, date);
        Guest dan = new Guest("ricky", "taylorswift", new UniqueID().getId(), "Will", "Delano", 0, "wdelano2002@gmail.com");
        Reservation r2 = new Reservation(dan,room,date,date,c,c);
        ReservationDatabase.addReservation(r2);
        assertNotEquals(CheckInDatabase.guestIsCheckedIn(dan),true);
    }

    @Test public void CheckOutTest() throws SQLException{
        ReservationDatabase.addReservation(r);
        CheckInDatabase.checkInGuest(r);
        CheckInDatabase.checkOutGuest(r);
        assertNotEquals(CheckInDatabase.guestIsCheckedIn(u),true);
    }
    @Test
    public void DoesNotHaveReservation(){

    }

    @AfterAll
    public static void finalCleanUp() {
        shutdownDatabase();
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
