package edu.core.uniqueID;

import edu.core.reservation.Room;

/**
 * Creates a unique id
 *
 * <p>
 * Used by any class needing an id
 * </p>
 *
 * @author William Delano
 * @version 1.0
 * @see Room
 */
public class UniqueID {
    private static int uniqueNumber = 0;
    private int id;

    /**
     * Sets a unique number id
     *
     */
    public UniqueID() {
        id = uniqueNumber++;
    }

    /**
     * Gets a unique id
     *
     * @return The unique id
     */
    public int getId() {
        return id;
    }
}
