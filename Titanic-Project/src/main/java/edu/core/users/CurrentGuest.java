package edu.core.users;

/**
 * Class for holding the currently logged in guest
 *
 * <p>
 * Contains the guest that is currently logged in
 * </p>
 *
 * @author William Delano
 * @version 1.0
 * @see Guest
 */
public class CurrentGuest {
    private static Guest guest;

    /**
     * This function creates a new logged in guest
     *
     * @param guest guest to set
     */
    public CurrentGuest(Guest guest) {
        CurrentGuest.guest = guest;
    }

    /**
     * This function sets current guest
     *
     * @param guest guest to set
     */
    public static void setCurrentGuest(Guest guest) {
        CurrentGuest.guest = guest;
    }

    /**
     * This function gets the logged in guest
     *
     * @return the currently logged in guest
     */
    public static Guest getCurrentGuest() {
       if (guest == null) {
           System.err.println("Null Guest logged in.");
       }
        return guest;
    }

    /**
     * This function logs out the current guest
     *
     */
    public static void logoutCurrentGuest() {
        guest = null;
    }
}
