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
    
    public CurrentGuest(Guest guest) {
        CurrentGuest.guest = guest;
    }
    public static void setCurrentGuest(Guest guest) {
        CurrentGuest.guest = guest;
    }
    
    public static Guest getCurrentGuest() {
       if (guest == null) {
           System.err.println("Null Guest logged in.");
       }
        return guest;
    }
    
    public static void logoutCurrentGuest() {
        guest = null;
    }
}
