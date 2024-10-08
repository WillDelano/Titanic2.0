package edu.core.reservation;

import edu.core.users.User;

import java.util.HashSet;
import java.util.Set;

/**
 * Controls the wait-list attributed with each Room.
 *
 * <p>
 * Each Room object should have a wait-list attribute to hold all the Users waiting for the room.
 * </p>
 *
 * @author William Delano
 * @version 1.0
 * @see Room
 */
public class Waitlist {
    private Set<User> waitlist_list;

    /**
     * This function is the constructor of the Wait list object. It initializes the list.
     */
    public Waitlist() {
        waitlist_list = new HashSet<>();
    }

    /**
     * This function returns the size of the wait-list
     *
     * @return The size of the wait-list
     */
    public int getSize() {
        return waitlist_list.size();
    }

    /**
     * This function adds a User to a Room object's wait-list.
     *
     * @param user The user to add to the wait-list.
     */
    public void joinWaitlist(User user) {
        waitlist_list.add(user);
    }

    /**
     * This function removes a user from the wait-list
     *
     * @param user The user to remove from the wait-list
     */
    public void removeFromWaitlist(User user) {
        waitlist_list.remove(user);
    }
}
