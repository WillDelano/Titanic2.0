package edu.core.uniqueID;

public class UniqueID {
    private static int uniqueNumber = 0;
    private int id;

    public UniqueID() {
        id = uniqueNumber;
    }

    public int getId() {
        return id;
    }
}
