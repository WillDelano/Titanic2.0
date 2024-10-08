package edu.core.cruise;

import edu.core.reservation.Room;


import java.time.LocalDate;
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
    private LocalDate departure;
    private List<Country> travelPath;
    private List<Room> roomList;
    private final int maxCapacity;
    private int currentOccupancy;

    /**
     * This function creates a Cruise object with given values
     *
     * @param name        The user to add to the wait-list
     * @param departure   The date the cruise will depart
     * @param maxCapacity The maximum amount of passengers
     * @param path        The in-order list of countries the cruise will travel to
     * @param roomList
     */
    public Cruise(String name, LocalDate departure, int maxCapacity, List<Country> path, List<Room> roomList) {
        this.travelPath = path;
        this.name = name;
        this.departure = departure;
        this.maxCapacity = maxCapacity;
        this.roomList = roomList;
        currentOccupancy = 0;
    }
    /**
     * This function adds a passenger to the passenger count on a cruise
     *
     */
    public void addPassenger() {
        if (currentOccupancy >= maxCapacity) {
            throw new IllegalStateException("Cannot add more passengers. Cruise is fully booked.");
        }
        currentOccupancy++;
    }

    /**
     * This function removes a passenger from the passenger count on a cruise
     *
     */
    public void removePassenger() {
        if (currentOccupancy <= 0) {
            throw new IllegalStateException("Cannot remove more passengers. Cruise is empty.");
        }
        currentOccupancy--;
    }

    /**
     * This function returns the list of rooms on a cruise
     *
     * @return The list of rooms on a cruise
     */
    public List<Room> getRoomList() { return roomList; }

    /**
     * This function gets the name of a cruise
     *
     * @return The name of a cruise
     */
    public String getName() { return name; }

    /**
     * This function gets the max capacity of a cruise
     *
     * @return The max capacity of a cruise
     */
    public int getMaxCapacity() { return maxCapacity; }

    /**
     * This function gets the current capacity of a cruise
     *
     * @return The current capacity of a cruise
     */
    public int getCurrentOccupancy() { return currentOccupancy; }

    /**
     * This function returns the departure Date of a cruise
     *
     * @return The departure of a cruise
     */
    public LocalDate getDeparture() { return departure; }

    /**
     * This function returns the travel path of a cruise
     *
     * @return The travel path of a cruise
     */
    public List<Country> getTravelPath() { return travelPath; }
}
