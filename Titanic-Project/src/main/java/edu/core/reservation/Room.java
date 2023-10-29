package edu.core.reservation;

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
    private int numberOfBeds;

    private String bedType;

    private boolean smokingAvailable;

    private double roomPrice;

    private roomType roomType;

    private int id;

    private int roomNumber;

    private boolean isBooked = false;

    private String cruise;

    /**
     * default constructor of the Room
     *
     */
    public Room(int roomNumber, int numberOfBeds, String bedType, boolean smokingAvailable, double roomPrice, String cruise) {
        this.roomNumber = roomNumber;
        this.numberOfBeds = numberOfBeds;
        this.bedType = bedType;
        this.smokingAvailable = smokingAvailable;
        this.roomPrice = roomPrice;
        this.cruise = cruise;
    }

    /**
     * sets the Room number
     *
     * @param roomNumber
     */
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
    /**
     * sets the NumberOfBeds in a room
     *
     * @param numberOfBeds
     */

    public void setNumberOfBeds(int numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }
    /**
     * Sets the type of Bed in a room
     *
     * @param bedType
     */
    public void setBedType(String bedType) {
        this.bedType = bedType;
    }
    /**
     * sets the smoking boolean
     *
     * @param smokingAvailable
     */
    public void setSmokingAvailable(boolean smokingAvailable) {
        this.smokingAvailable = smokingAvailable;
    }
    /**
     * After all the room information is set, this method
     * will take the number of beds, and the types, the room type,
     * to set the room price.
     *
     *
     */
    public void setRoomPrice(double roomPrice) {
        this.roomPrice = roomPrice;

    }

    /**
     * books the Room instance
     */
    public void bookRoom() {
        isBooked = true;
    }

    /**
     * unbooks the Room instance
     */
    public void unbookRoom() {
        isBooked = false;
    }

    public boolean isBooked() {
        return isBooked;
    }

    /**
     * retrieves the Room Number
     *
     *  @return returns the roomNumber
     */
    public int getRoomNumber() {
        return roomNumber;
    }
    /**
     * retrieves the number of Beds
     *
     *  @return the numberOfBeds
     */
    public int getNumberOfBeds() {
        return numberOfBeds;
    }
    /**
     * retrives the bed type
     *
     * @return the current bedType
     */
    public String getBedType() {
        return bedType;
    }
    /**
     * retrives the smoking availability
     *
     * @return true of false
     */
    public boolean getSmokingAvailable() {
        return smokingAvailable;
    }
    /**
     * retrieves the Room price
     *
     * @return the roomPrice
     */
    public double getRoomPrice() {
        return roomPrice;
    }

    /**
     * prints out the room details
     *
     * @return String: The converted String to display room details
     */
    @Override
    public String toString() {
        return "Room Number: " + roomNumber + "\n" +
                ", Number of Beds: " + numberOfBeds + "\n" +
                ", Bed Type: " + bedType + "\n" +
                ", Smoking Availability: " + smokingAvailable + "\n" +
                ", Room Price: $" + roomPrice + "\n" +
                ", Is Booked: " + isBooked;
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
     * @return hash of all the items in the Room class
     */
    @Override
    public int hashCode() {
        int result = 31; // A prime number as the initial value
        result = 31 * result + Objects.hashCode(numberOfBeds);
        result = 31 * result + Objects.hashCode(bedType);
        result = 31 * result + Objects.hashCode(smokingAvailable);
        result = 31 * result + Objects.hashCode(roomNumber);

        return result;
    }
}


