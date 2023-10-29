package edu.ui.cruiseDetails;

import edu.core.cruise.Cruise;
import edu.database.CruiseDatabase;
import edu.ui.authentication.RegisterPage;

import java.util.List;

/**
 * Controller for the cruise ui
 *
 * This class is an intermediary between the cruise ui and backend
 *
 * @author Vincent Dinh
 * @version 1.0
 * @see Cruise, CruiseDatabase, SelectCruisePage
 */
public class SelectCruiseController {
    // Fetches the names of all cruises in the database
    public static List<String> getCruiseNames() {
        return CruiseDatabase.getAllCruiseNames();
    }


    // Fetches detailed information of a cruise by its name
    public static Cruise getCruiseDetails(String cruiseName) {
        return CruiseDatabase.getCruise(cruiseName);
    }
}