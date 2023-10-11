import java.util.HashSet;
import java.util.Set;

/**
 * Controls the wait-list attributed with each Room.
 *
 * @author William Delano
 * @version 1.0
 * @see Room
 */
public class Waitlist {
    private Set<User> waitlist_list;

    /**
     * This function is the constructor of the Wait list object. It initializes the waitlist_list.
     */
    public Waitlist() {
        waitlist_list = new HashSet<>();
    }

    /**
     * This function returns all the users of a wait-list for debugging
     *
     * @return The wait-list of the Room
     */
    public Set<User> waitList() {
        return waitlist_list;
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
     * return value: the name of the user
     */
    public void removeFromWaitlist(User user) {
        waitlist_list.remove(user);
    }
}
