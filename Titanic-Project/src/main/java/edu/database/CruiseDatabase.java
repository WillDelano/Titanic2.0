package edu.database;

import edu.core.cruise.Cruise;
import edu.core.cruise.Country;
import edu.core.reservation.Room;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CruiseDatabase {
    public static String filepath = "/cruises.csv";

    public static Cruise getCruise(String searchName) {
        List<Country> travelPath = new ArrayList<>();
        List<Room> roomList = new ArrayList<>();
        LocalDate departure = null;
        int maxCapacity = 0;

        try {
            InputStream is = CruiseDatabase.class.getResourceAsStream(filepath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] split = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                String cruiseName = split[1];
                if (Objects.equals(searchName, cruiseName)) {
                    departure = LocalDate.parse(split[2]);
                    travelPath.add(new Country(split[3], LocalDate.parse(split[4]), LocalDate.parse(split[5])));
                    roomList.addAll(getRoomListFromCSV(split[6]));
                    maxCapacity = Integer.parseInt(split[7]);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (departure != null) {
            return new Cruise(searchName, departure, maxCapacity, travelPath);
        } else {
            System.err.println("Cruise with name " + searchName + " does not exist. Returning null.");
            return null;
        }
    }

    public static List<String> getAllCruiseNames() {
        List<String> cruiseNames = new ArrayList<>();

        try {
            InputStream is = ReservationDatabase.class.getResourceAsStream(filepath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] split = line.split(",");
                String cruiseName = split[1];
                cruiseNames.add(cruiseName);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cruiseNames;
    }

    private static List<Country> getCountryListFromCSV(String csv) {
        List<Country> countryList = new ArrayList<>();
        String[] countryEntries = csv.split("\\|");

        for (String entry : countryEntries) {
            String[] parts = entry.split(",");
            if (parts.length == 3) {
                String name = parts[0];
                LocalDate arrivalDate = LocalDate.parse(parts[1]);
                LocalDate departureDate = LocalDate.parse(parts[2]);
                countryList.add(new Country(name, arrivalDate, departureDate));
            }
        }

        return countryList;
    }


    private static List<Room> getRoomListFromCSV(String csv) {
        List<Room> roomList = new ArrayList<>();
        String[] roomNumbers = csv.split("\\|");

        for (String roomNum : roomNumbers) {
            if (!roomNum.isEmpty()) {
                Room room = new Room(Integer.parseInt(roomNum.trim()), 2, "Double", false, 100.0);
                roomList.add(room);
            }
        }
        return roomList;
    }

}
