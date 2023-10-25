package edu.ui.cruiseDetails;

import edu.core.cruise.Cruise;
import edu.database.CruiseDatabase;

import java.util.List;

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