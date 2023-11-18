package edu.databaseAccessors;
import edu.core.reservation.Reservation;
import edu.core.users.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.lang.*;


/**
 * Database to record valid accounts and information for accounts
 *
 *
 * This class documents a collection of unique usernames and another collection of unique passwords mapped with
 * user information.
 *
 * @author Michael Okonkwo
 * @version 1.1
 */
public class AccountDatabase {
    private static Set<User> accountDatabase;
    //private String fileName = getClass().getClassLoader().getResource("accountList.csv").getFile();
    private static String url = "jdbc:derby:C:\\Users\\vince\\IdeaProjects\\titanic2\\Titanic2.0\\Titanic-Project\\src\\main\\java\\edu\\Database";


    /**
     * Constructor for creating an instance of AccountDatabase
     *
     */
    public AccountDatabase() {
//        accountDatabase = new LinkedHashSet<>();
//        //the way the GUEST account will be put in file is String type, ...
//        //...String username,String password,int id, String firstName, String lastName,int rewardPoints, String email
//
//        // admin and agent are the same except no reward points
//        try {
//            BufferedReader reader = new BufferedReader(new FileReader(fileName));
//            String line;
//            line = reader.readLine();
//
//            while (line != null) {
//                String[] split = line.split(",");
//                if (split[0].equals("Guest")) {
//                    //guest has extra parameter for reward points
//
//                    //make guest instance then add to account list
//
//                    Guest guest = new Guest(split[1], split[2], Integer.parseInt(split[3]), split[4], split[5],
//                            Integer.parseInt(split[6]), split[7]);
//                    accountDatabase.add(guest);
//
//
//                } else {
//                    //normal parameters
//                    if (split[0].equals("Agent")) {
//                        //make agent instance based on file readings and add to list
//                        TravelAgent agent = new TravelAgent(split[1], split[2], Integer.parseInt(split[3]), split[4], split[5],
//                                split[6]);
//                        accountDatabase.add(agent);
//                    } else if (split[0].equals("Admin")) {
//                        //make admin instance based on file readings and add to list
//                        Admin admin = new Admin(split[1], split[2], Integer.parseInt(split[3]), split[4], split[5],
//                                split[6]);
//                        accountDatabase.add(admin);
//
//                    }
//                }
//                line = reader.readLine();
//            }
//
//            reader.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        //parse in everything into*/
    }

    /**
     * operation to get the count of users in the database
     *
     * @return amount of users in the database
     */
    public static int getUserCount() {
        int count = 0;
        try (Connection connection = DriverManager.getConnection(url)) {
            String selectCount = "SELECT COUNT(*) FROM Users";
            try (PreparedStatement statement = connection.prepareStatement(selectCount)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        count = resultSet.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * operation to validate login information from input
     *
     * @param username The username of the user.
     */
    public boolean isValidLogin(String username, String pass) {
        try (Connection connection = DriverManager.getConnection(url)) {
            String select = "SELECT * FROM Users WHERE username = ? AND password = ?";
            try (PreparedStatement statement = connection.prepareStatement(select)) {
                statement.setString(1, username);
                statement.setString(2, pass);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * Method to add users to the 'Users' Database
     */
    public static void addSampleUsers() {
        if (getUserCount() == 0) { // Check if there are already users in the database
            addUser("wdelano", "baylor", 0, "Will", "Delano", 0, "wdelano2002@gmail.com", "Guest");
            addUser("wdelano2", "baylor", 1, "Will", "Delano", 0, "wdelano2002@gmail.com", "Guest");
            addUser("wdelanoagent", "baylor", 2, "Will", "Delano", 0, "wdelano2002@gmail.com", "Agent");
        }
    }

    /**
     * Operation to register a new account within account database
     *
     * @param username The new user to add to account database
     */
    public static void addUser(String username, String password, int id, String firstName, String lastName, int rewardPoints, String email, String userType) {
        String insertSQL = "INSERT INTO Users (username, password, id, firstName, lastName, rewardPoints, email, type) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setInt(3, id);
            preparedStatement.setString(4, firstName);
            preparedStatement.setString(5, lastName);
            preparedStatement.setInt(6, rewardPoints);
            preparedStatement.setString(7, email);
            preparedStatement.setString(8, userType);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("User added successfully: " + username);
            } else {
                System.out.println("Failed to add user: " + username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    /**
     * operation to remove an account within the account database
     *
     * @param userToRemove The specified user to remove
     */

    /*public static void removeUserFile(User userToRemove) throws IOException {
        accountDatabase.remove(userToRemove);

        ArrayList<String> newFileLines = new ArrayList<>();

        String lineToCut = "Guest," + userToRemove.getUsername() + "," + userToRemove.getPassword() + "," + userToRemove.getId()
                + "," + userToRemove.getFirstName() + "," + userToRemove.getLastName() + "," + userToRemove.getRewardPoints() +
                "," + userToRemove.getEmail();


        String line;


        InputStream in = AccountDatabase.class.getClassLoader().getResourceAsStream("accountList.csv");
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        while ((line = reader.readLine()) != null) {
            newFileLines.add(line);
        }
        reader.close();

        //remove specified line
        for (String s : newFileLines) {
            if (s.contains(lineToCut)) {
                newFileLines.remove(s);
            }
        }

        //overrides old file
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        for (String newFileLine : newFileLines) {
            writer.write(newFileLine);
            writer.newLine();
        }

        writer.close();
        reader.close();
    }*/

    /**
     * Operation to get the type an account belongs to
     *
     * @param username A given username to find
     */

    public static String getAccountType(String username) {
        String accountType = "";
        try (Connection connection = DriverManager.getConnection(url)) {
            String query = "SELECT type FROM Users WHERE username = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        accountType = resultSet.getString("type");
                    } else {
                        System.err.println("No account found, returning empty account type.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accountType;
    }


    /**
     * Operation to validate the existence of a given username.
     *
     * @param username A given username to validate
     */
    public static boolean accountExists(String username) {
        try (Connection connection = DriverManager.getConnection(url)) {
            String query = "SELECT COUNT(*) FROM Users WHERE username = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt(1) > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Operation to modify an account username if valid.
     *
     * @param oldUser User's old username to change
     * @param newUser A given username to change into
     * @return the truth value of successfully modifying account
     */
    public boolean modifyUsername(String oldUser, String newUser) {
        try (Connection connection = DriverManager.getConnection(url)) {
            String update = "UPDATE Users SET username = ? WHERE username = ?";
            try (PreparedStatement statement = connection.prepareStatement(update)) {
                statement.setString(1, newUser);
                statement.setString(2, oldUser);
                int updated = statement.executeUpdate();
                return updated > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Operation to modify an account password.
     *
     * @param username User's username
     * @param newPass  A given password to change into
     */
    public static void modifyPassword(String username, String newPass) {
        try (Connection connection = DriverManager.getConnection(url)) {
            String update = "UPDATE Users SET password = ? WHERE username = ?";
            try (PreparedStatement statement = connection.prepareStatement(update)) {
                statement.setString(1, newPass);
                statement.setString(2, username);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    /**
     * Operation to modify an account first name.
     *
     * @param username User's username
     * @param newFName A given first name to change into
     */
    public void modifyFirstName(String username, String newFName) {
        try (Connection connection = DriverManager.getConnection(url)) {
            String update = "UPDATE Users SET firstName = ? WHERE username = ?";
            try (PreparedStatement statement = connection.prepareStatement(update)) {
                statement.setString(1, newFName);
                statement.setString(2, username);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    /**
     * Operation to modify an account last name.
     *
     * @param username User's username
     * @param newLName A given last name to change into
     */
    public void modifyLastName(String username, String newLName) {
        try (Connection connection = DriverManager.getConnection(url)) {
            String update = "UPDATE Users SET lastName = ? WHERE username = ?";
            try (PreparedStatement statement = connection.prepareStatement(update)) {
                statement.setString(1, newLName);
                statement.setString(2, username);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Operation to modify account changes on file database
     *
     * @param lineIndex line to alter
     * @param dataToChange specific index of information to change for specified user
     * @param newData   the newData to replace old data in file database
     *
     */
//    public static void modifyFileLine(int lineIndex, int dataToChange, String newData) throws IOException {
//        String line;
//        ArrayList<String> fileList = new ArrayList<>();
//
//        InputStream in = AccountDatabase.class.getClassLoader().getResourceAsStream("accountList.csv");
//        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//
//        //copy lines into arraylist
//        while ((line = reader.readLine()) != null) {
//            //find specified line to remove
//            fileList.add(line);
//
//        }
//        reader.close();
//
//
//        //modify specified line
//        String linetoModify = fileList.get(lineIndex);
//        //split line into pieces of data
//        String[]split = linetoModify.split(",");
//
//        //modify specific data you want
//        split[dataToChange] = newData;
//
//        //make a string builder to make new string
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i <= split.length; i++) {
//            sb.append(split[i]);
//        }
//
//        String lineToPut = sb.toString();
//
//        //put modified line back into ArrayList
//        fileList.set(lineIndex,lineToPut);
//
//        //overrides old file
//        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
//        for (String newFileLine : fileList) {
//            writer.write(newFileLine);
//            writer.newLine();
//        }
//        writer.close();
//    }

    /**
     * Operation to access specified user
     *
     * @param username the specified username of user to access
     *
     * @return the specified user
     */
    public static User getUser(String username){
        for(User u: accountDatabase){
            if(u.getUsername().equals(username)){
                return u;
            }
        }
        return null;
    }



    /**
     * Operation to update account details for user on SQL database
     *
     * @param account the specified account to update details
     * @param email the specified email details to change into
     * @param password the specified password details to change into
     *
     */
    public static void updateAccount(User account, String email, String password) {
        try (Connection connection = DriverManager.getConnection(url)) {
            String update = "UPDATE Users SET email = ?, password = ? WHERE username = ?";
            try (PreparedStatement statement = connection.prepareStatement(update)) {
                statement.setString(1, email);
                statement.setString(2, password);
                statement.setString(3, account.getUsername());
                int updated = statement.executeUpdate();
                if (updated <= 0) {
                    System.out.println("Failed to update data");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Operation to delete account from SQL database
     *
     * @param userToRemove the specified user to remove
     *
     */
    public static void removeUser(User userToRemove) {
        try (Connection connection = DriverManager.getConnection(url)) {
            String delete = "DELETE FROM Users WHERE username = ?";
            try (PreparedStatement statement = connection.prepareStatement(delete)) {
                statement.setString(1, userToRemove.getUsername());
                int deleted = statement.executeUpdate();
                if (deleted <= 0) {
                    System.out.println("Failed to delete data");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void shutdown() throws SQLException {
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException se) {
            if (!se.getSQLState().equals("XJ015")) {
                throw se;
            }
        }
    }

}
