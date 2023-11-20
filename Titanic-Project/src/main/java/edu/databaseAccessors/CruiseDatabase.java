package edu.databaseAccessors;

import edu.core.cruise.Cruise;
import edu.core.cruise.Country;
import edu.core.reservation.Room;
import edu.core.users.User;

import java.io.*;
import java.sql.*;
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
    private static Set<Cruise> cruiseDatabase;
    private static final String url = "jdbc:derby:C:/Users/vince/IdeaProjects/titanic2/Titanic2.0/Titanic-Project/src/main/java/edu/Database";

    static {
        cruiseDatabase = new HashSet<>();
        initializeDatabase(); // Static initialization of the database
    }

    public CruiseDatabase() {
    }

    private static void initializeDatabase() {
        try (Connection connection = DriverManager.getConnection(url)) {
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

    private static Cruise createCruiseFromResultSet(ResultSet resultSet) throws SQLException {
        // Extract cruise data from resultSet
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        LocalDate departure = resultSet.getDate("departure").toLocalDate();
        int maxCapacity = resultSet.getInt("maxCapacity");
        // Assuming travelPath and roomList are stored in a format that needs processing
        // List<Country> travelPath = processTravelPath(resultSet.getString("travelPath"));
        // List<Room> roomList = processRoomList(resultSet.getString("roomList"));
        // return new Cruise(name, departure, maxCapacity, travelPath);
        // Set id and roomList as needed
        // ...
    }

    public static Cruise getCruise(String cruiseName) {
        Cruise cruise = null;
        String query = "SELECT * FROM Cruises WHERE name = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, cruiseName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    LocalDate departure = resultSet.getDate("departure").toLocalDate();
                    int maxCapacity = resultSet.getInt("maxCapacity");
                    // Assuming travelPath and roomList are stored in a format that needs processing
                    // List<Country> travelPath = processTravelPath(resultSet.getString("travelPath"));
                    // List<Room> roomList = processRoomList(resultSet.getString("roomList"));
                    // cruise = new Cruise(name, departure, maxCapacity, travelPath);
                    // Set id and roomList as needed
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cruise;
    }


    //    public static Room getRoom(int roomNumber) {
//        try {
//            BufferedReader reader = new BufferedReader(new FileReader(filepath));
//            String line;
//
//            /*
//             * CSV style
//             * split[0] = room number
//             * split[1] = room price
//             * split[2] = bedType
//             * split[3] = number of beds
//             * split[4] = smoking status
//             * split[5] = booked status
//             */
//            while((line = reader.readLine()) != null) {
//                String[] split = line.split(",");
//
//                //if the room id matches the room to be retrieved
//                if (Objects.equals(roomNumber, Integer.parseInt(split[0]))) {
//
//                    Room room = new Room(roomNumber, Integer.parseInt(split[3]), split[2], Boolean.parseBoolean(split[4]), Double.parseDouble(split[1]), split[6]);
//
//                    System.err.println(split[4]);
//
//                    return room;
//                }
//            }
//            reader.close();
//        } catch(IOException e){
//            e.printStackTrace();
//        }
//        System.err.println("Room does not exist. Creating null values.");
//        return new Room(0, 0, null, false, 0, null);
//    }

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
