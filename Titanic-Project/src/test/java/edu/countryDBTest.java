package edu;

import edu.core.cruise.Country;
import edu.databaseAccessors.CountryDatabase;
import edu.databaseAccessors.DatabaseProperties;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class countryDBTest {
    Country testCountry;
    LocalDate rightNow;

    @BeforeEach
    public void makeCountry(){
        rightNow = LocalDate.now();
        testCountry = new Country("yee yee land",rightNow,rightNow);
    }
    @Test
    public void addCountry(){
        CountryDatabase.addCountry(testCountry,2);
        Assertions.assertNotNull(CountryDatabase.getCountry("yee yee land"));
        assertEquals(CountryDatabase.getCountry("yee yee land").getArrivalTime(),rightNow);

    }

    @Test
    public void getAllcountriestest(){
        assertEquals(CountryDatabase.getAllCountries().size(),9);
    }

    @AfterEach
    public void tearDown() {
        String url = DatabaseProperties.url;
        try (Connection connection = DriverManager.getConnection(url)) {
            String deleteQuery = "DELETE FROM Countries WHERE name = ?";
            try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
                statement.setString(1, "yee yee land");
                int deleted;
                deleted = statement.executeUpdate();

                if (deleted <= 0) {
                    System.out.println("Failed to delete data");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
