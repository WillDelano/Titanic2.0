package com.core.uniqueID;

import com.core.users.User;

/**
 * Representation of an admin user in the cruise reservation system.
 *
 * This class provides specific functionalities and attributes related to an admin user responsible for managing system settings, travel agent accounts, etc.
 *
 * @author Vincent Dinh
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
