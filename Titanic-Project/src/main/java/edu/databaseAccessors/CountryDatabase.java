package edu.databaseAccessors;

import edu.core.cruise.Country;

import java.io.*;
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
    private static final String url = "jdbc:derby:C:/Users/vince/IdeaProjects/titanic2/Titanic2.0/Titanic-Project/src/main/java/edu/Database";
    public static void addCountry(Country country) {
        String insertSQL = "INSERT INTO Countries (name, arrivalDate, departureDate) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setString(1, country.getName());
            preparedStatement.setDate(2, Date.valueOf(country.getArrivalTime()));
            preparedStatement.setDate(3, Date.valueOf(country.getDepartureTime()));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Country getCountry(String name) {
        String query = "SELECT * FROM Countries WHERE name = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Country(resultSet.getString("name"),
                            resultSet.getDate("arrivalDate").toLocalDate(),
                            resultSet.getDate("departureDate").toLocalDate());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
