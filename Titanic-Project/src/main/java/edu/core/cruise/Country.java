package edu.core.cruise;

import java.util.Date;

/**
 * Describes the country that a cruise will travel to
 *
 * <p>
 * Each country object will have a name, cruise arrival time, and cruise departure time
 * </p>
 *
 * @author William Delano
 * @version 1.0
 * @see Cruise
 */
public class Country {
    private String name;
    private Date arrivalTime;
    private Date departureTime;

    /**
     * This function creates a Cruise object with given values
     *
     * @param name The name of the country
     * @param arrivalTime The date the cruise will arrive
     * @param departureTime The date the cruise will depart
     */
    public Country(String name, Date arrivalTime, Date departureTime) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
    }
}
