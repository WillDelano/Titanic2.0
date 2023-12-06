package edu.core.reservation;
import edu.core.cruise.Country;
import edu.core.users.User;
import edu.databaseAccessors.ReservationDatabase;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;


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

    private boolean checkedIn = false;

    private ReservationDatabase reservations;


    public Reservation(int id, User user, Room room, LocalDate startDate, LocalDate endDate, Country startCountry, Country endCountry) {
        // if room is already booked, throw error
        if (room.isBooked()) {
            throw new IllegalArgumentException("Room is already booked!");
        }

        this.user = user;
        this.id = id;
        this.room = room;
        this.roomNumber = room.getRoomNumber();
        this.days = ChronoUnit.DAYS.between(startDate, endDate);
        this.startCountry = startCountry;
        this.endCountry = endCountry;
        this.startDate = startDate;
        this.endDate = endDate;

        room.bookRoom();
    }
    /**
     * Sets the check in
     *
     * @param newCheckIn
     */
    public void setCheckedIn(boolean newCheckIn){
        checkedIn = newCheckIn;
    }
    /**
     * getter for checkin
     *
     * @return checkedIn
     */
    public boolean getCheckedIn(){
        return checkedIn;
    }
    /**
     * getter for id
     *
     * @return id of reservation
     */
    public int getId() {
        return id;
    }
    /**
     * getter for user on reservation
     *
     * @return user
     */

    public User getUser() {
        return user;
    }
    /**
     * getter for reservation duration
     *
     * @return days/duration of the reservation
     */
    public long getDays() {
        return days;
    }
    /**
     * getter for the room reserved in reservation
     *
     * @return room
     */
    public Room getRoom() { return room; }
    /**
     * getter for reservation start country
     *
     * @return startCountry
     */

    public Country getStartCountry() {
        return startCountry;
    }
    /**
     * getter for reservation endCountry
     *
     * @return the last country for reservation
     */
    public Country getEndCountry() {
        return endCountry;
    }
    /**
     * getter for reservation start date
     *
     * @return start date
     */
    public LocalDate getStartDate() {
        return startDate;
    }
    /**
     * getter for reservation end date
     *
     * @return end Date
     */
    public LocalDate getEndDate() {
        return endDate;
    }
    /**
     * setter for reservation Id
     *
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * setter for the how many days the reservation lasts for
     *
     * @param days
     */
    public void setDays(Integer days) {
        Reservation oldReservation = this;
        this.days = days;
        alterReservationList(oldReservation);
    }
    /**
     * setter for the start country of the reservation
     *
     * @param startCountry
     */
    public void setStartCountry(Country startCountry) {
        Reservation oldReservation = this;
        this.startCountry = startCountry;
        alterReservationList(oldReservation);
    }
    /**
     * setter for the last country for the reservation
     *
     * @param endCountry
     */
    public void setEndCountry(Country endCountry) {
        Reservation oldReservation = this;
        this.endCountry = endCountry;
        alterReservationList(oldReservation);
    }
    /**
     * setter for the starting date for the reservation
     *
     * @param startDate
     */
    public void setStartDate(LocalDate startDate) {
        Reservation oldReservation = this;
        this.startDate = startDate;
        alterReservationList(oldReservation);
    }
    /**
     * setter for the endDate for the reservation
     *
     * @param endDate
     */
    public void setEndDate(LocalDate endDate) {
        Reservation oldReservation = this;
        this.endDate = endDate;
        alterReservationList(oldReservation);
    }
    /**
     * setter for the room of allocated to the reservation
     *
     * @param room
     */
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
        /*for(Map.Entry<User, Set<Reservation>> r : ReservationDatabase.getReservationDatabase().entrySet()){
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
        }*/
    }

    /**
     * Overrides the built-in equals function of an object
     *
     * @return true if the objects are equal in value
     */
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

    /**
     * Overrides the built-in hashing function of an object
     *
     * @return the hashed value
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, user, room, roomNumber, days, startCountry, endCountry);
    }
};