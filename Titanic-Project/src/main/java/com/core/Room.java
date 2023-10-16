package com.core;

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




    public Room(){
        numberOfBeds = 0;
        bedType = 0;

    }
    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setNumberOfBeds(Integer numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    public void setBedType(Integer bedType) {
        this.bedType = bedType;
    }

    public void setSmokingAvailable(Boolean smokingAvailable) {
        this.smokingAvailable = smokingAvailable;
    }

    public void setRoomPrice(Double roomPrice) {
        this.roomPrice = roomPrice;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public Integer getNumberOfBeds() {
        return numberOfBeds;
    }

    public Integer getBedType() {
        return bedType;
    }

    public Boolean getSmokingAvailable() {
        return smokingAvailable;
    }

    public Double getRoomPrice() {
        return roomPrice;
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Room room = (Room) object;
        return java.util.Objects.equals(numberOfBeds, room.numberOfBeds) && java.util.Objects.equals(bedType, room.bedType) && java.util.Objects.equals(smokingAvailable, room.smokingAvailable) && java.util.Objects.equals(roomPrice, room.roomPrice);
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), numberOfBeds, bedType, smokingAvailable, roomPrice);
    }
}
