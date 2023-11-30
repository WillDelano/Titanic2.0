package edu.core.cruise;
import edu.core.reservation.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class CruiseSearch {
    List<Cruise> allCruises;
    String preferredDestination, preferredDeparture;


    public CruiseSearch(List<Cruise> cruises){
        allCruises = cruises;
        preferredDestination = "";
        preferredDeparture = "";
    }
    public List<Cruise> findCruises(String line){
        String[] traits = line.split( " ");
        List<Cruise> relevantCruises = new ArrayList<>();
        Vector<String> countryNames = new Vector<>();

        // iterate through rooms and add relevant rooms to new list
        for(Cruise obj : allCruises){

            //check if capacity is full and departure hasnt passed
            //if(!obj.) {

            // iterate through traits to find in room's attributes
            for (String s : traits) {
                boolean added = false;
                if (obj.getName().toLowerCase().contains(s.toLowerCase())) { // cruise name
                    relevantCruises.add(obj);
                    break;
                }
                // check for country
                for(Country country : obj.getTravelPath()){
                    if(country.getName().toLowerCase().contains(s.toLowerCase())){
                        relevantCruises.add(obj);
                        added = true;
                        break;
                    }
                }
                // check for departure date

                if(added){
                    break;
                }
            }


            //}
        }
        relevantCruises = sortAndFilterRooms(relevantCruises);

        return relevantCruises;
    }

    public List<Cruise> sortAndFilterRooms (List<Cruise> cruises){
        List<Cruise> updatedCruises = new ArrayList<>(cruises);
        if(!preferredDestination.isEmpty()){
            updatedCruises = filterByDestination(updatedCruises);
        }
        if(!preferredDeparture.isEmpty()){
            updatedCruises = filterByDeparture(updatedCruises);
        }

        return updatedCruises;
    }

    public void setPreferredDestination(String name){
        preferredDestination = name;
    }

    public List<Cruise> filterByDestination(List<Cruise> cruiseList){

        List<Cruise> updatedCruiseList = new ArrayList<>();

        for(Cruise cruise : cruiseList){
            for(Country country : cruise.getTravelPath()){
                if(country.getName().equals(preferredDestination) && !updatedCruiseList.contains(cruise)){
                    updatedCruiseList.add(cruise);
                    break;
                }
            }
        }

        return updatedCruiseList;
    }
    public void setPreferredDeparture(String name){
        preferredDeparture = name;
    }

    public List<Cruise> filterByDeparture(List<Cruise> cruiseList){
        List<Cruise> updatedCruiseList = new ArrayList<>(cruiseList);

        updatedCruiseList.removeIf(cruise -> !cruise.getTravelPath().get(0).getName().equals(preferredDeparture));

        return updatedCruiseList;
    }


}
