package edu.core.reservation;
import edu.core.cruise.Country;
import uniqueID.UniqueID;
import edu.core.users.User;
import edu.database.ReservationDatabase;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


/**
 * Documents information of a single reservation.
 *
 * <p>
 * Each Room object should have a wait-list attribute to hold all the Users waiting for the room.
 * </p>
 *
 * @author Cole Hogan
 * @version 1.1
 * @see Room
 */
public class Reservation {
    private User user;
    private int id;
    private long days;
    private Country startCountry;
    private Country endCountry;
    private int roomNumber;
    private Room room;

    private LocalDate startDate;

    private LocalDate endDate;

    private ReservationDatabase reservations;


    public Reservation(User user, Room room, LocalDate startDate, LocalDate endDate, Country startCountry, Country endCountry) {
        // if room is already booked, throw error
        if (room.isBooked()) {
            throw new IllegalArgumentException("Room is already booked!");
        }

        this.user = user;
        this.id = new UniqueID().getId();
        this.room = room;
        this.roomNumber = room.getRoomNumber();
        this.days = ChronoUnit.DAYS.between(startDate, endDate);
        this.startCountry = startCountry;
        this.endCountry = endCountry;
        this.startDate = startDate;
        this.endDate = endDate;

        room.bookRoom();
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public long getDays() {
        return days;
    }

    public Room getRoom() { return room; }

    public Country getStartCountry() {
        return startCountry;
    }

    public Country getEndCountry() {
        return endCountry;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDays(Integer days) {
        Reservation oldReservation = this;
        this.days = days;
        alterReservationList(oldReservation);
    }

    public void setStartCountry(Country startCountry) {
        Reservation oldReservation = this;
        this.startCountry = startCountry;
        alterReservationList(oldReservation);
    }

    public void setEndCountry(Country endCountry) {
        Reservation oldReservation = this;
        this.endCountry = endCountry;
        alterReservationList(oldReservation);
    }

    public void setStartDate(LocalDate startDate) {
        Reservation oldReservation = this;
        this.startDate = startDate;
        alterReservationList(oldReservation);
    }

    public void setEndDate(LocalDate endDate) {
        Reservation oldReservation = this;
        this.endDate = endDate;
        alterReservationList(oldReservation);
    }

    public void setRoom(Room room){
        Reservation oldReservation = this;
        this.room = room;
        alterReservationList(oldReservation);
    }
    /**
     * Operation to alter reservation database based on specified user changes
     *
     * @param oldReservation   The given old reservation to alter.
     */
    private void alterReservationList(Reservation oldReservation){
        //iterate through set of values in map of reservations
        for(Map.Entry<User, Set<Reservation>> r : ReservationDatabase.getReservationDatabase().entrySet()){
            //find specified user attached to reservation
            if(r.getKey().equals(getUser())){
                //now iterate through reservations for specified user
                for(Reservation s: r.getValue()){
                    //now find this specific reservation then change details on list
                    if(s.equals(oldReservation)){
                        s = this;
                    }
                }
            }
        }
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;

        //check if they are the same reservation by unique id
        if (id == that.id) {
            return true;
        }
        //if they have different ids, check if they are the same reservation details
        else if (Objects.equals(days, that.days) &&
                Objects.equals(startCountry, that.startCountry) &&
                Objects.equals(endCountry, that.endCountry) &&
                Objects.equals(roomNumber, that.roomNumber) &&
                Objects.equals(room, that.room) /*&&
                Objects.equals(numberOfBeds, that.numberOfBeds) &&
                Objects.equals(bedType, that.bedType) &&
                Objects.equals(smokingAvailable, that.smokingAvailable) &&
                Objects.equals(roomPrice, that.roomPrice);
                */) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, room, roomNumber, days, startCountry, endCountry);
    }
};


//getter and setter for Room will be added later.