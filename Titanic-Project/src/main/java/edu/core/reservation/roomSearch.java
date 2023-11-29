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
    enum bedPreferenceType {ALL, SINGLE, TWIN, FULL, QUEEN, KING}
    enum bedCountType {ALL, ONE, TWO, THREE, FOUR}

    enum smokingSortType {ALL, NON_SMOKING, SMOKING}
    priceSortType priceSort = priceSortType.NONE;
    smokingSortType smokeType = smokingSortType.ALL;
    bedPreferenceType bedType = bedPreferenceType.ALL;
    bedCountType bedCount = bedCountType.ALL;

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
                    if (s.toLowerCase().contains(obj.getBedTypeStr().toLowerCase())) { //bed type
                        relevantRooms.add(obj);
                    } else if (s.toLowerCase().contains( // searching for beds
                            (obj.getNumberOfBeds() + " beds").toLowerCase())) {
                        relevantRooms.add(obj);
                    } else if (s.contains(String.valueOf(obj.getRoomNumber()))) {
                        relevantRooms.add(obj);
                    } else if (s.contains(String.valueOf(obj.getRoomPrice()))) {
                        relevantRooms.add(obj);
                    }
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

        // filter by bed type
        if(bedType != bedPreferenceType.ALL){
            filterByBedType(sortedRooms);
        }

        // filter by bed Count
        if(bedCount != bedCountType.ALL){
            filterByBedCount(sortedRooms);
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

    /**
     * setter for bed type preference option
     *
     * //@param bed preference
     */
    void setBedType(bedPreferenceType type){
        switch (type){
            case ALL:
                bedType = bedPreferenceType.ALL;

            case SINGLE:
                bedType = bedPreferenceType.SINGLE;

            case TWIN:
                bedType = bedPreferenceType.TWIN;

            case FULL:
                bedType = bedPreferenceType.FULL;

            case QUEEN:
                bedType = bedPreferenceType.QUEEN;

            case KING:
                bedType = bedPreferenceType.KING;
        }
    }

    /**
     * sorts rooms if a bed type preference is enabled
     *
     * //@param list of rooms to sort
     */
    void filterByBedType(List<Room> roomList){

        switch(bedType){
            case SINGLE:
                for(Room obj: roomList){
                    if(obj.getBedTypeStr().equalsIgnoreCase("single")){
                        roomList.add(obj);
                    }
                }

            case TWIN:
                for(Room obj: roomList){
                    if(obj.getBedTypeStr().equalsIgnoreCase("twin")){
                        roomList.add(obj);
                    }
                }

            case FULL:
                for(Room obj: roomList){
                    if(obj.getBedTypeStr().equalsIgnoreCase("full")){
                        roomList.add(obj);
                    }
                }

            case QUEEN:
                for(Room obj: roomList){
                    if(obj.getBedTypeStr().equalsIgnoreCase("queen")){
                        roomList.add(obj);
                    }
                }

            case KING:
                for(Room obj: roomList){
                    if(obj.getBedTypeStr().equalsIgnoreCase("king")){
                        roomList.add(obj);
                    }
                }
        }
    }

    /**
     * setter for bed count preference option
     *
     * //@param bedtype choice
     */
    void setBedCount(bedCountType type){
        switch (type){
            case ALL:
                bedCount = bedCountType.ALL;

            case ONE:
                bedCount = bedCountType.ONE;

            case TWO:
                bedCount = bedCountType.TWO;

            case THREE:
                bedCount = bedCountType.THREE;

            case FOUR:
                bedCount = bedCountType.FOUR;
        }
    }

    /**
     * sorts rooms if a bed count preference is enabled
     *
     * //@param list of rooms to sort
     */
    void filterByBedCount(List<Room> roomList){
        int preferredBedCount = 0;

        switch(bedCount){
            case ONE:
                preferredBedCount = 1;

            case TWO:
                preferredBedCount = 2;

            case THREE:
                preferredBedCount = 3;

            case FOUR:
                preferredBedCount = 4;
        }

        for(Room obj: roomList){
            if(obj.getNumberOfBeds() == preferredBedCount){
                roomList.add(obj);
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

