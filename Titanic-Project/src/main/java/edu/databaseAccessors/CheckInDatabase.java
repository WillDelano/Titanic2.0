package edu.databaseAccessors;

import edu.core.reservation.Reservation;
import edu.core.reservation.Room;
import edu.core.users.Guest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;


/**
 * Modifies the Reservation database to Check in and checkout users
 *
 * <p>
 * The User will look for a reservation that a user has, select the reservation, which will be
 * passed into this class, and will check in that user.
 * </p>
 *
 * @author Cole Hogan
 * @version 1.1
 * @see Room
 */
public class CheckInDatabase {
    private static final Logger logger = Logger.getLogger(ReservationDatabase.class.getName());


    public CheckInDatabase() throws SQLException {
    }
    /**
     * this method checks in a user to a Reservation. The method will return true
     * allowing the front end to print out the the user can check in. The method will
     * return false if the user cannot check in.
     *
     * @param reservation the reservation being booked
     *
     */

    public static boolean checkInGuest(Reservation reservation) {
        //connecting to the table
        String updateTableSQL = "UPDATE Reservation SET Checkedin = ? WHERE id = ?";
        LocalDate rightNow = LocalDate.now();
        if (rightNow.equals(reservation.getStartDate())) {
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
            //RoomDatabase.bookRoom(reservation.getRoom().getRoomNumber());
            return true;
        }
        else{
            logger.info("the user is trying to checkin in past registration time");
            return false;
        }
    }
    /**
     * Checks if a guest is checked in or not
     *
     * @param g: the guest being checked
     * @return Returns true or false whether the guest is checked in or not
     *
     */
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


    /**
     * Checking out a guest from a room
     *
     * @param reservation: the reservation the room is booked under
     *
     */
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
