package edu.core.users;

import java.util.ArrayList;
import java.util.List;

public class GuestSearch {
    List<Guest> allGuests;

    public GuestSearch(List<Guest> guests){
        allGuests = guests;
    }

    public List<Guest> findGuests(String line){
        List<Guest> relevantGuests = new ArrayList<>();
        String[] input = line.split( " ");
        String currName;

        if(line.replaceAll(" ", "").isEmpty()){
            return allGuests;
        }

        for(Guest guest : allGuests){

            for(String s : input){
                if(guest.getUsername().equalsIgnoreCase(s)){
                    relevantGuests.add(guest);

                }else if(guest.getFirstName().equalsIgnoreCase(s)){
                    relevantGuests.add(guest);

                }else if(guest.getLastName().equalsIgnoreCase(s)){
                    relevantGuests.add(guest);
                }
            }

            /*
            currName = guest.getFirstName() + " " + guest.getLastName() +
                    " " + guest.getUsername();
            if(currName.toLowerCase().contains(line.toLowerCase())){
                relevantGuests.add(guest);
            }*/
        }

        return relevantGuests;
    }

}
