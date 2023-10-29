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

    private bedType bedType;

    String bedTypeStr;

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
    public Room(int roomNumber, int numberOfBeds, String bedTypeStr, boolean smokingAvailable, double roomPrice, String cruise) {
        this.roomNumber = roomNumber;
        this.numberOfBeds = numberOfBeds;
        this.bedTypeStr = bedTypeStr;
        this.smokingAvailable = smokingAvailable;
        this.roomPrice = roomPrice;
        this.cruise = cruise;
    }

    /**
     * Returns the cruise
     *
     * @return The cruise name associated with this room
     */
    public String getCruise() {
        return this.cruise;
    }

    /**
     * sets the NumberOfBeds in a room
     *
     * @param numberOfBeds
     */

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
     * will take the string bedType
     * to pick the room's type.
     *
     */
    public void setBedTypeFromStr(){
        for (bedType theType: edu.core.reservation.bedType.values()){
            if(theType.name().equals(bedTypeStr)){
                bedType = theType;
            }
        }
    }
    /**
     * Sets the type of Bed in a room
     *
     * @param bedTypeStr
     */
    public void setBedTypeStr(String bedTypeStr) {
        this.bedTypeStr = bedTypeStr;
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

        //will calculate the room price using this function
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
    public String getBedTypeStr() {
        return bedTypeStr;
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
        String smoking;
        if (smokingAvailable) {
            smoking = "Yes";
        }
        else {
            smoking = "No";
        }

        return "Room Number: " + roomNumber + ", Number of Beds: " + numberOfBeds +
                ", Bed Type: " + bedTypeStr +
                ", Smoking Availability: " + smoking +
                ", Room Price: $" + roomPrice;
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
        return java.util.Objects.equals(numberOfBeds, room.numberOfBeds) && java.util.Objects.equals(bedTypeStr, room.bedTypeStr) && java.util.Objects.equals(smokingAvailable, room.smokingAvailable) && java.util.Objects.equals(roomPrice, room.roomPrice);
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
        result = 31 * result + Objects.hashCode(bedTypeStr);
        result = 31 * result + Objects.hashCode(smokingAvailable);
        result = 31 * result + Objects.hashCode(roomNumber);

        return result;
    }
}


