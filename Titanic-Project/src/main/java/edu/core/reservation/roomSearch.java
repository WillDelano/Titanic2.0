package edu.core.reservation;

import edu.core.cruise.Cruise;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Used to search for rooms and filter room searches
 *
 * More detailed description of the class, including its purpose, usage, and any other relevant information.
 *
 * @author Chas Doughtry
 */
public class roomSearch {
    List<Room> allRooms;
    boolean price;
    enum priceSortType {NONE, ASCENDING, DESCENDING}
    enum bedCountSortType {NONE, ASCENDING, DESCENDING}
    //enum bedSortType {  };
    enum smokingSortType {ALL, NON_SMOKING, SMOKING}
    priceSortType priceSort = priceSortType.NONE;
    smokingSortType smokeType = smokingSortType.ALL;

    enum roomSortType{/* add room types here */};

    /**
     * RoomSearch Constructor
     *
     * @param cruise - cruise object to retrieve rooms from.
     */

    roomSearch(Cruise cruise){
        allRooms = cruise.getRoomList();
    }
    public List<Room> findRooms(String line){
        String[] traits = line.split( " ");
        List<Room> relevantRooms = new ArrayList<>();

        // iterate through rooms and add relevant rooms to new list
        for(Room obj : allRooms){

            if(!obj.isBooked()) {

                // iterate through traits to find in room's attributes
                for (String s : traits) {

                    /*if () {
                        //relevantRooms.add(obj);
                    }*/
                }
            }
        }
        sortAndFilterRooms(relevantRooms);

        return relevantRooms;
    }

    /**
     * filters allRooms by the enabled filters and sorting type
     *
     * @param rooms list of rooms to be sorted
     * @return filtered and sorted list of rooms
     */
    List<Room> sortAndFilterRooms (List<Room> rooms){
        List<Room> sortedRooms = rooms;

        // filter rooms
        if(smokeType != smokingSortType.ALL) {
            filterBySmokingType(sortedRooms);
        }

        // sort rooms
        if(priceSort != priceSortType.NONE){
            sortRoomsByPrice(sortedRooms);
        }

        return sortedRooms;
    }

    /**
     * setter for type of price sorting
     *
     * //@param type of price sorting
     */
    void setPriceSorting(priceSortType type){
        switch (type){
            case NONE:
                priceSort = priceSortType.NONE;

            case ASCENDING:
                priceSort = priceSortType.ASCENDING;

            case DESCENDING:
                priceSort = priceSortType.DESCENDING;

        }
    }

    /**
     * sorts rooms by price
     *
     * //@param list of rooms to sort
     */
    void sortRoomsByPrice(List<Room> roomList){
        switch(priceSort){
            case ASCENDING:
                Collections.sort(roomList, new ByPriceASCENDING());

            case DESCENDING:
                Collections.sort(roomList, new ByPriceDESCENDING());
        }
    }

    /**
     * setter for smoking option
     *
     * //@param smoking choice
     */
    void setSmokingType(smokingSortType type){
        switch (type){
            case ALL:
                smokeType = smokingSortType.ALL;

            case NON_SMOKING:
                smokeType = smokingSortType.NON_SMOKING;

            case SMOKING:
                smokeType = smokingSortType.SMOKING;
        }
    }

    /**
     * sorts rooms if a smoking preference is enabled
     *
     * //@param list of rooms to sort
     */
    void filterBySmokingType(List<Room> roomList){
        switch(smokeType){
            case NON_SMOKING:
                for(Room obj: roomList){
                    if(!obj.getSmokingAvailable()){
                        roomList.add(obj);
                    }
                }

            case SMOKING:
                for(Room obj: roomList){
                    if(obj.getSmokingAvailable()){
                        roomList.add(obj);
                    }
                }
        }
    }
}


/**
 * Comparator class to sort rooms
 *
 * This class sorts rooms by price in ascending order
 *
 * @author Chas Doughtry
 * @version 1.0
 */
class ByPriceASCENDING implements Comparator<Room> {
    public int compare (Room a, Room b){
        double epsilon = 0.000001d;

        if((a.getRoomPrice() - b.getRoomPrice()) < epsilon){
            return 1;
        }

        return 0;
    }
}

/**
 * Comparator class to sort rooms
 *
 * This class sorts rooms by price in descending order
 *
 * @author Chas Doughtry
 * @version 1.0
 */
class ByPriceDESCENDING implements Comparator<Room> {
    public int compare (Room a, Room b){
        double epsilon = 0.000001d;

        if((b.getRoomPrice() - a.getRoomPrice()) < epsilon){
            return 1;
        }

        return 0;
    }
}

