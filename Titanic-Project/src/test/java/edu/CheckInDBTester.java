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

public class CheckInDBTester implements driver {
    CheckInDatabase checkInDB = null;

    int testId;
    private Guest u = new Guest("wdelano", "baylor", new UniqueID().getId(), "Will", "Delano", 0, "wdelano2002@gmail.com");
    LocalDate date = null;
    Room room = null;
    Country c = null;
    Reservation r = null;
    @BeforeEach
    public void setUp() throws SQLException {
        new ReservationDatabase();
         checkInDB = new CheckInDatabase();

        try (Connection connection = driver.getDBConnection()) {
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
        LocalDate date = LocalDate.now();
        Room room = new Room(0, 0, "", false, 0, "test");
        Country c = new Country("Test", date, date);
        Reservation r = new Reservation(u, room, date, date, c, c);
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

    @Test
    public void checkInTest() throws SQLException {
        ReservationDatabase.addReservation(r);
        CheckInDatabase.checkInGuest(r);

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
