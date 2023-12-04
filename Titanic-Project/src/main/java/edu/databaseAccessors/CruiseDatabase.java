package edu.databaseAccessors;

import edu.core.cruise.Cruise;
import edu.core.cruise.Country;
import edu.core.reservation.Room;
import edu.core.users.User;

import java.io.*;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

/**
 * Database to record all the cruises
 *
 * This class documents a collection of cruises and reads/writes to the database file
 *
 * @author Vincent Dinh
 * @version 1.0
 */
public class CruiseDatabase {
    private static final String url = DatabaseProperties.url;

    private static Set<Cruise> cruiseDatabase;

    static {
        cruiseDatabase = new HashSet<>();
        initializeDatabase(); // Static initialization of the database
    }

    public CruiseDatabase() {
    }

    public static void addCruise(Cruise cruise) {
        String insertSQL = "INSERT INTO Cruises (name, departure, maxCapacity) VALUES (?, ?, ?)";
        try (Connection connection = driver.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setString(1, cruise.getName());
            preparedStatement.setDate(2, Date.valueOf(cruise.getDeparture()));
            preparedStatement.setInt(3, cruise.getMaxCapacity());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void initializeDatabase() {
        try (Connection connection = driver.getDBConnection()) {
            String query = "SELECT * FROM Cruises";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Cruise cruise = createCruiseFromResultSet(resultSet);
                        if (cruise != null) {
                            cruiseDatabase.add(cruise);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Cruise getCruise(String cruiseName) {
        String query = "SELECT * FROM Cruises WHERE name = ?";
        try (Connection connection = driver.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, cruiseName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return createCruiseFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Cruise createCruiseFromResultSet(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("name");
        LocalDate departure = resultSet.getDate("departure").toLocalDate();
        int maxCapacity = resultSet.getInt("maxCapacity");

        // Retrieve the list of countries associated with the cruise
        List<Country> travelPath = getTravelPathForCruise(name);

        List<Room> roomList = RoomDatabase.getRoomsForCruise(name);

        return new Cruise(name, departure, maxCapacity, travelPath, roomList);
    }

    public static List<Cruise> getAllCruises() {
        List<Cruise> cruises = new ArrayList<>();
        String query = "SELECT * FROM Cruises";
        try (Connection connection = driver.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    cruises.add(createCruiseFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cruises;
    }


    public static List<String> getAllCruiseNames() {
        List<String> cruiseNames = new ArrayList<>();
        String query = "SELECT name FROM Cruises";
        try (Connection connection = driver.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    cruiseNames.add(resultSet.getString("name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cruiseNames;
    }

    public static void initializeCruises() {
        List<Cruise> predefinedCruises = Arrays.asList(
                new Cruise("Caribbean Adventure", LocalDate.of(2023, 12, 12), 200, createCaribbeanTravelPath(), createSampleRoomList()),
                new Cruise("Mediterranean Escape", LocalDate.of(2023, 7, 4), 150, createMediterraneanTravelPath(), createSampleRoomList()),
                new Cruise("Alaskan", LocalDate.of(2023, 6, 14), 250, createAlaskanTravelPath(), createSampleRoomList())
        );

        for (Cruise cruise : predefinedCruises) {
            if (!cruiseExists(cruise.getName())) {
                addCruise(cruise);
            }
        }
    }

    private static boolean cruiseExists(String cruiseName) {
        return getAllCruiseNames().contains(cruiseName);
    }

    private static List<Country> createCaribbeanTravelPath() {
        return Arrays.asList(
                new Country("Kingston, Jamaica", LocalDate.of(2023, 12, 12), LocalDate.of(2023, 12, 12)),
                new Country("Nassau, Bahamas", LocalDate.of(2023, 12, 14), LocalDate.of(2023, 12, 14)),
                new Country("Bridgetown, Barbados", LocalDate.of(2023, 12, 17), LocalDate.of(2023, 12, 17))
        );
    }

    private static List<Country> createMediterraneanTravelPath() {
        return Arrays.asList(
                new Country("Naples, Italy", LocalDate.of(2023, 7, 4), LocalDate.of(2023, 7, 4)),
                new Country("Athens, Greece", LocalDate.of(2023, 7, 7), LocalDate.of(2023, 7, 7)),
                new Country("Nice, France", LocalDate.of(2023, 7, 8), LocalDate.of(2023, 7, 8))
        );
    }

    private static List<Country> createAlaskanTravelPath() {
        return Arrays.asList(
                new Country("Juneau, United States", LocalDate.of(2023, 6, 14), LocalDate.of(2023, 6, 14)),
                new Country("Valdez, United States", LocalDate.of(2023, 6, 16), LocalDate.of(2023, 6, 16)),
                new Country("Anchorage, United States", LocalDate.of(2023, 6, 17), LocalDate.of(2023, 6, 17))
        );
    }


    private static List<Room> createSampleRoomList() {
        return Arrays.asList(
                new Room(101, 2, "Double", false, 150.0, "Caribbean Adventure"),
                new Room(102, 1, "Single", true, 100.0, "Caribbean Adventure"),
                new Room(101, 2, "Double", false, 150.0, "Mediterranean Escape"),
                new Room(102, 1, "Single", true, 100.0, "Mediterranean Escape"),
                new Room(101, 2, "Double", false, 150.0, "Alaskan"),
                new Room(102, 1, "Single", true, 100.0, "Alaskan")

        );
    }

    public static List<Country> getTravelPathForCruise(String cruiseName) {
        List<Country> travelPath = new ArrayList<>();
        String query = "SELECT c.* FROM Countries c JOIN Cruises cr ON c.cruise_id = cr.id WHERE cr.name = ?";

        try (Connection connection = driver.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, cruiseName);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String countryName = resultSet.getString("name");
                    LocalDate arrivalDate = resultSet.getDate("arrivalTime").toLocalDate();
                    LocalDate departureDate = resultSet.getDate("departureTime").toLocalDate();
                    travelPath.add(new Country(countryName, arrivalDate, departureDate));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error retrieving travel path for cruise: " + cruiseName);
        }

        return travelPath;
    }

}