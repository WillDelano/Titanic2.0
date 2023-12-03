package edu.ui.guestSelectCruise;

import edu.core.cruise.Cruise;
import edu.databaseAccessors.CruiseDatabase;

import java.util.List;
import java.util.stream.Collectors;

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
    public static List<String> getCruiseNames() {
        return CruiseDatabase.getAllCruises().stream()
                .map(Cruise::getName)
                .collect(Collectors.toList());
    }

    public static Cruise getCruiseDetails(String cruiseName) {
        return CruiseDatabase.getCruise(cruiseName);
    }
}