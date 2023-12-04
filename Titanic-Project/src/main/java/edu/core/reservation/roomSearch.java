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
    public enum priceSortType {NONE, ASCENDING, DESCENDING}
    enum bedPreferenceType {ALL, SINGLE, TWIN, FULL, QUEEN, KING}
    public enum bedCountType {ALL, ONE, TWO, THREE, FOUR}

    public enum smokingSortType {ALL, NON_SMOKING, SMOKING}
    priceSortType priceSort;
    public smokingSortType smokeType;
    bedPreferenceType bedType;
    bedCountType bedCount;

    enum roomSortType{/* add room types here */}

    /**
     * RoomSearch Constructor
     *
     * @param cruise - cruise object to retrieve rooms from.
     */

    public roomSearch(Cruise cruise){
        allRooms = cruise.getRoomList();
        priceSort = priceSortType.NONE;
        smokeType = smokingSortType.ALL;
        bedType = bedPreferenceType.ALL;
        bedCount = bedCountType.ALL;
    }

    /**
     * RoomSearch Constructor
     *
     * @param rooms - list to retrieve rooms from.
     */
    public roomSearch(List<Room> rooms){
        allRooms = rooms;
        priceSort = priceSortType.NONE;
        smokeType = smokingSortType.ALL;
        bedType = bedPreferenceType.ALL;
        bedCount = bedCountType.ALL;
    }

    public List<Room> findRooms(String line){
        String[] traits = line.split( " ");
        List<Room> relevantRooms = new ArrayList<>();
        boolean checkSmoking = false, smoking = true;

        if(line.toLowerCase().contains("smoking")) {
            checkSmoking = true;
            if (line.toLowerCase().contains("no")) { // checks for "no" in non smoking || no smoking || non-smoking
                smoking = false;
            }
        }

        // iterate through rooms and add relevant rooms to new list
        for(Room obj : allRooms){

            if(!obj.isBooked()) {

                // iterate through traits to find in room's attributes
                for (String s : traits) {
                    if (obj.getBedTypeStr().toLowerCase().contains(s.toLowerCase())) { //bed type
                        relevantRooms.add(obj);
                    } else if (s.toLowerCase().equals( // searching for beds
                            (Integer.toString( obj.getNumberOfBeds())))) {
                        relevantRooms.add(obj);
                    } else if (s.equals(String.valueOf(obj.getRoomNumber()))) {
                        relevantRooms.add(obj);
                    } else if (s.contains(String.valueOf((int)obj.getRoomPrice()))) {
                        relevantRooms.add(obj);
                    }
                }

                if(checkSmoking) {
                    if (!obj.getSmokingAvailable() && !smoking) {
                        relevantRooms.add(obj);
                    } else if (obj.getSmokingAvailable() && smoking) {
                        relevantRooms.add(obj);
                    }
                }
            }
        }
        relevantRooms = sortAndFilterRooms(relevantRooms);

        return relevantRooms;
    }

    /**
     * filters allRooms by the enabled filters and sorting type
     *
     * @param rooms list of rooms to be sorted
     * @return filtered and sorted list of rooms
     */
    public List<Room> sortAndFilterRooms (List<Room> rooms){
        List<Room> sortedRooms = new ArrayList<Room>(rooms);

        // filter rooms
        if(smokeType != smokingSortType.ALL) {
            sortedRooms = filterBySmokingType(sortedRooms);
        }

        // filter by bed type
        if(bedType != bedPreferenceType.ALL){
            filterByBedType(sortedRooms);
        }

        // filter by bed Count
        if(bedCount != bedCountType.ALL){
            sortedRooms = filterByBedCount(sortedRooms);
        }

        // sort rooms
        if(priceSort != priceSortType.NONE){
            sortedRooms = sortRoomsByPrice(sortedRooms);
        }

        return sortedRooms;
    }

    /**
     * setter for type of price sorting
     *
     * //@param type of price sorting
     */
    public void setPriceSorting(priceSortType type){
        switch (type){
            case NONE:
                priceSort = priceSortType.NONE;
                break;
            case ASCENDING:
                priceSort = priceSortType.ASCENDING;
                break;
            case DESCENDING:
                priceSort = priceSortType.DESCENDING;
                break;
        }
    }

    /**
     * sorts rooms by price
     *
     * //@param list of rooms to sort
     */
    List<Room> sortRoomsByPrice(List<Room> roomList){
        Comparator<Room> myComparator = Comparator.comparingDouble(Room::getRoomPrice);
        switch(priceSort){
            case ASCENDING:
                myComparator = Comparator.comparingDouble(Room::getRoomPrice);
                break;
            case DESCENDING:
                myComparator = Comparator.comparingDouble(Room::getRoomPrice).reversed();
                break;
        }
        Collections.sort(roomList, myComparator);
        return roomList;
    }

    /**
     * setter for smoking option
     *
     * //@param smoking choice
     */
    public void setSmokingType(smokingSortType type){
        switch (type){
            case ALL:
                smokeType = smokingSortType.ALL;
                break;
            case NON_SMOKING:
                smokeType = smokingSortType.NON_SMOKING;
                break;
            case SMOKING:
                smokeType = smokingSortType.SMOKING;
                break;
        }
    }

    /**
     * sorts rooms if a smoking preference is enabled
     *
     * //@param list of rooms to sort
     */
    public List<Room> filterBySmokingType(List<Room> roomList){
        List<Room> ogRooms = new ArrayList<Room>(roomList);
        switch(smokeType){
            case NON_SMOKING:
                ogRooms.removeIf(obj -> obj.getSmokingAvailable());
                break;

            case SMOKING:
                ogRooms.removeIf(obj -> !obj.getSmokingAvailable());
                break;
        }
        return ogRooms;
    }

    /**
     * setter for bed type preference option
     *
     * //@param bed preference
     */
    public void setBedType(bedPreferenceType type){
        switch (type){
            case ALL:
                bedType = bedPreferenceType.ALL;
                break;
            case SINGLE:
                bedType = bedPreferenceType.SINGLE;
                break;
            case TWIN:
                bedType = bedPreferenceType.TWIN;
                break;
            case FULL:
                bedType = bedPreferenceType.FULL;
                break;
            case QUEEN:
                bedType = bedPreferenceType.QUEEN;
                break;
            case KING:
                bedType = bedPreferenceType.KING;
                break;
        }
    }
    public String getSortType(){
        String smokingType = "";

        if(priceSort == priceSortType.ASCENDING){
            smokingType = "ascending";
        }else if (priceSort == priceSortType.DESCENDING){
            smokingType = "descending";
        } else if (priceSort == priceSortType.NONE){
            smokingType = "None";
        }

        return smokingType;
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
                break;
            case TWIN:
                for(Room obj: roomList){
                    if(obj.getBedTypeStr().equalsIgnoreCase("twin")){
                        roomList.add(obj);
                    }
                }
                break;
            case FULL:
                for(Room obj: roomList){
                    if(obj.getBedTypeStr().equalsIgnoreCase("full")){
                        roomList.add(obj);
                    }
                }
                break;
            case QUEEN:
                for(Room obj: roomList){
                    if(obj.getBedTypeStr().equalsIgnoreCase("queen")){
                        roomList.add(obj);
                    }
                }
                break;
            case KING:
                for(Room obj: roomList){
                    if(obj.getBedTypeStr().equalsIgnoreCase("king")){
                        roomList.add(obj);
                    }
                }
                break;
        }
    }

    /**
     * setter for bed count preference option
     *
     * //@param bedtype choice
     */
    public void setBedCount(bedCountType type){
        switch (type){
            case ALL:
                bedCount = bedCountType.ALL;
                break;
            case ONE:
                bedCount = bedCountType.ONE;
                break;
            case TWO:
                bedCount = bedCountType.TWO;
                break;
            case THREE:
                bedCount = bedCountType.THREE;
                break;
            case FOUR:
                bedCount = bedCountType.FOUR;
                break;
        }
    }

    /**
     * sorts rooms if a bed count preference is enabled
     *
     * //@param list of rooms to sort
     */
    List<Room> filterByBedCount(List<Room> roomList){
        int preferredBedCount;

        switch(bedCount){
            case ONE:
                preferredBedCount = 1;
                break;
            case TWO:
                preferredBedCount = 2;
                break;
            case THREE:
                preferredBedCount = 3;
                break;
            case FOUR:
                preferredBedCount = 4;
                break;

            default:
                preferredBedCount = 0;
        }
        roomList.removeIf(obj -> obj.getNumberOfBeds() != preferredBedCount);

        return roomList;
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
