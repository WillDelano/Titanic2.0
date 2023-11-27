package edu.databaseAccessors;

import edu.core.reservation.Reservation;
import edu.core.users.Guest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class CheckInDatabase {


    public CheckInDatabase() throws SQLException {
    }

    public static boolean checkInGuest(Reservation reservation) {
        //connecting to the table
        String updateTableSQL = "UPDATE Reservation SET Checkedin = ? WHERE id = ?";

        try (Connection dbConnection = driver.getDBConnection();
             PreparedStatement statement = dbConnection.prepareStatement(updateTableSQL)) {
            //the checked in is being inserted first, then the id in the updateTableSQL
            statement.setBoolean(1, true);
            statement.setInt(2, reservation.getId());

            System.out.println("Data updated successfully");
            System.out.println(updateTableSQL);

            // execute update SQL statement
            statement.executeUpdate();  // Use executeUpdate instead of execute

            System.out.println("User has been checked out of Reservation table!");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return true;
    }

    public static boolean guestIsCheckedIn(Guest g) throws SQLException {
        Set<Reservation> guestReservations = new HashSet<>();
        boolean checkedIn = false;
        int count = 0;

        //create the connection to the db
        try (Connection connection = driver.getDBConnection()) {
            //command to select all rows from db matching the guest id
            String selectAll = "SELECT * FROM Reservation WHERE Username = ?";
            //preparing the statement
            try (PreparedStatement statement = connection.prepareStatement(selectAll)) {
                //set the first parameter to search for (id) to the guest's id
                statement.setString(1, g.getUsername());
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        if (resultSet.getBoolean("checkedin")) {
                            checkedIn = true;
                            count++;
                        }
                    }
                }
            }

        }
        if (checkedIn){
            System.out.println("you have two reservations checked in for the same user");
        }
        return checkedIn;
    }



    public static void checkOutGuest(Reservation reservation){
        //connecting to the table
        String updateTableSQL = "UPDATE Reservation SET Checkedin = ? WHERE id = ?";

        try (Connection dbConnection = driver.getDBConnection();
             PreparedStatement statement = dbConnection.prepareStatement(updateTableSQL)) {
            //the checked in is being inserted first, then the id in the updateTableSQL
            statement.setBoolean(1, false);
            statement.setInt(2, reservation.getId());

            System.out.println("Data updated successfully");
            System.out.println(updateTableSQL);

            // execute update SQL statement
            statement.executeUpdate();  // Use executeUpdate instead of execute

            System.out.println("User has been checked into Reservation table!");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }




}
