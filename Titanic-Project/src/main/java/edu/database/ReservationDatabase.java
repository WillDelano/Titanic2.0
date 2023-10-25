package edu.database;

import edu.core.cruise.Country;
import edu.core.reservation.Reservation;
import edu.core.users.Guest;
import edu.core.reservation.Room;
import edu.core.users.User;

import java.io.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Database to record reservations
 *
 * This class documents a collection of reservations mapped with users
 *
 * @author William Delano
 * @version 1.0
 */
public class ReservationDatabase {
    private static Map<User, Set<Reservation>> reservationDatabase;
    private static String fileName = "C:\\Users\\Owner\\Desktop\\Titanic2.0\\Titanic-Project\\src\\main\\java\\edu\\repositories\\reservationList.csv";


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
            * split[1] = user id
            * split[2] = duration of trip in days
            * split[3] = starting country
            * split[4] = ending country
            * split[5] = room id
            * split[6] = start date of reservation
            * split[7] = end date of reservation
            */
            while((line = reader.readLine()) != null) {
                String[] split = line.split(",");

                //if the guest id of the reservation matches the guest
                if (Integer.parseInt(split[0]) == guest.getId()) {
                    //get room that guest is in with id
                    Room guestRoom = RoomDatabase.getRoom(Integer.parseInt(split[5]));

                    //get countries from database off name
                    Country startCountry = CountryDatabase.getCountry(split[3]);
                    Country endCountry = CountryDatabase.getCountry(split[4]);

                    //turn string of dates into local date format
                    LocalDate startDate = LocalDate.parse(split[6]);
                    LocalDate endDate = LocalDate.parse(split[7]);

                    Reservation reservation = new Reservation(guest, guestRoom, startDate, endDate, startCountry, endCountry);

                    //set the id of the reservation to the stored value (the constructor will initially give it a unique one because it's "new")
                    reservation.setId(Integer.valueOf(split[0]));

                    //add to the set of reservations for the current guest
                    guestReservations.add(reservation);
                }
            }

            reader.close();
        } catch(IOException e){
            e.printStackTrace();
        }
        return guestReservations;
    }

    //Fixme: Reservation has User user, Room room, LocalDate startDate,
    // LocalDate endDate, Country startCountry, Country endCountry

    /*public boolean isValidReservation(Reservation reservationCheck){
        return getReservationDatabase().get(reservationCheck.getUser()).contains(reservationCheck);
    }*/
    public static void addReservation(Reservation newReservation) {

        //boolean added = reservationDatabase.get(newReservation.getUser()).add(newReservation);
        //now add reservation to file.
        //Agents and admins are hardcoded on the backend

        //if the reservation was not a duplicate
        if(!ReservationDatabase.hasReservation(newReservation)) {
            try {
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
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

                //write to csv
                String toWrite = newReservation.getId() + "," + newReservation.getUser().getId() + "," +
                         newReservation.getDays() + "," + newReservation.getStartCountry().getName() + ","
                        + newReservation.getEndCountry().getName() + "," + newReservation.getRoom().getRoomNumber()
                        + "," + newReservation.getStartDate() + "," + newReservation.getEndDate();

                FileWriter write = new FileWriter(fileName, true);
                writer.write(toWrite);
                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

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

    public static boolean hasReservation(Reservation reservation) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;

            while((line = reader.readLine()) != null) {
                String[] split = line.split(",");

                System.err.println("Comparing csv: " + split[0] + " with " + reservation.getId());

                //if the reservation id exists in the database, return true
                if (Integer.parseInt(split[0]) == reservation.getId()) {
                    return true;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}


