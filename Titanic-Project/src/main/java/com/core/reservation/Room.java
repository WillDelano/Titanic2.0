package com.core.reservation;

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
    private Integer numberOfBeds;

    private Integer bedType;

    private Boolean smokingAvailable;

    private Double roomPrice;

    private roomType roomType;



    /**
     * default constructor of the Room
     *
     */
    public Room(){
        numberOfBeds = 0;
        bedType = 0;

    }
    /**
     * sets the Room number/ id
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

/**
 * Has types of Rooms along with respective prices of those rooms
 *
 * <p>
 * There are four different room types, as the user looks to find a type room, they will have a different luxery of
 * room based on their pick. With diffferent luxery type, there are different prices.
 * </p>
 *
 * @author Cole Hogan
 * @version 1.0
 * @see Room
 */
public enum roomType {
    Economy(100.0),Comfort(200.0),Business(300.0),Executive(400.0);

    private double price;
    /**
     * default constructor, sets a room price at Economy
     *
     */
    RoomType(){

        this.price = 100.0;
    }
    /**
     * constructor that will assign a RoomType with it's respective price
     *
     * @param newPrice:assigns a new price to a room type
     */
    RoomType (double newPrice){
        this.price = newPrice;
    }
    /**
     * sets the price of a roomType
     *
     * @param newPrice:assigns a new price to a room type
     */
    public void setPrice(double newPrice) {

        this.price = newPrice;
    }
    /**
     * sets the price of a roomType
     *
     * @return the price of a RoomType
     */
    public double getPrice() {

        return price;
    }
}


