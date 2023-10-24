package edu.core.users;

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
