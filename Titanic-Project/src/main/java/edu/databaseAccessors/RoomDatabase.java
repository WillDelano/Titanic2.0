package edu.databaseAccessors;

import edu.core.reservation.Room;
import edu.core.users.User;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import edu.core.reservation.Room;

import javax.swing.plaf.synth.SynthDesktopIconUI;
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
    private static final String url = DatabaseProperties.url;
    private static Set<Room> roomDatabase;

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

    public static boolean roomExists(int roomNumber) {
        String query = "SELECT COUNT(*) FROM Rooms WHERE roomnumber = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, roomNumber);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    if (resultSet.getInt(1) > 0) { return true; }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Operation to get all rooms of a specific cruise
     *
     * @param cruiseName name of cruise to get the rooms of
     *
     * @return list of rooms for the specific cruise
     */
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

                            if (resultSet.getBoolean("isBooked")) {
                                room.bookRoom();
                            }
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
            if (!roomExists(room.getRoomNumber())) {
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
                //return a false value if there is no room with the number
                return new Room(-1, -1, "", false, -1, "");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Operation to get all the rooms that exist
     *
     *
     * @return list of rooms for the specific cruise
     */
    public static List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();
        String query = "SELECT * FROM Rooms";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Room room = new Room(resultSet.getInt("roomnumber"),
                            resultSet.getInt("numberofbeds"),
                            resultSet.getString("bedtype"),
                            resultSet.getBoolean("smokingavailable"),
                            resultSet.getDouble("roomprice"),
                            resultSet.getString("cruise"));
                    //sets avaliability of room
                    if(resultSet.getBoolean("isbooked")){
                        room.bookRoom();
                    }
                    rooms.add(room);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    public static void editRoom(User account, String email, String password) {
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
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Room not found.");
        return false;
    }

    public static void unbookRoom(int roomNumber) {
        String updateSQL = "UPDATE Rooms SET isbooked = false WHERE roomnumber = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setInt(1, roomNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.err.println("Room: " + roomNumber + " unbooked");
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

    public void updateRoom(Room room, String bedType, int numOfBeds, boolean smokingStatus, double price) {
        //fixme: actually updating sql(bed type, number of beds, status, and price all at once even if some are same)
        //fixme: finds room based on roomNumber
        int roomNum = room.getRoomNumber();
        try (Connection connection = DriverManager.getConnection(url)) {
            String update = "UPDATE ROOMS SET BEDTYPE = ?, NUMBEROFBEDS = ?,SMOKINGAVAILABLE = ?, ROOMPRICE = ? WHERE ROOMNUMBER = ?";
            try (PreparedStatement statement = connection.prepareStatement(update)) {
                statement.setString(1, bedType);
                statement.setInt(2, numOfBeds);
                statement.setBoolean(3, smokingStatus);
                statement.setDouble(4,price);
                statement.setInt(5,roomNum);
                int updated = statement.executeUpdate();
                if (updated <= 0) {
                    System.out.println("Failed to update data");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
