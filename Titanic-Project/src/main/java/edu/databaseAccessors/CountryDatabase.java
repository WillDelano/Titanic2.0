package edu.databaseAccessors;

import edu.core.cruise.Country;

import java.io.*;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;
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
 * Database to record all the countries
 *
 * This class documents a collection of countries and reads/writes to the database file
 *
 * @author William Delano
 * @version 1.0
 */
public class CountryDatabase {
    private static final String url = DatabaseProperties.url;

    public static void addCountry(Country country, int cruiseId) {
        String insertSQL = "INSERT INTO Countries (name, arrivalTime, departureTime, cruise_id) VALUES (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, country.getName());
            preparedStatement.setDate(2, Date.valueOf(country.getArrivalTime()));
            preparedStatement.setDate(3, Date.valueOf(country.getDepartureTime()));
            preparedStatement.setInt(4, cruiseId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static boolean countryExists(String countryName) {
        String query = "SELECT COUNT(*) FROM Countries WHERE name = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, countryName);
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

    public static void initializeCountries() {
        int caribbeanCruiseId = 1;
        int mediterraneanCruiseId = 2;
        int alaskanCruiseId = 3;

        List<Country> predefinedCountries = Arrays.asList(
                new Country("Kingston, Jamaica", LocalDate.of(2023, 12, 12), LocalDate.of(2023, 12, 12)),
                new Country("Nassau, Bahamas", LocalDate.of(2023, 12, 14), LocalDate.of(2023, 12, 14)),
                new Country("Bridgetown, Barbados", LocalDate.of(2023, 12, 17), LocalDate.of(2023, 12, 17)),
                new Country("Naples, Italy", LocalDate.of(2023, 7, 4), LocalDate.of(2023, 7, 4)),
                new Country("Athens, Greece", LocalDate.of(2023, 7, 7), LocalDate.of(2023, 7, 7)),
                new Country("Nice, France", LocalDate.of(2023, 7, 8), LocalDate.of(2023, 7, 8)),
                new Country("Juneau, United States", LocalDate.of(2023, 6, 14), LocalDate.of(2023, 6, 14)),
                new Country("Valdez, United States", LocalDate.of(2023, 6, 16), LocalDate.of(2023, 6, 16)),
                new Country("Anchorage, United States", LocalDate.of(2023, 6, 17), LocalDate.of(2023, 6, 17))
        );

        for (Country country : predefinedCountries) {
            if (!countryExists(country.getName())) {

                int cruiseId;
                switch (country.getName()) {
                    case "Kingston, Jamaica":
                    case "Nassau, Bahamas":
                    case "Bridgetown, Barbados":
                        cruiseId = caribbeanCruiseId;
                        break;
                    case "Athens, Greece":
                    case "Naples, Italy":
                    case "Nice, France":
                        cruiseId = mediterraneanCruiseId;
                        break;
                    case "Juneau, United States":
                    case "Valdez, United States":
                    case "Anchorage, United States":
                        cruiseId = alaskanCruiseId;
                        break;
                    default:
                        continue;
                }
                addCountry(country, cruiseId);
            }
        }
    }

    public static Country getCountry(String name) {

        String query = "SELECT * FROM Countries WHERE name = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Country(
                            resultSet.getString("name"),
                            resultSet.getDate("arrivalTime").toLocalDate(),
                            resultSet.getDate("departureTime").toLocalDate()
                    );
                } else {
                    System.out.println("No country found with name: " + name); // Log if no country is found
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error querying country: " + e.getMessage()); // Log any SQL exceptions
        }
        return null;
    }

    public static List<Country> getAllCountries() {
        List<Country> countries = new ArrayList<>();
        String query = "SELECT * FROM Countries";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(query)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Country country = new Country(resultSet.getString("name"),
                            resultSet.getDate("arrivalDate").toLocalDate(),
                            resultSet.getDate("departureDate").toLocalDate());
                    countries.add(country);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countries;
    }

}