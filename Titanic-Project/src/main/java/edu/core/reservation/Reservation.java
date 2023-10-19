package edu.core.reservation;
import edu.core.uniqueID.UniqueID;

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
    private Integer days;
    private String startCountry;
    private String endCountry;
    private Integer roomNumber;

    private Room room;

    //private roomClass class;


    ///Constructor might be adjusted to where rather than relying on set and get to add into the Reservation,
    ///A constructor initialized with all the information needed will be used.
    public Reservation(Room room) {
        id = new UniqueID().getId();
        this.room = room;
    }

    public Integer getId() {
        return id;
    }

    public Integer getDays() {
        return days;
    }

    public String getStartCountry() {
        return startCountry;
    }

    public String getEndCountry() {
        return endCountry;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public void setStartCountry(String startCountry) {
        this.startCountry = startCountry;
    }

    public void setEndCountry(String endCountry) {
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
                Objects.equals(roomNumber, that.roomNumber) &&
                Objects.equals(numberOfBeds, that.numberOfBeds) &&
                Objects.equals(bedType, that.bedType) &&
                Objects.equals(smokingAvailable, that.smokingAvailable) &&
                Objects.equals(roomPrice, that.roomPrice);
    }

    @Override
    public int hashCode() {
        int result = 31;
        return Objects.hash(id, days, startCountry, endCountry, roomNumber, numberOfBeds, bedType, smokingAvailable, roomPrice);
    }
};


//getter and setter for Room will be added later.