package edu.databaseAccessors;

import java.nio.file.Path;
import java.nio.file.Paths;

public class DatabaseProperties {
    private static Path relativePath = Paths.get("src", "main", "java", "edu", "Database");
    public static final String url = "jdbc:derby:" + relativePath.toAbsolutePath().toString();
}
