package edu.core.reservation;
import edu.core.cruise.Country;
import edu.core.uniqueID.UniqueID;
import edu.core.users.User;

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

        room.setBooked(true);
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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public void setStartCountry(Country startCountry) {
        this.startCountry = startCountry;
    }

    public void setEndCountry(Country endCountry) {
        this.endCountry = endCountry;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
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