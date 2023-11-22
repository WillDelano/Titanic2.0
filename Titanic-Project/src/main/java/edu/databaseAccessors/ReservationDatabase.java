package edu.databaseAccessors;

import edu.core.cruise.Country;
import edu.core.reservation.Reservation;
import edu.core.users.Guest;
import edu.core.reservation.Room;
import edu.core.users.User;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Database to record reservations
 *
 * This class documents a collection of reservations and reads/writes to the database file
 *
 * @author William Delano
 * @version 1.0
 */
public class ReservationDatabase {
    private static final String url = "jdbc:derby:C:/Users/vince/IdeaProjects/titanic2/Titanic2.0/Titanic-Project/src/main/java/edu/Database";
    /**
     * TODO
     *
     */
    public ReservationDatabase() {
    }

    /**
     * Returns the reservation database size
     **
     * @return The reservation database size
     */
    public static int getReservationDatabaseSize() {
        int count = -1;

        //create the connection to the db
        try (Connection connection = DriverManager.getConnection(url)) {
            //command to select all rows from db
            String selectAll = "SELECT COUNT(*) FROM Reservation";
            //preparing the statement
            try (PreparedStatement statement = connection.prepareStatement(selectAll)) {
                //executing the statement (executeQuery returns a ResultSet)
                try (ResultSet resultSet = statement.executeQuery()) {
                    //get the value of the first column (.getInt(1)) of the result set which returned the count of all rows
                    if (resultSet.next()) {
                        count = resultSet.getInt(1);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();

            System.err.println("Failed to connect to database.");
        }

        if (count == -1) {
            System.err.println("Failed to count all reservations.");
        }

        return count;
    }

    /**
     * Returns the reservations of a guest
     *
     * @return The set of reservations a guest has
     */
    public static Set<Reservation> getReservations(Guest guest) {
        Set<Reservation> guestReservations = new HashSet<>();

        //create the connection to the db
        try (Connection connection = DriverManager.getConnection(url)) {
            //command to select all rows from db matching the guest username
            String selectAll = "SELECT * FROM Reservation WHERE username = ?";
            //preparing the statement
            try (PreparedStatement statement = connection.prepareStatement(selectAll)) {
                //set the first parameter to search for (id) to the guest's id
                statement.setString(1, guest.getUsername());
                //executing the statement (executeQuery returns a ResultSet)
                try (ResultSet resultSet = statement.executeQuery()) {
                    //get the values in the set and create reservations for them
                    if (resultSet.next()) {
                        User user = AccountDatabase.getUser(resultSet.getString("username"));
                        Room room = RoomDatabase.getRoom(resultSet.getInt("roomNum"));
                        LocalDate startDate = LocalDate.parse(resultSet.getString("startDate"));
                        LocalDate endDate = LocalDate.parse(resultSet.getString("endDate"));
                        Country startCountry = CountryDatabase.getCountry(resultSet.getString("startCountry"));
                        Country endCountry = CountryDatabase.getCountry(resultSet.getString("endCountry"));

                        Reservation r = new Reservation(user, room, startDate, endDate, startCountry, endCountry);

                        guestReservations.add(r);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();

            System.err.println("Failed to connect to database.");
        }

        return guestReservations;
    }

    /**
     * Returns all the usernames of users with a reservation
     *
     * @return A set of usernames
     */
    public static Set<String> getAllUsernames() {
        Set<String> allUsernames = new HashSet<>();

        //create the connection to the db
        try (Connection connection = DriverManager.getConnection(url)) {
            //command to select all rows from db matching the guest id
            String selectAll = "SELECT * FROM Reservation";
            //preparing the statement
            try (PreparedStatement statement = connection.prepareStatement(selectAll)) {
                //executing the statement (executeQuery returns a ResultSet)
                try (ResultSet resultSet = statement.executeQuery()) {
                    //get the values in the set and create reservations for them
                    if (resultSet.next()) {
                        String username = resultSet.getString("username");

                        allUsernames.add(username);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();

            System.err.println("Failed to connect to database.");
        }

        return allUsernames;
    }

    /**
     * Operation to check if reservation is valid
     *
     * @param newReservation specified reservation to add
     *
     */
    public static void addReservation(Reservation newReservation) {
        String startDate = String.valueOf(newReservation.getStartDate());
        String endDate = String.valueOf(newReservation.getEndDate());

        //if the reservation was not a duplicate
        if (!ReservationDatabase.hasReservation(newReservation)) {
            //create the connection to the db
            try (Connection connection = DriverManager.getConnection(url)) {
                //command to insert all information
                String insert = "INSERT INTO Reservation (username, duration, startDate, endDate, startCountry, endCountry, roomNum) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(insert)) {
                    statement.setString(1, newReservation.getUser().getUsername());
                    statement.setLong(2, newReservation.getDays());
                    statement.setString(3,  startDate);
                    statement.setString(4, endDate);
                    statement.setString(5, newReservation.getStartCountry().getName());
                    statement.setString(6, newReservation.getEndCountry().getName());
                    statement.setInt(7, newReservation.getRoom().getRoomNumber());

                    int inserted = statement.executeUpdate();

                    if (inserted <= 0) {
                        System.out.println("Failed to insert data");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();

                System.err.println("Failed to connect to database.");
            }
        }
    }


    /**
     * Operation to check if a User has reservations
     *
     * @param user user to check database for
     *
     */
    public static boolean hasUser(User user) {
        //create the connection to the db
        try (Connection connection = DriverManager.getConnection(url)) {
            //command to find all ids
            String select = "SELECT * FROM Reservation WHERE username=?";
            try (PreparedStatement statement = connection.prepareStatement(select)) {
                //set user id as the one to find
                statement.setString(1, user.getUsername());
                //execute the statement and get the result set
                try (ResultSet resultSet = statement.executeQuery()) {
                    //while there is a valid row left in result set
                    while (resultSet.next()) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

            System.err.println("Failed to connect to database.");
        }
        return false;
    }



    /**
     * Operation to check if a specific room is in reservation
     *
     * @param roomNumber room number to check database for
     *
     */
    public static boolean hasRoom(int roomNumber) {
        //create the connection to the db
        try (Connection connection = DriverManager.getConnection(url)) {
            //command to find all room numbers
            String select = "SELECT * FROM Reservation WHERE roomNum=?";
            try (PreparedStatement statement = connection.prepareStatement(select)) {
                //set room num as the one to find
                statement.setInt(1, roomNumber);
                //execute the statement and get the result set
                try (ResultSet resultSet = statement.executeQuery()) {
                    //while there is a valid row left in result set
                    while (resultSet.next()) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

            System.err.println("Failed to connect to database.");
        }
        return false;
    }



    /**
     * Operation to check if the database has a specific reservation
     *
     * @param reservation specified reservation to check
     *
     */
    public static boolean hasReservation(Reservation reservation) {
        String startDate = String.valueOf(reservation.getStartDate());
        String endDate = String.valueOf(reservation.getEndDate());

        //create the connection to the db
        try (Connection connection = DriverManager.getConnection(url)) {
            //command to find all matching reservations
            String selectRow = "SELECT * FROM Reservation WHERE username = ? AND startCountry = ? AND endCountry = ? " +
                    "AND roomNum = ? AND startDate = ? AND endDate = ?";

            try (PreparedStatement statement = connection.prepareStatement(selectRow)) {
                statement.setString(1, reservation.getUser().getUsername());
                statement.setString(2, reservation.getStartCountry().getName());
                statement.setString(3, reservation.getEndCountry().getName());
                statement.setInt(4, reservation.getRoom().getRoomNumber());
                statement.setString(5, startDate);
                statement.setString(6, endDate);

                //if there is a valid row to examine in the set, return true
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

            System.err.println("Failed to connect to database.");
        }

        return false;
    }

    /**
     * Operation to delete a reservation in the database
     *
     * @param reservation specified reservation to delete
     *
     */
    public static void deleteReservation(Reservation reservation) {
        try (Connection connection = DriverManager.getConnection(url)) {
            String select = "DELETE FROM Reservation WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(select)) {
                statement.setLong(1, reservation.getId());

                int deleted;
                deleted = statement.executeUpdate();

                if (deleted <= 0) {
                    System.out.println("Failed to delete data");
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();

            System.err.println("Failed to connect to database.");
        }
    }

    /**
     * Operation to update the reservation in the database
     *
     * @param reservation specified reservation to update
     *
     */
    public static void updateReservation(Reservation reservation, String endDate, String roomNumber) {
        //get the connection to the db
        try (Connection connection = DriverManager.getConnection(url)) {
            String update = "UPDATE Reservation SET endDate=?, roomNum=?, WHERE id=?";

            try (PreparedStatement statement = connection.prepareStatement(update)) {
                statement.setString(1, endDate);
                statement.setInt(2, Integer.parseInt(roomNumber));
                statement.setInt(3, reservation.getId());

                int updated = statement.executeUpdate();

                if (updated <= 0) {
                    System.out.println("Failed to update data.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

            System.err.println("Failed to connect to database.");
        }
    }
}


