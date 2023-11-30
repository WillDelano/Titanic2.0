package edu.core.cruise;
import java.util.ArrayList;
import java.util.List;
public class CruiseSearch {
    List<Cruise> allCruises;


    public CruiseSearch(List<Cruise> cruises){
        allCruises = cruises;
    }
    public List<Cruise> findCruises(String line){
        String[] traits = line.split( " ");
        List<Cruise> relevantCruises = new ArrayList<>();

        // iterate through rooms and add relevant rooms to new list
        for(Cruise obj : allCruises){

            //check if capacity is full and departure hasnt passed
            //if(!obj.) {

            // iterate through traits to find in room's attributes
            for (String s : traits) {
                if (obj.getName().toLowerCase().contains(s.toLowerCase())) { // cruise name
                    relevantCruises.add(obj);
                }
                // check for country
                // check for departure date
            }
            //}
        }
        relevantCruises = sortAndFilterRooms(relevantCruises);

        return relevantCruises;
    }

    public List<Cruise> sortAndFilterRooms (List<Cruise> cruises){
        return cruises;
    }

}
