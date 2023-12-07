package edu.databaseAccessors;
import edu.core.reservation.Reservation;
import edu.core.users.*;
import edu.exceptions.NoMatchingClassException;
import edu.exceptions.UserNotFoundException;

import java.io.*;
import java.nio.file.Paths;
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
    private static final String url = DatabaseProperties.url;
    private static Set<User> accountDatabase;
    //private String fileName = getClass().getClassLoader().getResource("accountList.csv").getFile();

    static {
        accountDatabase = new HashSet<>();
        initializeDatabase(); // Static initialization of the database
    }

    /**
     * operation to initialize the database and populate the list of users associated with accountDatabase class
     *
     */
    private static void initializeDatabase() {
        try (Connection connection = DriverManager.getConnection(url)) {
            String query = "SELECT * FROM UsersV2";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        User user = createUserFromResultSet(resultSet);
                        if (user != null) {
                            accountDatabase.add(user);
                        }
                    }
                } catch (NoMatchingClassException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * operation to create a user object from a given result set
     *
     * @param resultSet set to create user from
     */
    private static User createUserFromResultSet(ResultSet resultSet) throws SQLException, NoMatchingClassException {
        // Extract data from resultSet
        String username = resultSet.getString("username");
        String password = resultSet.getString("password");
        int id = resultSet.getInt("id");
        String firstName = resultSet.getString("firstName");
        String lastName = resultSet.getString("lastName");
        String email = resultSet.getString("email");
        int rewardPoints = resultSet.getInt("rewardPoints"); // Assuming you have this column in your database
        String userType = resultSet.getString("type");
        System.out.println(userType);

        // Determine the type of user and instantiate accordingly
        switch (userType) {
            case "Guest":
                return new Guest(username, password, firstName, lastName, rewardPoints, email); // Now passing rewardPoints
            case "Agent":
                return new TravelAgent(username, password, firstName, lastName, email);
            case "Admin":
                return new Admin(username, password, firstName, lastName, email);
            default:
                throw new NoMatchingClassException("No class matches the user type!" + userType);
        }
    }

    /**
     * operation to get the count of users in the database
     *
     * @return amount of users in the database
     */
    public static int getUserCount() {
        int count = 0;
        try (Connection connection = DriverManager.getConnection(url)) {
            String selectCount = "SELECT COUNT(*) FROM UsersV2";
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
     * operation to get all users in the database
     *
     * @return amount of users in the database
     */
    public static List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();

        //create the connection to the db
        try (Connection connection = DriverManager.getConnection(url)) {
            //command to select all rows from db matching the guest id
            String selectAll = "SELECT * FROM UsersV2";
            //preparing the statement
            try (PreparedStatement statement = connection.prepareStatement(selectAll)) {
                //executing the statement (executeQuery returns a ResultSet)
                try (ResultSet resultSet = statement.executeQuery()) {
                    //get the values in the set and create reservations for them
                    while (resultSet.next()) {
                        User user = createUserFromResultSet(resultSet);

                        allUsers.add(user);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();

            System.err.println("Failed to connect to database.");
        }

        return allUsers;
    }

    /**
     * operation to validate login information from input
     *
     * @param username The username of the user.
     */
    public boolean isValidLogin(String username, String pass) {
        try (Connection connection = DriverManager.getConnection(url)) {
            String select = "SELECT * FROM UsersV2 WHERE username = ? AND password = ?";

            try (PreparedStatement statement = connection.prepareStatement(select)) {
                statement.setString(1, username);
                statement.setString(2, pass);
                try (ResultSet resultSet = statement.executeQuery()) {
                    boolean validLogin = resultSet.next();

                    // Log the result of the query
                    System.out.println("Account found in database: " + validLogin);
                    return validLogin;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Method to add users to the 'Users' Database
     */
    public static void addSampleUsers() {
        if (getUserCount() == 0) {
            addUser("wdelano", "baylor", "Will", "Delano", 0, "wdelano2002@gmail.com", "Guest");
            addUser("wdelanoagent", "baylor", "Will", "Delano", 0, "wdelano2002@gmail.com", "Agent");
            addUser("admin", "baylor", "Will", "Delano", 0, "wdelano2002@gmail.com", "Admin");
        }
    }

    /**
     * Operation to register a new account within account database
     *
     * @param username The new user to add to account database
     */
    public static void addUser(String username, String password, String firstName, String lastName, int rewardPoints, String email, String userType) {

        String insertSQL = "INSERT INTO UsersV2 (TYPE, USERNAME, PASSWORD, FIRSTNAME, LASTNAME, EMAIL, REWARDPOINTS) VALUES (?, ?, ?, ?, ?, ?, ?)";


        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setString(1, userType);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, firstName);
            preparedStatement.setString(5, lastName);
            preparedStatement.setString(6, email);
            preparedStatement.setInt(7, rewardPoints);

            if (Objects.equals(userType, "Guest")) {
                accountDatabase.add(new Guest(username, password , firstName, lastName, rewardPoints, email));
            }
            else if (Objects.equals(userType, "Agent")) {
                accountDatabase.add(new TravelAgent(username, password, firstName, lastName, email));
            }
            else {
                accountDatabase.add(new Admin(username, password, firstName, lastName, email));
            }

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
     * Operation to get the type an account belongs to
     *
     * @param username A given username to find
     */
    public static String getAccountType(String username) {

    String accountType = "";
        try (Connection connection = DriverManager.getConnection(url)) {
        String query = "SELECT type FROM UsersV2 WHERE username = ?";
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
            String query = "SELECT COUNT(*) FROM UsersV2 WHERE username = ?";
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
            String update = "UPDATE UsersV2 SET username = ? WHERE username = ?";
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
            String update = "UPDATE UsersV2 SET password = ? WHERE username = ?";
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
            String update = "UPDATE USERSV2 SET firstName = ? WHERE username = ?";
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
            String update = "UPDATE UsersV2 SET lastName = ? WHERE username = ?";
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
     * Operation to access specified user
     *
     * @param username the specified username of user to access
     *
     * @return the specified user
     */
    public static User getUser(String username) throws UserNotFoundException {
        for(User u: accountDatabase){
            if(u.getUsername().equals(username)){
                return u;
            }
        }
        throw new UserNotFoundException("No user with username " + username + " exists.");
    }

    /**
     * Operation to update account details for user on SQL database
     *
     * This function is called in two cases: The first case is when
     * a travel agent has had their account created by an admin and
     * now needs to complete the creation of their account themselves
     * by adding their name and email. The second case occurs when any
     * account type is trying to update their information. The two cases
     * are determined by whether a firstname is passed or not, because you
     * are not able to change your name in the second case.
     *
     * @param account the specified account to update details
     * @param email the specified email details to change to
     * @param password the specified password details to change to
     * @param firstName the first name to change to
     * @param lastName the last name to change to
     * @param username the username to change to
     *
     */
    public static void updateAccount(User account, String email, String password, String username, String firstName, String lastName) {
        try (Connection connection = DriverManager.getConnection(url)) {

            //if the updating is for a travel agent finishing their account creation
            if (!Objects.equals(firstName, "")) {
                String update = "UPDATE UsersV2 SET firstName = ?, lastName = ?, email = ? WHERE username = ? AND password = ?";
                try (PreparedStatement statement = connection.prepareStatement(update)) {
                    statement.setString(1, firstName);
                    statement.setString(2, lastName);
                    statement.setString(3, email);
                    statement.setString(4, account.getUsername());
                    statement.setString(5, account.getPassword());

                    int updated = statement.executeUpdate();
                    if (updated <= 0) {
                        System.err.println("Failed to update data");
                    }
                }
            } else {
                String update = "UPDATE UsersV2 SET email = ?, password = ? WHERE username = ?";
                try (PreparedStatement statement = connection.prepareStatement(update)) {
                    statement.setString(1, email);
                    statement.setString(2, password);
                    statement.setString(3, account.getUsername());

                    int updated = statement.executeUpdate();
                    if (updated <= 0) {
                        System.err.println("Failed to update data");
                    }
                }
            }
        } catch(SQLException e){
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

        //if the account to remove has potential reservations (is a guest)
        if (Objects.equals(AccountDatabase.getAccountType(userToRemove.getUsername()), "Guest")) {
            //get all the reservations to remove associated with the user
            Set<Reservation> resToRemove = ReservationDatabase.getReservations((Guest) userToRemove);

            //loop through all reservations and delete
            for (Reservation r : resToRemove) {
                ReservationDatabase.deleteReservation(r);
            }
        }

        try (Connection connection = DriverManager.getConnection(url)) {
            String delete = "DELETE FROM UsersV2 WHERE username = ?";
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

    /**
     * Operation to shut down the SQL database
     *
     */
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
