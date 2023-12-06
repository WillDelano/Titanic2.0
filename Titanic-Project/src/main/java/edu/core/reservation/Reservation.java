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

    /**
     * This function creates a reservation with given values
     *
     * @param id the id to create the reservation with
     * @param user to assign to the reservation
     * @param room room to assign to the reservation
     * @param startDate starting date of the reservation
     * @param endDate ending date of the reservation
     * @param startCountry starting country of the reservation
     * @param endCountry ending country of the reservation
     */
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
     * This function sets a reservation to checked in
     *
     * @param newCheckIn
     */
    public void setCheckedIn(boolean newCheckIn){
        checkedIn = newCheckIn;
    }

    /**
     * This function checks if a reservation is checked in
     *
     * @return true if the reservation has been checked in
     */
    public boolean getCheckedIn(){
        return checkedIn;
    }

    /**
     * This function returns the id of a reservation
     *
     * @return the id of the reservation
     */
    public int getId() {
        return id;
    }

    /**
     * This function returns the user assigned with a reservation
     *
     * @return the user assigned to the reservation
     */
    public User getUser() {
        return user;
    }

    /**
     * This function returns the duration of the reservation
     *
     * @return the duration of the reservation in days
     */
    public long getDays() {
        return days;
    }

    /**
     * This function returns the room assigned to a reservation
     *
     * @return the room assigned to the reservation
     */
    public Room getRoom() { return room; }

    /**
     * This function returns the starting country of a reservation
     *
     * @return the starting country of the reservation
     */
    public Country getStartCountry() {
        return startCountry;
    }

    /**
     * This function returns the ending country of a reservation
     *
     * @return the ending country of the reservation
     */
    public Country getEndCountry() {
        return endCountry;
    }

    /**
     * This function returns the start date of a reservation
     *
     * @return the start date of the reservation
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * This function returns the end date of a reservation
     *
     * @return the end date of the reservation
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * This function sets the id of a reservation
     *
     * @param id the id to set for the reservation
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This function sets the amount of days in a reservation
     *
     * @param days amount of days in the reservation
     */
    public void setDays(Integer days) {
        Reservation oldReservation = this;
        this.days = days;
    }

    /**
     * This function sets the start country in the reservation
     *
     * @param startCountry start country in the reservation
     */
    public void setStartCountry(Country startCountry) {
        Reservation oldReservation = this;
        this.startCountry = startCountry;
    }

    /**
     * This function sets the end country in the reservation
     *
     * @param endCountry end country in the reservation
     */
    public void setEndCountry(Country endCountry) {
        Reservation oldReservation = this;
        this.endCountry = endCountry;
    }

    /**
     * This function sets the start date in a reservation
     *
     * @param startDate start date to set
     */
    public void setStartDate(LocalDate startDate) {
        Reservation oldReservation = this;
        this.startDate = startDate;
    }

    /**
     * This function sets the end date in a reservation
     *
     * @param endDate end date to set
     */
    public void setEndDate(LocalDate endDate) {
        Reservation oldReservation = this;
        this.endDate = endDate;
    }

    /**
     * This function sets the room for a reservation
     *
     * @param room room to assign to the reservation
     */
    public void setRoom(Room room){
        Reservation oldReservation = this;
        this.room = room;
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