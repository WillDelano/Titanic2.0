package edu.core.cruise;

import java.time.LocalDate;

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
    private LocalDate arrivalTime;
    private LocalDate departureTime;

    /**
     * This function creates a Cruise object with given values
     *
     * @param name The name of the country
     * @param arrivalTime The date the cruise will arrive
     * @param departureTime The date the cruise will depart
     */
    public Country(String name, LocalDate arrivalTime, LocalDate departureTime) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
    }

    /**
     * This function returns the name of a country
     *
     * @return The name of a country
     */
    public String getName() { return name; }

    /**
     * This function returns the date a cruise will arrive
     *
     * @return The date the cruise arrives to the country
     */
    public LocalDate getArrivalTime() { return arrivalTime; }

    /**
     * This function returns the date a cruise will depart
     *
     * @return The date the cruise departs from the country
     */
    public LocalDate getDepartureTime() { return departureTime; }
}
