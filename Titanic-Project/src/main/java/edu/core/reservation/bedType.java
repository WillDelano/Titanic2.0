package edu.core.reservation;
/**
 * each room has a type of bed
 *
 * <p>
 * There are 3 types of beds each with their own respective price. Of course some deals, like
 * having two queens vs one king might vary in pricing but the travel agent can override prices.
 * </p>
 *
 * @author Cole Hogan
 * @version 1.0
 * @see Room
 */
public enum bedType {
    doubleSize(30.0), queenSize(40.0),kingSize(50.0);

    private double price;

    /**
     * default constructor
     */
    bedType(){
        price = 30.0;
    }
    /**
     * constructor that will assign a bedType with its respective price
     *
     * @param newPrice:assigns a new price to a room type
     */
    bedType(double newPrice){
        price = newPrice;
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
