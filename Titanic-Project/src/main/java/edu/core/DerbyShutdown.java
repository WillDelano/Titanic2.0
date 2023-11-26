package edu.core;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DerbyShutdown {
    public static void main(String[] args) {
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException se) {
            if (!se.getSQLState().equals("XJ015")) {
                se.printStackTrace();
            }
        }
        System.out.println("Derby shutdown completed.");
    }
}
