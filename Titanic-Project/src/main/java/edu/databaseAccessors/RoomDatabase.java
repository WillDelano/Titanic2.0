package edu.databaseAccessors;

import edu.core.reservation.Room;
import edu.core.users.User;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import edu.core.reservation.Room;
import java.sql.*;
import java.util.*;


/**
 * Database to record all the rooms
 *
 * This class documents a collection of rooms and reads/writes to the database file
 *
 * @author William Delano
 * @version 1.0
 */
public class RoomDatabase {
    private static Set<Room> roomDatabase;

    private static final String url = "jdbc:derby:/Users/willdelano/Desktop/Software1/Titanic2.0/Titanic-Project/src/main/java/edu/Database";

    /**
     * Operation to add a reservation
     *
     * @param room specified room to add
     *
     */
    public static void addRoom(Room room) {
        String insertSQL = "INSERT INTO Rooms (roomnumber, numberofbeds, bedtype, smokingavailable, roomprice, isbooked, cruise) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setInt(1, room.getRoomNumber());
            preparedStatement.setInt(2, room.getNumberOfBeds());
            preparedStatement.setString(3, room.getBedType());
            preparedStatement.setBoolean(4, room.getSmokingAvailable());
            preparedStatement.setDouble(5, room.getRoomPrice());
            preparedStatement.setBoolean(6, room.isBooked());
            preparedStatement.setString(7, room.getCruise());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean roomExists(int roomNumber, String cruise) {
        String query = "SELECT COUNT(*) FROM Rooms WHERE roomnumber = ? AND cruise = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, roomNumber);
            statement.setString(2, cruise);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<Room> getRoomsForCruise(String cruiseName) {
        List<Room> rooms = new ArrayList<>();
        String query = "SELECT * FROM Rooms WHERE cruise = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, cruiseName);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Room room = new Room(resultSet.getInt("roomnumber"),
                            resultSet.getInt("numberofbeds"),
                            resultSet.getString("bedtype"),
                            resultSet.getBoolean("smokingavailable"),
                            resultSet.getDouble("roomprice"),
                            resultSet.getString("cruise"));
                    rooms.add(room);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    public static void initializeRooms() {
        List<Room> predefinedRooms = Arrays.asList(
                new Room(101, 2, "Queen", false, 250, "Caribbean Adventure"),
                new Room(102, 4, "King", true, 400, "Caribbean Adventure"),
                new Room(103, 1, "Twin", false, 200, "Caribbean Adventure"),
                new Room(104, 2, "Queen", false, 250, "Mediterranean Escape"),
                new Room(105, 2, "King", true, 400, "Mediterranean Escape"),
                new Room(106, 2, "Twin", false, 200, "Mediterranean Escape"),
                new Room(107, 2, "Queen", false, 250, "Alaskan"),
                new Room(108, 2, "King", true, 400, "Alaskan"),
                new Room(109, 2, "Twin", false, 200, "Alaskan")
        );

        for (Room room : predefinedRooms) {
            if (!roomExists(room.getRoomNumber(), room.getCruise())) {
                addRoom(room);
            }
        }
    }

    /**
     * Operation to get a room
     *
     * @param roomNumber specified room to get
     *
     */
    public static Room getRoom(int roomNumber) {
        String query = "SELECT * FROM Rooms WHERE roomnumber = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, roomNumber);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Room(resultSet.getInt("roomnumber"),
                            resultSet.getInt("numberofbeds"),
                            resultSet.getString("bedtype"),
                            resultSet.getBoolean("smokingavailable"),
                            resultSet.getDouble("roomprice"),
                            resultSet.getString("cruise"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Operation to get all rooms of a specific cruise
     *
     * @param cruise cruise name containing the rooms
     *
     * @return list of rooms for the specific cruise
     */
    public static List<Room> getAllRooms(String cruise) {
        List<Room> rooms = new ArrayList<>();
        String query = "SELECT * FROM Rooms WHERE cruise = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, cruise);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Room room = new Room(resultSet.getInt("roomnumber"),
                            resultSet.getInt("numberofbeds"),
                            resultSet.getString("bedtype"),
                            resultSet.getBoolean("smokingavailable"),
                            resultSet.getDouble("roomprice"),
                            resultSet.getString("cruise"));
                    rooms.add(room);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    public static void editAccount(User account, String email, String password) {
        //SQL STUFF TO ALTER ROOM
    }

    public boolean isValidRoom(int roomChoice) {
        if (roomChoice < 0) {
            System.out.println("Please enter a valid room choice.");
            return false;
        }

        String query = "SELECT COUNT(*) FROM Rooms WHERE roomnumber = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, roomChoice);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next() && resultSet.getInt(1) > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Room not found.");
        return false;
    }

    public static void bookRoom(int roomNumber) {
        String updateSQL = "UPDATE Rooms SET isbooked = true WHERE roomnumber = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setInt(1, roomNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
