package edu.databaseAccessors;

import java.nio.file.Paths;

public class DatabaseProperties {
    private static String relativePath = "src/main/java/edu/Database";
    public static final String url = "jdbc:derby:" + Paths.get(System.getProperty("user.dir"), relativePath).toString();
}
