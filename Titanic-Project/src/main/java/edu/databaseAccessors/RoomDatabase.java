package edu.databaseAccessors;

import edu.core.reservation.Room;

import java.io.*;
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
<<<<<<< HEAD:Titanic-Project/src/main/java/edu/database/RoomDatabase.java
    private static String fileName = "C:\\Users\\Colet\\Documents\\GIT\\Titanic2.0\\Titanic-Project\\src\\main\\resources\\room.csv";
=======
    private static String fileName = "C:\\Users\\gabec\\SoftwareEngineeringI\\Titanic2.0\\Titanic-Project\\src\\main\\resources\\room.csv";
>>>>>>> 7d1521485f6b487929a2e1275f9c74a737f369bb:Titanic-Project/src/main/java/edu/databaseAccessors/RoomDatabase.java

    public static void addRoom(Room room) {
        /*
         * CSV style
         * split[0] = room number
         * split[1] = room price
         * split[2] = bedType
         * split[3] = number of beds
         * split[4] = smoking status
         * split[5] = booked status
         */

        //write to csv
        String toWrite = room.getRoomNumber() + "," + room.getRoomPrice() + "," +
                room.getBedTypeStr() + "," + room.getNumberOfBeds() + ","
                + room.getSmokingAvailable() + "," + room.isBooked() + "," + room.getCruise() + "\n";

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(fileName, true));
            writer.write(toWrite);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close(); // Closing the BufferedWriter
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Room getRoom(int roomNumber) {
        try {
<<<<<<< HEAD:Titanic-Project/src/main/java/edu/database/RoomDatabase.java
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
=======
            BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\vince\\Java Projects\\Titanic2.0\\Titanic-Project\\src\\main\\resources\\room.csv"));
>>>>>>> 7d1521485f6b487929a2e1275f9c74a737f369bb:Titanic-Project/src/main/java/edu/databaseAccessors/RoomDatabase.java
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

                    return room;
                }
            }
            reader.close();
        } catch(IOException e){
            e.printStackTrace();
        }
        System.err.println("Room does not exist. Creating null values. If you are creating a room you can ignore this error.");
        return new Room(-1, 0, null, false, 0, null);
    }


    /**
     * Operation to give access to a list of all rooms of a specific cruise
     *
     * @param cruise cruise name to parse all rooms
     *
     * @return list of rooms for a specific cruise
     */
    //TODO: Add a parameter to get all the rooms of a certain cruise
    public static List<Room> getAllRooms(String cruise) {
        List<Room> rooms = new LinkedList<>();
        try {
<<<<<<< HEAD:Titanic-Project/src/main/java/edu/database/RoomDatabase.java
            BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Colet\\Documents\\GIT\\Titanic2.0\\Titanic-Project\\src\\main\\resources\\room.csv"));
=======
            BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\vince\\Java Projects\\Titanic2.0\\Titanic-Project\\src\\main\\resources\\room.csv"));

>>>>>>> 7d1521485f6b487929a2e1275f9c74a737f369bb:Titanic-Project/src/main/java/edu/databaseAccessors/RoomDatabase.java
            String line;
            /*
             * CSV style
             * split[0] = room number
             * split[1] = room price
             * split[2] = bedType
             * split[3] = number of beds
             * split[4] = smoking status
             * split[5] = booked status
             * split[6] = cruise name
             */

            line = reader.readLine();

            while(line != null) {
                String[] split = line.split(",");

                //if the room is on the correct cruise
                if (Objects.equals(split[6], cruise)) {
                    Room room = new Room(Integer.parseInt(split[0]), Integer.parseInt(split[3]), split[2], Boolean.parseBoolean(split[4]), Double.parseDouble(split[1]), split[6]);

                    if (!ReservationDatabase.hasRoom(room.getRoomNumber())) {
                        rooms.add(room);
                    }
                }
                line = reader.readLine();
            }
            reader.close();
            return rooms;

        } catch(IOException e){
            e.printStackTrace();
        }
        System.err.println("Rooms do not exist for the cruise. Creating null values.");
        return rooms;
    }
}
