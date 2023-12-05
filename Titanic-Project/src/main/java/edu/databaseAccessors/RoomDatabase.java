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
            preparedStatement.setString(3, room.getBedTypeStr());
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
                // Caribbean Adventure
                new Room(101, 2, "Queen", false, 500, "Caribbean Adventure"),
                new Room(102, 4, "King", true, 1600, "Caribbean Adventure"),
                new Room(103, 1, "Twin", false, 200, "Caribbean Adventure"),
                new Room(110, 3, "King", true, 1050, "Caribbean Adventure"),
                new Room(111, 1, "Queen", false, 240, "Caribbean Adventure"),
                new Room(112, 2, "Twin", false, 420, "Caribbean Adventure"),
                new Room(113, 4, "King", true, 1680, "Caribbean Adventure"),
                new Room(114, 2, "Queen", false, 500, "Caribbean Adventure"),
                new Room(115, 3, "King", true, 1200, "Caribbean Adventure"),
                new Room(116, 1, "Twin", false, 300, "Caribbean Adventure"),
                new Room(117, 2, "Queen", false, 480, "Caribbean Adventure"),
                new Room(118, 3, "King", true, 1350, "Caribbean Adventure"),
                new Room(119, 1, "Twin", false, 240, "Caribbean Adventure"),
                new Room(120, 4, "King", true, 2000, "Caribbean Adventure"),
                new Room(121, 2, "Twin", false, 420, "Caribbean Adventure"),
                new Room(122, 4, "King", true, 1680, "Caribbean Adventure"),
                new Room(123, 2, "Queen", false, 500, "Caribbean Adventure"),
                new Room(124, 3, "King", true, 1200, "Caribbean Adventure"),
                new Room(125, 1, "Twin", false, 300, "Caribbean Adventure"),
                new Room(126, 2, "Queen", false, 480, "Caribbean Adventure"),
                new Room(127, 3, "King", true, 1350, "Caribbean Adventure"),
                new Room(128, 1, "Twin", false, 240, "Caribbean Adventure"),
                new Room(129, 4, "King", true, 2000, "Caribbean Adventure"),
                new Room(130, 2, "Twin", false, 420, "Caribbean Adventure"),
                new Room(131, 4, "King", true, 1680, "Caribbean Adventure"),
                new Room(132, 2, "Queen", false, 500, "Caribbean Adventure"),
                new Room(133, 3, "King", true, 1200, "Caribbean Adventure"),
                new Room(134, 1, "Twin", false, 300, "Caribbean Adventure"),
                new Room(135, 2, "Queen", false, 480, "Caribbean Adventure"),
                new Room(136, 3, "King", true, 1350, "Caribbean Adventure"),
                new Room(137, 1, "Twin", false, 240, "Caribbean Adventure"),
                new Room(138, 4, "King", true, 2000, "Caribbean Adventure"),
                new Room(139, 2, "Twin", false, 420, "Caribbean Adventure"),
                new Room(140, 2, "Queen", false, 520, "Caribbean Adventure"),
                new Room(141, 3, "King", true, 1140, "Caribbean Adventure"),
                new Room(142, 2, "Twin", false, 440, "Caribbean Adventure"),
                new Room(143, 1, "Queen", false, 230, "Caribbean Adventure"),
                new Room(144, 4, "King", true, 2000, "Caribbean Adventure"),
                new Room(145, 2, "Twin", false, 400, "Caribbean Adventure"),
                new Room(146, 2, "Queen", false, 520, "Caribbean Adventure"),
                new Room(147, 3, "King", true, 1140, "Caribbean Adventure"),
                new Room(148, 2, "Twin", false, 440, "Caribbean Adventure"),
                new Room(149, 1, "Queen", false, 230, "Caribbean Adventure"),
                new Room(150, 4, "King", true, 2000, "Caribbean Adventure"),
                new Room(151, 2, "Twin", false, 420, "Caribbean Adventure"),

                // Mediterranean Escape
                new Room(201, 2, "Queen", false, 500, "Mediterranean Escape"),
                new Room(202, 4, "King", true, 1600, "Mediterranean Escape"),
                new Room(203, 2, "Twin", false, 400, "Mediterranean Escape"),
                new Room(204, 2, "Queen", false, 520, "Mediterranean Escape"),
                new Room(205, 3, "King", true, 1140, "Mediterranean Escape"),
                new Room(206, 2, "Twin", false, 440, "Mediterranean Escape"),
                new Room(207, 1, "Queen", false, 230, "Mediterranean Escape"),
                new Room(208, 2, "Queen", false, 500, "Mediterranean Escape"),
                new Room(209, 4, "King", true, 1600, "Mediterranean Escape"),
                new Room(210, 2, "Twin", false, 400, "Mediterranean Escape"),
                new Room(211, 2, "Queen", false, 520, "Mediterranean Escape"),
                new Room(212, 3, "King", true, 1140, "Mediterranean Escape"),
                new Room(213, 2, "Twin", false, 440, "Mediterranean Escape"),
                new Room(214, 1, "Queen", false, 230, "Mediterranean Escape"),
                new Room(215, 4, "King", true, 2000, "Mediterranean Escape"),
                new Room(216, 2, "Twin", false, 400, "Mediterranean Escape"),
                new Room(217, 4, "King", true, 1600, "Mediterranean Escape"),
                new Room(218, 2, "Queen", false, 520, "Mediterranean Escape"),
                new Room(219, 3, "King", true, 1140, "Mediterranean Escape"),
                new Room(220, 1, "Twin", false, 240, "Mediterranean Escape"),
                new Room(221, 2, "Queen", false, 480, "Mediterranean Escape"),
                new Room(222, 3, "King", true, 1350, "Mediterranean Escape"),
                new Room(223, 1, "Twin", false, 240, "Mediterranean Escape"),
                new Room(224, 4, "King", true, 2000, "Mediterranean Escape"),
                new Room(225, 2, "Twin", false, 420, "Mediterranean Escape"),
                new Room(226, 2, "Queen", false, 520, "Mediterranean Escape"),
                new Room(227, 3, "King", true, 1140, "Mediterranean Escape"),
                new Room(228, 2, "Twin", false, 440, "Mediterranean Escape"),
                new Room(229, 1, "Queen", false, 230, "Mediterranean Escape"),
                new Room(230, 4, "King", true, 2000, "Mediterranean Escape"),
                new Room(231, 2, "Twin", false, 420, "Mediterranean Escape"),
                new Room(232, 4, "King", true, 1600, "Mediterranean Escape"),
                new Room(233, 2, "Queen", false, 520, "Mediterranean Escape"),
                new Room(234, 3, "King", true, 1140, "Mediterranean Escape"),
                new Room(235, 1, "Twin", false, 240, "Mediterranean Escape"),
                new Room(236, 2, "Queen", false, 480, "Mediterranean Escape"),
                new Room(237, 3, "King", true, 1350, "Mediterranean Escape"),
                new Room(238, 1, "Twin", false, 240, "Mediterranean Escape"),
                new Room(239, 4, "King", true, 2000, "Mediterranean Escape"),
                new Room(240, 2, "Twin", false, 420, "Mediterranean Escape"),

                // Alaskan
                new Room(301, 2, "Queen", false, 500, "Alaskan"),
                new Room(302, 4, "King", true, 1600, "Alaskan"),
                new Room(303, 2, "Twin", false, 400, "Alaskan"),
                new Room(304, 2, "King", true, 820, "Alaskan"),
                new Room(305, 3, "Queen", false, 810, "Alaskan"),
                new Room(306, 2, "Twin", false, 460, "Alaskan"),
                new Room(307, 4, "King", true, 1720, "Alaskan"),
                new Room(308, 2, "Queen", false, 500, "Alaskan"),
                new Room(309, 4, "King", true, 1600, "Alaskan"),
                new Room(310, 2, "Twin", false, 400, "Alaskan"),
                new Room(311, 1, "Queen", false, 230, "Alaskan"),
                new Room(312, 2, "King", true, 820, "Alaskan"),
                new Room(313, 3, "Queen", false, 810, "Alaskan"),
                new Room(314, 2, "Twin", false, 460, "Alaskan"),
                new Room(315, 4, "King", true, 1720, "Alaskan"),
                new Room(316, 2, "Queen", false, 500, "Alaskan"),
                new Room(317, 3, "King", true, 1200, "Alaskan"),
                new Room(318, 1, "Twin", false, 300, "Alaskan"),
                new Room(319, 2, "Queen", false, 480, "Alaskan"),
                new Room(320, 3, "King", true, 1350, "Alaskan"),
                new Room(321, 1, "Twin", false, 240, "Alaskan"),
                new Room(322, 4, "King", true, 2000, "Alaskan"),
                new Room(323, 2, "Twin", false, 420, "Alaskan"),
                new Room(324, 2, "Queen", false, 520, "Alaskan"),
                new Room(325, 3, "King", true, 1140, "Alaskan"),
                new Room(326, 2, "Twin", false, 440, "Alaskan"),
                new Room(327, 1, "Queen", false, 230, "Alaskan"),
                new Room(328, 4, "King", true, 2000, "Alaskan"),
                new Room(329, 2, "Twin", false, 400, "Alaskan"),
                new Room(330, 2, "Queen", false, 520, "Alaskan"),
                new Room(331, 3, "King", true, 1140, "Alaskan"),
                new Room(332, 2, "Twin", false, 440, "Alaskan"),
                new Room(333, 1, "Queen", false, 230, "Alaskan"),
                new Room(334, 4, "King", true, 2000, "Alaskan"),
                new Room(335, 2, "Twin", false, 400, "Alaskan"),
                new Room(336, 2, "Queen", false, 520, "Alaskan"),
                new Room(337, 3, "King", true, 1140, "Alaskan"),
                new Room(338, 2, "Twin", false, 440, "Alaskan"),
                new Room(339, 1, "Queen", false, 230, "Alaskan"),
                new Room(340, 4, "King", true, 2000, "Alaskan"),
                new Room(341, 2, "Twin", false, 420, "Alaskan"),
                new Room(342, 4, "King", true, 1600, "Alaskan"),
                new Room(343, 2, "Queen", false, 520, "Alaskan"),
                new Room(344, 3, "King", true, 1140, "Alaskan"),
                new Room(345, 1, "Twin", false, 240, "Alaskan"),
                new Room(346, 2, "Queen", false, 480, "Alaskan"),
                new Room(347, 3, "King", true, 1350, "Alaskan"),
                new Room(348, 1, "Twin", false, 240, "Alaskan"),
                new Room(349, 4, "King", true, 2000, "Alaskan"),
                new Room(350, 2, "Twin", false, 420, "Alaskan"),
                new Room(351, 2, "Queen", false, 520, "Alaskan"),
                new Room(352, 3, "King", true, 1140, "Alaskan"),
                new Room(353, 2, "Twin", false, 440, "Alaskan"),
                new Room(354, 1, "Queen", false, 230, "Alaskan")
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