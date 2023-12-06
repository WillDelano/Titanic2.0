package edu.ui.guestSelectCruise;

import edu.core.cruise.Cruise;
import edu.databaseAccessors.CruiseDatabase;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller class for managing actions related to cruise selection UI.
 *
 * This class serves as an intermediary between the cruise UI and the backend, providing methods
 * to retrieve cruise names and details.
 *
 * @author Vincent Dinh
 * @version 1.0
 * @see Cruise
 * @see CruiseDatabase
 * @see SelectCruisePage
 */
public class SelectCruiseController {

    /**
     * Retrieves a list of cruise names.
     *
     * @return A list of cruise names.
     * @see CruiseDatabase#getAllCruises()
     */
    public static List<String> getCruiseNames() {
        return CruiseDatabase.getAllCruises().stream()
                .map(Cruise::getName)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves details for a specific cruise.
     *
     * @param cruiseName The name of the cruise for which details are requested.
     * @return The Cruise object containing details for the specified cruise.
     * @see CruiseDatabase#getCruise(String)
     */
    public static Cruise getCruiseDetails(String cruiseName) {
        return CruiseDatabase.getCruise(cruiseName);
    }
}