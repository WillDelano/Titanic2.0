package edu.database;

import edu.core.cruise.Country;
import edu.core.reservation.Reservation;
import edu.core.reservation.Room;

import java.io.*;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Database to record all the countries
 *
 * This class documents a collection of countries and reads/writes to the database file
 *
 * @author William Delano
 * @version 1.0
 */
public class CountryDatabase {
    private static String fileName = "/countries.csv";
    public static Country getCountry(String name) {
        //look through database to find country with matching name
        try {
            InputStream is = CountryDatabase.class.getResourceAsStream(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;

            /*
             * CSV style
             * split[0] = country name
             * split[1] = date cruise arrives at country
             * split[2] = date cruise departs from country
             */
            while((line = reader.readLine()) != null) {
                String[] split = line.split(",");

                //if the country name matches the csv name
                if (Objects.equals(name, split[0])) {

                    LocalDate arrival = LocalDate.parse(split[1]);
                    LocalDate departure = LocalDate.parse(split[2]);

                    Country country = new Country(name, arrival, departure);

                    return country;
                }
            }
            reader.close();
        } catch(IOException e){
            e.printStackTrace();
        }
        System.err.println("Country does not exist. Creating null values.");
        return new Country(null, null, null);
    }
}
