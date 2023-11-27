package edu.databaseAccessors;

import edu.core.reservation.Reservation;
import edu.core.users.Guest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class CheckInDatabase {


    public CheckInDatabase() throws SQLException {
    }

    public static void checkInGuest(Reservation reservation){
        //connecting to the table
        PreparedStatement statement = null;
        String updateTableSQL = "UPDATE Reservation SET Checkedin = ? WHERE id = ?";
        try(Connection dbConnection = driver.getDBConnection()){
            //creating a statement to execute what we want
            statement = dbConnection.prepareStatement(updateTableSQL);
            //the checked in is being inserted first, then the id in the updateTableSQL
            statement.setBoolean(1,true);
            statement.setInt(2,reservation.getId());
            System.out.println("Data updated successfully");
            System.out.println(updateTableSQL);
            // execute update SQL stetement
            statement.execute(updateTableSQL);
            System.out.println("User has been checked into Reservation table!");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static boolean isCheckedIn(Guest g){
        Set<Reservation> guestReservations = new HashSet<>();


    }

    public void checkOutGuest(Reservation reservation){

    }



}
