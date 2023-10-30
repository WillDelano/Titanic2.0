package edu.core.reservation;

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
    ArrayList<Room> allRooms;
    boolean price;
    enum priceSortType {NONE, ASCENDING, DESCENDING};
    enum bedCountSortType {NONE, ASCENDING, DESCENDING};
    //enum bedSortType {  };
    enum smokingSortType {ALL, NON_SMOKING, SMOKING};
    priceSortType priceSort = priceSortType.NONE;
    smokingSortType smokeType = smokingSortType.ALL;

    enum roomSortType{/* add room types here */};

    /**
     * RoomSearch Constructor
     *
     * //@param database of cruise objects
     */

    /*roomSearch(CruiseDatabase cruises){
        // foreach cruise,c, in cruises
        // for each room in c
        // add c to allRooms

        allRooms = cruises.getRoomListfromCSV()


    }*/
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

        return relevantRooms;
    }

    ArrayList<Room> sortAndFilterRooms (){
        ArrayList<Room> list = allRooms;

        // filter rooms
        if(smokeType != smokingSortType.ALL) {
            filterBySmokingType(list);
        }

        // sort rooms
        if(priceSort != priceSortType.NONE){
            sortRoomsByPrice(list);
        }


        return allRooms;
    }

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

    void sortRoomsByPrice(ArrayList<Room> roomList){
        switch(priceSort){
            case ASCENDING:
                Collections.sort(roomList, new ByPriceASCENDING());

            case DESCENDING:
                Collections.sort(roomList, new ByPriceDESCENDING());
        }
    }

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

    void filterBySmokingType(ArrayList<Room> roomList){
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
class ByPriceASCENDING implements Comparator<Room> {
    public int compare (Room a, Room b){
        double epsilon = 0.000001d;

        if((a.getRoomPrice() - b.getRoomPrice()) < epsilon){
            return 1;
        }

        return 0;
    }
}
class ByPriceDESCENDING implements Comparator<Room> {
    public int compare (Room a, Room b){
        double epsilon = 0.000001d;

        if((b.getRoomPrice() - a.getRoomPrice()) < epsilon){
            return 1;
        }

        return 0;
    }
}

