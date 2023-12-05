package edu.databaseAccessors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//public interface driver {

//    public static Connection getDBConnection() {
//        String DB_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
//        String url = "jdbc:derby:C:\\Users\\Colet\\Documents\\GIT\\Titanic2.0\\Titanic-Project\\src\\main\\java\\edu\\database";
//        Connection dbConnection = null;
//        //Class.forName(DB_DRIVER);
//        try {
//            dbConnection = DriverManager.getConnection(url);
//            return dbConnection;
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        return dbConnection;
//    }
//}
