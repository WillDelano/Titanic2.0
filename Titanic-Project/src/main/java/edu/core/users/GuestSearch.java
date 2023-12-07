package edu.core.users;

import java.util.ArrayList;
import java.util.List;

/**
 * Used to search for guests
 *
 * Includes functions that allow travel agents to search
 * for guests to edit reservation or other info.
 *
 * @author Chas Doughtry
 */
public class GuestSearch {
    List<Guest> allGuests;

    public GuestSearch(List<Guest> guests){
        allGuests = guests;
    }

    /**
     * findGuests
     *
     * function that searches through allGuests to
     * find the guest objects that meet the users
     * input.
     *
     * @param line - user input.
     */
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
        }

        return relevantGuests;
    }

}
