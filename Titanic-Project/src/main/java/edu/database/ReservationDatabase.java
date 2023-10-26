package edu.database;

import com.opencsv.CSVWriter;
import edu.core.cruise.Country;
import edu.core.reservation.Reservation;
import edu.core.users.Guest;
import edu.core.reservation.Room;
import edu.core.users.User;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

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
    private static String fileName = "/reservationList.csv";


    /**
     * Creates the database for reservations
     *
     */
    public ReservationDatabase() {
        try {
            InputStream is = ReservationDatabase.class.getResourceAsStream(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
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
            InputStream is = ReservationDatabase.class.getResourceAsStream(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
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
                if (Integer.parseInt(split[1]) == guest.getId()) {
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
        String reservedRoomFile = "reserved_rooms.csv";

        //boolean added = reservationDatabase.get(newReservation.getUser()).add(newReservation);
        //now add reservation to file.
        //Agents and admins are hardcoded on the backend

        //if the reservation was not a duplicate
        if(!ReservationDatabase.hasReservation(newReservation)) {

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

            //write to csv
            String toWrite = newReservation.getId() + "," + newReservation.getUser().getId() + "," +
                     newReservation.getDays() + "," + newReservation.getStartCountry().getName() + ","
                    + newReservation.getEndCountry().getName() + "," + newReservation.getRoom().getRoomNumber()
                    + "," + newReservation.getStartDate() + "," + newReservation.getEndDate();

            try (CSVWriter writer = new CSVWriter(new FileWriter(reservedRoomFile, true))) {
                writer.writeNext(toWrite.split(","));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeReservation(int userId,Reservation toRemove) throws IOException {
        //precondition is that User has this specific reservation

        //first remove reservation from the map
        for(Map.Entry<User,Set<Reservation>> e : reservationDatabase.entrySet()){
            //if we are at the correct user with the specified reservation
            if (( e. getKey().getId()== userId) && e.getValue().contains(toRemove)){
                //we can now remove this reservation
                e.getValue().remove(toRemove);
            }
        }

        //now remove reservation from the file
        ArrayList<String> newFileLines= new ArrayList<>();

        String lineToCut = toRemove.getId() + "," + toRemove.getUser().getId() + "," +
                toRemove.getDays() + "," + toRemove.getStartCountry().getName() + ","
                + toRemove.getEndCountry().getName() + "," + toRemove.getRoom().getRoomNumber()
                + "," + toRemove.getStartDate() + "," + toRemove.getEndDate();

        String line;

        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        while((line  = reader.readLine()) != null){
            newFileLines.add(line);
        }
        reader.close();

        //remove specified line
        for(String s:newFileLines){
            if(s.contains(lineToCut)){
                newFileLines.remove(s);
            }
        }

        //overrides old file
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        for(String newFileLine: newFileLines){
            writer.write(newFileLine);
            writer.newLine();
        }

        writer.close();
        reader.close();


    }

    public static boolean hasUser(User user) {
        try {
            InputStream is = ReservationDatabase.class.getResourceAsStream(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
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
            InputStream is = ReservationDatabase.class.getResourceAsStream(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
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
}


