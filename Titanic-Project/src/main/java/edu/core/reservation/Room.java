package edu.core.reservation;

import edu.core.uniqueID.UniqueID;

import java.util.Objects;

/**
 * Documents information about a room
 *
 * <p>
 * Each room wil have a numberOfBeds, bedType, smokingAvailability and a RoomType
 * </p>
 *
 * @author Cole Hogan
 * @version 1.0
 * @see Room
 */
public class Room {
    //private Room room;
    private int numberOfBeds;

    private int bedType;

    private boolean smokingAvailable;

    private double roomPrice;

    private roomType roomType;

    private int id;

    private int roomNumber;


    /**
     * default constructor of the Room
     *
     */
    public Room(){
        numberOfBeds = 0;
        bedType = 0;
        id = new UniqueID().getId();
    }
    /**
     * sets the Room number/id
     *
     * @param roomNumber
     */
    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }
    /**
     * sets the NumberOfBeds in a room
     *
     * @param numberOfBeds
     */

    public void setNumberOfBeds(Integer numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }
    /**
     * Sets the type of Bed in a room
     *
     * @param bedType
     */
    public void setBedType(Integer bedType) {
        this.bedType = bedType;
    }
    /**
     * sets the smoking boolean
     *
     * @param smokingAvailable
     */
    public void setSmokingAvailable(Boolean smokingAvailable) {
        this.smokingAvailable = smokingAvailable;
    }
    /**
     * After all the room information is set, this method
     * will take the number of beds, and the types, the room type,
     * to set the room price.
     *
     *
     */
    public void setRoomPrice() {
        //TO DO

    }
    /**
     * retrieves the Room Number
     *
     *  @return returns the roomNumber
     */
    public Integer getRoomNumber() {
        return roomNumber;
    }
    /**
     * retrieves the number of Beds
     *
     *  @return the numberOfBeds
     */
    public Integer getNumberOfBeds() {
        return numberOfBeds;
    }
    /**
     * retrives the bed type
     *
     * @return the current bedType
     */
    public Integer getBedType() {
        return bedType;
    }
    /**
     * retrives the smoking availability
     *
     * @return true of false
     */
    public Boolean getSmokingAvailable() {
        return smokingAvailable;
    }
    /**
     * retrieves the Room price
     *
     * @return the roomPrice
     */
    public Double getRoomPrice() {
        return roomPrice;
    }
    /**
     * overrtides the equals operation to aid in comparing Rooms with eachother
     *
     * @param object: the room being compared with this
     * @return returns a true or false statement if object == this
     */
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Room room = (Room) object;
        return java.util.Objects.equals(numberOfBeds, room.numberOfBeds) && java.util.Objects.equals(bedType, room.bedType) && java.util.Objects.equals(smokingAvailable, room.smokingAvailable) && java.util.Objects.equals(roomPrice, room.roomPrice);
    }
    /**
     * overrides the hashing of the rooms, allowing the system to put rooms in maps and sets
     *
     * @return hash of all the items in the Room  class
     */
    public int hashCode() {
        return Objects.hash(super.hashCode(), numberOfBeds, bedType, smokingAvailable, roomPrice);
    }
}


