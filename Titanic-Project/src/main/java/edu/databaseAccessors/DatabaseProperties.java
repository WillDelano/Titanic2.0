package edu.databaseAccessors;

import java.nio.file.Paths;

/**
 * This class allows the database path to not be hardcoded
 *
 * Using the user's directory, the relative path is added onto it and set to a string to be used for all database accessors
 *
 * @author William Delano
 * @version 1.0
 */
public class DatabaseProperties {
    private static String relativePath = "src/main/java/edu/Database";
    public static final String url = "jdbc:derby:" + Paths.get(System.getProperty("user.dir"), relativePath).toString();
}
