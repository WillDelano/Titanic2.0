package edu;

import edu.core.reservation.Room;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class RoomCSVReader {
    public static void main(String[] args) {
        String csvFilePath = "C:\\Users\\vince\\Java Projects\\Titanic2.0\\Titanic-Project\\room.csv";
        List<Room> roomList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            boolean isFirstLine = true; // Flag to skip the first line
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip the header line
                }

                String[] parts = line.split(","); // Assuming CSV uses a comma as the separator

                if (parts.length >= 6) {
                    int roomNumber = Integer.parseInt(parts[0]);
                    int numberOfBeds = Integer.parseInt(parts[1]);
                    String bedType = parts[2];
                    boolean smokingAvailable = Boolean.parseBoolean(parts[3]);
                    double roomPrice = Double.parseDouble(parts[4]);

                    // create a Room object with the parsed data
                    Room room = new Room(roomNumber, numberOfBeds, bedType, smokingAvailable, roomPrice);
                    roomList.add(room);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        reserveRoom(roomList, 101);
        reserveRoom(roomList, 102);
        reserveRoom(roomList, 105);
    }


    private static void reserveRoom(List<Room> roomList, int roomNumber) {
        for (Room room : roomList) {
            if (room.getRoomNumber() == roomNumber) {
                if (!room.isBooked()) {
                    room.bookRoom();
                    System.out.println("Room " + roomNumber + " reserved successfully.");
                } else {
                    System.out.println("Room " + roomNumber + " is already booked.");
                }
                return;
            }
        }
        System.out.println("Room " + roomNumber + " not found.");
    }
}