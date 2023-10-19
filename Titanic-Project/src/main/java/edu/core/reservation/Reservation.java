package edu.core.reservation;
import edu.core.cruise.Country;
import edu.core.uniqueID.UniqueID;

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
    private int id;
    private long days;
    private Country startCountry;
    private Country endCountry;
    private int roomNumber;
    private Room room;

    public Reservation(Room room, LocalDate startDate, LocalDate endDate, Country startCountry, Country endCountry) {
        this.id = new UniqueID().getId();
        this.room = room;
        this.roomNumber = room.getRoomNumber();
        this.days = ChronoUnit.DAYS.between(startDate, endDate);
        this.startCountry = startCountry;
        this.endCountry = endCountry;
    }

    public int getId() {
        return id;
    }

    public long getDays() {
        return days;
    }

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id) && Objects.equals(days, that.days) &&
                Objects.equals(startCountry, that.startCountry) &&
                Objects.equals(endCountry, that.endCountry) &&
                Objects.equals(roomNumber, that.roomNumber); /* &&
                Objects.equals(numberOfBeds, that.numberOfBeds) &&
                Objects.equals(bedType, that.bedType) &&
                Objects.equals(smokingAvailable, that.smokingAvailable) &&
                Objects.equals(roomPrice, that.roomPrice); */
    }

    @Override
    public int hashCode() {
        int result = 31;
        return Objects.hash(id, days, startCountry, endCountry, roomNumber); /*( numberOfBeds, bedType, smokingAvailable, roomPrice);*/
    }
};


//getter and setter for Room will be added later.