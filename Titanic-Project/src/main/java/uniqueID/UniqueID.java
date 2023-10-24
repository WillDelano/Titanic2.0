package uniqueID;

import edu.core.users.User;

/**
 * Creates a unqiue id
 *
 * This class provides a unique id for any instance
 *
 * @author William Delano
 * @version 1.0
 * @see User
 */
public class UniqueID {
    private static int uniqueNumber = 0;
    private int id;

    /**
     * Constructor for creating a new Guest.
     *
     */
    public UniqueID() {
        id = uniqueNumber;
    }

    /**
     * Constructor for creating a new Guest.
     *
     * @return The unique id
     */
    public int getId() {
        return id;
    }
}
