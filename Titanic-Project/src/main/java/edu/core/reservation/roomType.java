package edu.core.reservation;

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
    Economy(100.0), Comfort(200.0), Business(300.0), Executive(400.0);

    private double price;

    /**
     * default constructor, sets a room price at Economy
     */
    roomType() {

        this.price = 100.0;
    }

    /**
     * constructor that will assign a RoomType with it's respective price
     *
     * @param newPrice:assigns a new price to a room type
     */
    roomType(double newPrice) {
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
