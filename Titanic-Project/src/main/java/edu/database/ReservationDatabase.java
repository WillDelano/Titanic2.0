package edu.database;

import edu.core.reservation.Reservation;
import edu.core.users.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
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
    private String fileName = "C:\\Users\\Michael O\\IdeaProjects\\Titanic2.0\\Titanic-Project\\src\\main\\java\\edu\\database\\reservationList.csv";


    /**
     * Creates the database for reservations
     *
     */
    public ReservationDatabase() {
        reservationDatabase = new HashMap<>();
    }

    /**
     * Returns the reservation database
     *
     * @return The reservation database
     */
    public static Map<User, Set<Reservation>> getReservationDatabase() {
        return reservationDatabase;
    }

    //Fixme: Reservation has User user, Room room, LocalDate startDate,
    // LocalDate endDate, Country startCountry, Country endCountry

    public boolean isValidReservation(Reservation reservationCheck){
        return getReservationDatabase().get(reservationCheck.getUser()).contains(reservationCheck);
    }
    public void addReservation(Reservation newReservation){

        boolean added = reservationDatabase.get(newReservation.getUser()).add(newReservation);
        //now add reservation to file.
        //Agents and admins are hardcoded on the backend

        //if the reservation was not a duplicate
        if(added) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

                //  String username,String password,int id, String firstName, String lastName,int rewardPoints, String email
                String toWrite = newReservation.getUser().getId()+","+newReservation.getRoom().getRoomNumber()+","+
                        newReservation.getStartDate().toString()+","+newReservation.getEndDate().toString()+","
                        +newReservation.getStartCountry().toString()+","+newReservation.getEndCountry().toString();
                FileWriter write = new FileWriter(fileName, true);
                writer.write(toWrite);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}


