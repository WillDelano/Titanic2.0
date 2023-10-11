import java.util.HashSet;
import java.util.Set;

/* Each room object should have a waitlist attribute that tracks the people on the waitlist */
public class Waitlist {
    private Set<User> waitlist_list;

    public Waitlist() {
        waitlist_list = new HashSet<>();
    }
    public void joinWaitlist(User user) {
        waitlist_list.add(user);
    }

    public void removeFromWaitlist(User user) {
        waitlist_list.remove(user);
    }
}
