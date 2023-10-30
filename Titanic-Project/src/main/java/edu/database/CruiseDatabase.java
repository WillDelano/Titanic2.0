package edu.database;

import edu.core.cruise.Cruise;
import edu.core.cruise.Country;
import edu.core.reservation.Room;

import java.io.*;
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
    public static String filepath = "C:\\Users\\Colet\\Documents\\GIT\\Titanic2.0\\Titanic-Project\\src\\main\\resources\\cruises.csv";

    public static Room getRoom(int roomNumber) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            String line;

            /*
             * CSV style
             * split[0] = room number
             * split[1] = room price
             * split[2] = bedType
             * split[3] = number of beds
             * split[4] = smoking status
             * split[5] = booked status
             */
            while((line = reader.readLine()) != null) {
                String[] split = line.split(",");

                //if the room id matches the room to be retrieved
                if (Objects.equals(roomNumber, Integer.parseInt(split[0]))) {

                    Room room = new Room(roomNumber, Integer.parseInt(split[3]), split[2], Boolean.parseBoolean(split[4]), Double.parseDouble(split[1]), split[6]);

                    System.err.println(split[4]);

                    return room;
                }
            }
            reader.close();
        } catch(IOException e){
            e.printStackTrace();
        }
        System.err.println("Room does not exist. Creating null values.");
        return new Room(0, 0, null, false, 0, null);
    }
    public static Cruise getCruise(String searchName) {
        List<Country> travelPath = new ArrayList<>();
        List<Room> roomList = new ArrayList<>();
        LocalDate departure = null;
        int maxCapacity = 0;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            String line;

            line = reader.readLine();
            while (line != null) {
                String[] split = line.split(",");

                /*
                * CSV Style
                * split[0] = cruise id
                * split[1] = cruise name
                * split[2] = departure date
                * split[3] = country
                * split[4] = country arrival date
                * split[5] = country departure date
                * split[6] =
                */

                String cruiseName = split[1];
                if (Objects.equals(searchName, cruiseName)) {
                    departure = LocalDate.parse(split[2]);
                    travelPath.add(new Country(split[3], LocalDate.parse(split[4]), LocalDate.parse(split[5])));
                    roomList.addAll(getRoomListFromCSV(split[6], split[1]));
                    maxCapacity = Integer.parseInt(split[7]);
                }
                line = reader.readLine();
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
            BufferedReader reader = new BufferedReader(new FileReader(filepath));
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

        //sort to alphabetical order
        Collections.sort(cruiseNames);

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


    private static List<Room> getRoomListFromCSV(String csv, String cruiseName) {
        List<Room> roomList = new ArrayList<>();
        String[] roomNumbers = csv.split("\\|");

        for (String roomNum : roomNumbers) {
            if (!roomNum.isEmpty()) {
                Room room = new Room(Integer.parseInt(roomNum.trim()), 2, "Double", false, 100.0, cruiseName);
                roomList.add(room);
            }
        }
        return roomList;
    }

}
