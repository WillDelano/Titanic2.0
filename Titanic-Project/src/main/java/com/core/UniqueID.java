package com.core;

public class UniqueID {
    private static int id = 0;

    public int getUniqueID() {
        return id++;
    }
}
