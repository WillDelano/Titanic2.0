package edu.Database;

import com.opencsv.CSVWriter;
import edu.core.cruise.Country;
import edu.core.reservation.Reservation;
import edu.core.users.Guest;
import edu.core.reservation.Room;
import edu.core.users.User;
import edu.databaseAccessors.CountryDatabase;
import edu.databaseAccessors.RoomDatabase;
import edu.uniqueID.UniqueID;

import java.io.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Database to record reservations
 *
 * This class documents a collection of reservations and reads/writes to the database file
 *
 * @author William Delano
 * @version 1.0
 */
public class ReservationDatabase {
    private static Map<User, Set<Reservation>> reservationDatabase;
    private static String fileName = "C:\\Users\\Colet\\Documents\\GIT\\Titanic2.0\\Titanic-Project\\src\\main\\resources\\reservationList.csv";
    private static String reservedRoomFile = "C:\\Users\\Colet\\Documents\\GIT\\Titanic2.0\\Titanic-Project\\src\\main\\resources\\reserved_rooms.csv";

    /**
     * Creates the database for reservations
     *
     */
    /**
     * Creates the database for reservations
     *
     */
    public ReservationDatabase() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            reader.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the reservation database size
     **
     * @return The reservation database size
     */
    public static int getReservationDatabaseSize() {
        int count = 0;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;

            /*
             * CSV style
             * split[0] = reservation id
             * split[1] = user username
             * split[2] = duration of trip in days
             * split[3] = starting country
             * split[4] = ending country
             * split[5] = room id
             * split[6] = start date of reservation
             * split[7] = end date of reservation
             */

            line = reader.readLine();

            while(line != null) {
                count++;

                line = reader.readLine();
            }

            reader.close();
        } catch(IOException e){
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Returns the reservation database
     *
     * @return The reservation database
     */
    public static Set<Reservation> getReservations(Guest guest) {
        Set<Reservation> guestReservations = new HashSet<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;

            /*
             * CSV style
             * split[0] = reservation id
             * split[1] = user username
             * split[2] = duration of trip in days
             * split[3] = starting country
             * split[4] = ending country
             * split[5] = room id
             * split[6] = start date of reservation
             * split[7] = end date of reservation
             */

            line = reader.readLine();

            while(line != null) {
                String[] split = line.split(",");

                //if the guest username of the reservation matches the guest
                if (Objects.equals(split[1], guest.getUsername())) {
                    //get room that guest is in with id
                    Room guestRoom = RoomDatabase.getRoom(Integer.parseInt(split[5]));

                    //get countries from database off name
                    Country startCountry = CountryDatabase.getCountry(split[3]);
                    Country endCountry = CountryDatabase.getCountry(split[4]);

                    //turn string of dates into local date format
                    LocalDate startDate = LocalDate.parse(split[6]);
                    LocalDate endDate = LocalDate.parse(split[7]);

                    Reservation reservation = new Reservation(new UniqueID().getId(),guest, guestRoom, startDate, endDate, startCountry, endCountry);

                    //set the id of the reservation to the stored value (the constructor will initially give it a unique one because it's "new")
                    reservation.setId(Integer.valueOf(split[0]));

                    //add to the set of reservations for the current guest
                    guestReservations.add(reservation);
                }
                line = reader.readLine();
            }

            reader.close();
        } catch(IOException e){
            e.printStackTrace();
        }
        return guestReservations;
    }

    /**
     * Returns all the usernames of users with a reservation
     *
     * @return A set of usernames
     */
    public static Set<String> getAllUsernames() {
        Set<String> allUsernames = new HashSet<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;

            /*
             * CSV style
             * split[0] = reservation id
             * split[1] = user username
             * split[2] = duration of trip in days
             * split[3] = starting country
             * split[4] = ending country
             * split[5] = room id
             * split[6] = start date of reservation
             * split[7] = end date of reservation
             */

            line = reader.readLine();

            while(line != null) {
                String[] split = line.split(",");

                //add the username
                allUsernames.add(split[1]);

                line = reader.readLine();
            }

            reader.close();
        } catch(IOException e){
            e.printStackTrace();
            System.err.println("Could not retrieve all usernames.");
        }
        return allUsernames;
    }

    //Fixme: Reservation has User user, Room room, LocalDate startDate,
    //LocalDate endDate, Country startCountry, Country endCountry

    /**
     * Operation to check if reservation is valid
     *
     * @param u specified user to check reservations
     * @param reservationCheck specified reservation to validate
     *
     * @return Truth value of whether reservation is valid for user
     */
    public boolean isValidReservation(User u,Reservation reservationCheck){
        //THIS will be checked before removing a database
        boolean reservationExists = false;
        //iterate through data structure then search if specific reservation exists
        for(Map.Entry<User,Set<Reservation>> e: reservationDatabase.entrySet()){
            if(e.getKey().equals(u)){
                //check if reservation exists in set
                if(e.getValue().contains(reservationCheck)){
                    reservationExists = true;
                }
            }
        }

        return reservationExists;
    }



    /**
     * Operation to check if reservation is valid
     *
     * @param newReservation specified reservation to add
     *
     */
    public static void addReservation(Reservation newReservation) {
        //boolean added = reservationDatabase.get(newReservation.getUser()).add(newReservation);
        //now add reservation to file.
        //Agents and admins are hardcoded on the backend

        //if the reservation was not a duplicate

        if(ReservationDatabase.hasReservation(newReservation)) {
            /*

             * CSV style
             * split[0] = reservation id
             * split[1] = user username
             * split[2] = duration of trip in days
             * split[3] = starting country
             * split[4] = ending country
             * split[5] = room number
             * split[6] = start date of reservation
             * split[7] = end date of reservation
             */

            //write to csv
            System.err.println("ID: " + newReservation.getUser().getId());
            String toWrite = newReservation.getId() + "," + newReservation.getUser().getUsername() + "," +
                    newReservation.getDays() + "," + newReservation.getStartCountry().getName() + ","
                    + newReservation.getEndCountry().getName() + "," + newReservation.getRoom().getRoomNumber()
                    + "," + newReservation.getStartDate() + "," + newReservation.getEndDate() + "\n";

            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new FileWriter(reservedRoomFile, true));
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
    }


    /**
     * Operation to check if a User has valid reservations
     *
     * @param user user to check database for
     *
     */
    public static boolean hasUser(User user) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;

            while((line = reader.readLine()) != null) {
                String[] split = line.split(",");

                //if the user exists in the database, return true
                if (Integer.parseInt(split[1]) == user.getId()) {
                    return true;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }



    /**
     * Operation to check if a specific room is in reservation
     *
     * @param roomNumber room number to check database for
     *
     */
    public static boolean hasRoom(int roomNumber) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;

            line = reader.readLine();
            while(line != null) {
                String[] split = line.split(",");

                //if the room exists in the database, return true
                if (Integer.parseInt(split[5]) == roomNumber) {
                    return true;
                }

                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }



    /**
     * Operation to check if the database has a reservation
     *
     * @param reservation specified reservation to check
     *
     */
    public static boolean hasReservation(Reservation reservation) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;

            while((line = reader.readLine()) != null) {
                String[] split = line.split(",");

                /*
                 * CSV style
                 * split[0] = reservation id
                 * split[1] = user id
                 * split[2] = duration of trip in days
                 * split[3] = starting country
                 * split[4] = ending country
                 * split[5] = room number
                 * split[6] = start date of reservation
                 * split[7] = end date of reservation
                 */

                //if the room number exists in a reservation already, check if everything's the same
                if (Integer.parseInt(split[2]) == reservation.getDays() && Objects.equals(split[3], reservation.getStartCountry().getName()) &&
                        Objects.equals(split[4], reservation.getEndCountry().getName()) && Objects.equals(Integer.parseInt(split[5]), reservation.getRoom().getRoomNumber()) &&
                        Objects.equals(split[6], reservation.getStartDate().toString()) && Objects.equals(split[7], reservation.getEndDate().toString())) {
                    return true;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    //TODO: Write using our database when we start that
    public static void deleteReservation(Reservation reservation) {
        System.err.println("Trying to delete reservation: " + reservation.getId() + " with owner " + reservation.getUser().getUsername());
    }

    //TODO: Write using our database when we start that
    public static void updateReservation(Reservation reservation, String checkout, String roomNumber) {
        System.err.println("Trying to update reservation: " + reservation.getId() + " from " + reservation.getUser().getUsername());
        System.err.println("To new checkout: " + checkout + " and new room number: " + roomNumber);
    }
}