package com.core.cruise;

import com.core.uniqueID.UniqueID;

import java.util.Date;
import java.util.List;

/**
 * Cruise object that rooms are booked on
 *
 * <p>
 * Each Cruise object has a list of country objects that are given as its travel path
 * </p>
 *
 * @author William Delano
 * @version 1.0
 * @see Country
 */
public class Cruise {
    private int id;
    private String name;
    private Date departure;
    private List<Country> travelPath;
    private final int maxCapacity;
    private int currentCapacity;

    /**
     * This function creates a Cruise object with given values
     *
     * @param name The user to add to the wait-list
     * @param departure The date the cruise will depart
     * @param maxCapacity The maximum amount of passengers
     * @param path The in-order list of countries the cruise will travel to
     */
    public Cruise(String name, Date departure, int maxCapacity, List<Country> path) {
        this.id = new UniqueID().getId();
        this.travelPath = path;
        this.name = name;
        this.departure = departure;
        this.maxCapacity = maxCapacity;
        currentCapacity = 0;
    }
    /**
     * This function adds a passenger to the passenger count on a cruise
     *
     */
    public void addPassenger() {
        if (currentCapacity == maxCapacity) {
            throw new IllegalStateException("Cannot add more passengers. Cruise is fully booked.");
        }
        currentCapacity++;
    }

}
