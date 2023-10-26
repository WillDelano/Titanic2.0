package edu.database;

import edu.core.cruise.Country;
import edu.core.reservation.Room;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class RoomDatabase {
    private static String fileName = "C:\\Users\\Owner\\Desktop\\Titanic2.0\\Titanic-Project\\src\\main\\java\\edu\\repositories\\room.csv";

    public static Room getRoom(int roomNumber) {
        try {
            InputStream is = RoomDatabase.class.getResourceAsStream("/room.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
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
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(",");

                //if the room id matches the room to be retrieved
                if (Objects.equals(roomNumber, Integer.parseInt(split[0]))) {

                    Room room = new Room(roomNumber, Integer.parseInt(split[3]), split[2], Boolean.parseBoolean(split[4]), Double.parseDouble(split[1]));

                    return room;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.err.println("Room does not exist. Creating null values.");
        return new Room(0, 0, null, false, 0);
    }

    public void addRoom(Room r) {
        //add room to set of rooms

        /*
         * CSV style
         * split[0] = room number
         * split[1] = room price
         * split[2] = bedType
         * split[3] = number of beds
         * split[4] = smoking status
         * split[5] = booked status
         */



        //add to file
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            //  String username,String password,int id, String firstName, String lastName,int rewardPoints, String email
            String linetoAdd = r.getRoomNumber() + "," + r.getRoomPrice() + "," + r.getBedType() + "," +
                    r.getNumberOfBeds() + "," + r.getSmokingAvailable() + "," + r.isBooked();
            FileWriter write = new FileWriter(fileName, true);
            writer.write(linetoAdd);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    //TODO: Add a parameter to get all the rooms of a certain cruise
    public static List<Room> getAllRooms() {
        List<Room> rooms = new LinkedList<>();
        try {
            InputStream is = RoomDatabase.class.getResourceAsStream("/room.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
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

                Room room = new Room(Integer.parseInt(split[0]), Integer.parseInt(split[3]), split[2], Boolean.parseBoolean(split[4]), Double.parseDouble(split[1]));

                rooms.add(room);
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
