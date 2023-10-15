package com.core;

public class Guest extends User {
    private int rewardPoints;

    public Guest(String username, String password, Long id, String firstName, String lastName,int rewardPoints) {
        super(username, password, id, firstName, lastName);
        this.rewardPoints = rewardPoints;
    }

    public void setRewardPoints(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    public int getRewardPoints() {
        return rewardPoints;
    }

    public void requestReservation(int cruiseID) {
        //TODO Implement method
    }
    public void requestModifyReservation(int reservationID) {
        //TODO Implement method
    }

    public void requestCancelReservation(int reservationID) {
        //TODO Implement method
    }

}