package edu;

import edu.core.cruise.Country;
import edu.core.reservation.Reservation;
import edu.core.reservation.Room;
import edu.core.reservation.roomSearch;
import edu.core.users.Guest;
import edu.core.users.User;
import edu.databaseAccessors.ReservationDatabase;
import edu.databaseAccessors.RoomDatabase;

import org.junit.Test;
import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class roomSearchTester {
    List<Room> allRooms;
    ArrayList<Room> roomArr;
    roomSearch cruiseSearch;
    String cruiseName;

    @BeforeEach
    void init(){
        cruiseName = "Carnival Cruise";
        //allRooms = gettingAllRooms(cruiseName);
        //allRooms = BrowseRoomController.getRooms(cruiseName);
        allRooms = RoomDatabase.getAllRooms();
        //roomArr = allRooms.toArray();
        cruiseSearch = new roomSearch(allRooms);
    }

    @Test
    public void printRoomTest() {
        allRooms.forEach(obj -> System.out.println(obj.getRoomNumber()));

    }
    @Test
    public void relevantRoomTest() {
        double d = 150.1;
        System.out.println((int)d);
        System.out.println(d);

    }

    public static List<Room> gettingAllRooms(String cruise) {
        List<Room> rooms = new LinkedList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Chas\\Soft.Eng\\Group project\\Titanic5\\Titanic2.0\\Titanic-Project\\src\\main\\resources\\room.csv"));
            String line;
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
