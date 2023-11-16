package edu.core.cruise;

import edu.core.reservation.Reservation;

import java.time.LocalDate;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country that = (Country) o;

        return (Objects.equals(name, that.name) &&
                Objects.equals(arrivalTime, that.arrivalTime)) &&
                Objects.equals(departureTime, that.departureTime);
    }

    /**
     * This function overrides the built-in hashing for an object
     *
     * @return The hashed value
     */
    @Override
    public int hashCode() {
        int result = 31; // A prime number as the initial value
        result = 31 * result + Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(arrivalTime);
        result = 31 * result + Objects.hashCode(departureTime);
        return result;
    }
}
