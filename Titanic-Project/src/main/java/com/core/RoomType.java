package org.example;
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
public enum RoomType {
    Economy(100.0),Comfort(200.0),Business(300.0),Executive(400.0);

    private double price;


    RoomType(){
        this.price = 100.0;
    }
    RoomType(double price) {
        this.price = price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}
