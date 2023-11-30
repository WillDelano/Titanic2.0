package edu.core.cruise;
import edu.core.reservation.Room;

import java.util.ArrayList;
import java.util.List;
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

        // iterate through rooms and add relevant rooms to new list
        for(Cruise obj : allCruises){

            //check if capacity is full and departure hasnt passed
            //if(!obj.) {

            // iterate through traits to find in room's attributes
            for (String s : traits) {
                if (obj.getName().toLowerCase().contains(s.toLowerCase())) { // cruise name
                    relevantCruises.add(obj);
                    break;
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
        List<Cruise> updatedCruises = new ArrayList<>(cruises);
        if(!preferredDestination.equals("")){
            updatedCruises = filterByDestination(updatedCruises);
        }
        if(!preferredDeparture.equals("")){
            updatedCruises = filterByDeparture(updatedCruises);
        }

        return updatedCruises;
    }

    public void setPreferredDestination(String name){
        preferredDeparture = name;
    }

    public List<Cruise> filterByDestination(List<Cruise> cruiseList){
        List<Cruise> updatedCruiseList = new ArrayList<Cruise>(cruiseList);

        for(Cruise cruise : updatedCruiseList){
            boolean toKeep = false;
            for(Country country : cruise.getTravelPath()){
                if(country.getName().equals(preferredDestination) ){//&& !updatedCruiseList.contains(cruise)){
                    //updatedCruiseList.add(cruise);
                    toKeep = true;
                    break;
                }
                if(!toKeep){
                    updatedCruiseList.remove(cruise);
                }
            }
        }

        return updatedCruiseList;
    }
    public void setPreferredDeparture(String name){
        preferredDeparture = name;
    }

    public List<Cruise> filterByDeparture(List<Cruise> cruiseList){
        List<Cruise> updatedCruiseList = new ArrayList<Cruise>(cruiseList);

        updatedCruiseList.removeIf(cruise -> !cruise.getTravelPath().get(0).getName().equals(preferredDeparture));

        /*
        for(Cruise cruise : cruiseList){
            if(cruise.getTravelPath().get(0).getName().equals(preferredDeparture)){
                updatedCruiseList.add(cruise);
            }
        }*/

        return updatedCruiseList;
    }


}
