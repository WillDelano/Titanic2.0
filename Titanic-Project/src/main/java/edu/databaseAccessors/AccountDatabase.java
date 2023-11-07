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
    private static String fileName = "C:\\Users\\vince\\Java Projects\\Titanic2.0\\Titanic-Project\\src\\main\\resources\\accountList.csv";


    /**
     * Constructor for creating an instance of AccountDatabase
     *
     */
    public AccountDatabase() {
        accountDatabase = new LinkedHashSet<>();
        //the way the GUEST account will be put in file is String type, ...
        //...String username,String password,int id, String firstName, String lastName,int rewardPoints, String email

        // admin and agent are the same except no reward points
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            line = reader.readLine();

            while (line != null) {
                String[] split = line.split(",");
                if (split[0].equals("Guest")) {
                    //guest has extra parameter for reward points

                    //make guest instance then add to account list

                    Guest guest = new Guest(split[1], split[2], Integer.parseInt(split[3]), split[4], split[5],
                            Integer.parseInt(split[6]), split[7]);
                    accountDatabase.add(guest);


                } else {
                    //normal parameters
                    if (split[0].equals("Agent")) {
                        //make agent instance based on file readings and add to list
                        TravelAgent agent = new TravelAgent(split[1], split[2], Integer.parseInt(split[3]), split[4], split[5],
                                split[6]);
                        accountDatabase.add(agent);
                    } else if (split[0].equals("Admin")) {
                        //make admin instance based on file readings and add to list
                        Admin admin = new Admin(split[1], split[2], Integer.parseInt(split[3]), split[4], split[5],
                                split[6]);
                        accountDatabase.add(admin);

                    }
                }
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //parse in everything into*/
    }

    /**
     * operation to get the count of users in the database
     *
     * @return amount of users in the database
     */
    public static int getUserCount() {
        int count = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            line = reader.readLine();

            while (line != null) {
                String[] split = line.split(",");

                //if the username is found, return the account type
                if (split[0].equals("Guest")) {
                    count++;
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
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
        boolean isValid = false;
        for (User user : accountDatabase) {
            if (user.getUsername().equals(username)) {
                if (user.getPassword().equals(pass)) {
                    isValid = true;
                }
            }
        }

        return isValid;
    }

    //FIXME: Will have to use "User" type for parameter. Will be used for when admin wants to create a travel agent account
    /**
     * Operation to register a new account within account database
     *
     * @param u The new user to add to account database
     */
    public void addUser(Guest u) {
        accountDatabase.add(u);

        //now add the user to the file. YOU WILL ONLY ADD GUESTS.
        //Agents and admins are hardcoded on the backend
        try {
            //BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            //  String username,String password,int id, String firstName, String lastName,int rewardPoints, String email
            System.err.println("HERE");
            String toWrite = "Guest," + u.getUsername() + "," + u.getPassword() + "," + u.getId() + ","
                    + u.getFirstName() + "," + u.getLastName() + "," + u.getRewardPoints() + "," + u.getEmail() + "\n";
            FileWriter write = new FileWriter(fileName, true);
            write.write(toWrite);
            write.close();
        } catch (IOException e) {
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
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            line = reader.readLine();

            while (line != null) {
                String[] split = line.split(",");

                //if the username is found, return the account type
                if (split[1].equals(username)) {
                    return split[0];
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.err.println("No account found, returning empty account type.");
        return "";
    }

    /**
     * Operation to validate the existence of a given username.
     *
     * @param username A given username to validate
     */
    public static boolean accountExists(String username) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            line = reader.readLine();

            while (line != null) {
                String[] split = line.split(",");

                //if the username exists in the file return true
                if (split[1].equals(username)) {
                    return true;
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;

        /*boolean accountFlag=false;

        if(!accountDatabase.isEmpty()){
            for(User u: accountDatabase){
                if(u.getUsername().equals(username)){
                    accountFlag= true;
                    break;
                }
            }
        }

        return accountFlag;*/
    }

    /**
     * Operation to modify an account username if valid.
     *
     * @param oldUser User's old username to change
     * @param newUser A given username to change into
     * @return the truth value of successfully modifying account
     */
    public boolean modifyUsername(String oldUser, String newUser) throws IOException {
        boolean changeSuccess = false;
        int counter = 0;

        if (accountExists(newUser)) {
            for (User u : accountDatabase) {
                //finding the correct account to change username
                if (u.getUsername().equals(oldUser)) {
                    u.setUsername(newUser);
                    changeSuccess = true;
                    break;
                }
                ++counter;
            }

            //after changing on data structure, change on the file that stores accounts
            //modifyFileLine(counter,0,newUser);
        }

        return changeSuccess;
    }

    /**
     * Operation to modify an account password.
     *
     * @param username User's username
     * @param oldPass  User's old password to change
     * @param newPass  A given password to change into
     */
    public static void modifyPassword(String username, String oldPass, String newPass) throws IOException {
        int counter = 0;

        //find account, then change data structure to match new password
        for(User u: accountDatabase){
            if(u.getUsername().equals(username)) {
                if (u.getPassword().equals(oldPass)) {
                    u.setPassword(newPass);
                    break;
                }
            }
            ++counter;
        }

        //change account database to match new password
        //modifyFileLine(counter,1,newPass);
    }


    /**
     * Operation to modify an account first name.
     *
     * @param username User's username
     * @param oldFName User's old first name to change
     * @param newFName A given first name to change into
     */
    public void modifyFirstName(String username, String oldFName, String newFName) throws IOException {
        int counter = 0;

        //update data structure
        for(User u: accountDatabase){
            if(u.getUsername().equals(username)) {
                if (u.getFirstName().equals(oldFName)) {
                    u.setFirstName(newFName);
                    break;
                }
                ++counter;
            }
        }

        //update file holding account list
        modifyFileLine(counter,2,newFName);
    }


    /**
     * Operation to modify an account last name.
     *
     * @param username User's username
     * @param oldLName User's old last name to change
     * @param newLName A given last name to change into
     */
    public void modifyLastName(String username, String oldLName, String newLName) throws IOException {
        int counter = 0;

        //update lastName on data structure
        for(User u: accountDatabase){
            if(u.getUsername().equals(username)) {
                if (u.getLastName().equals(oldLName)) {
                    u.setLastName(newLName);
                    break;
                }
            }
            ++counter;
        }

        //update file holding account list
        modifyFileLine(counter,3,newLName);
    }

    /**
     * Operation to modify account changes on file database
     *
     * @param lineIndex line to alter
     * @param dataToChange specific index of information to change for specified user
     * @param newData   the newData to replace old data in file database
     *
     */
    public static void modifyFileLine(int lineIndex, int dataToChange, String newData) throws IOException {
        String line;
        ArrayList<String> fileList = new ArrayList<>();

        InputStream in = AccountDatabase.class.getClassLoader().getResourceAsStream("accountList.csv");
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        //copy lines into arraylist
        while ((line = reader.readLine()) != null) {
            //find specified line to remove
            fileList.add(line);

        }
        reader.close();


        //modify specified line
        String linetoModify = fileList.get(lineIndex);
        //split line into pieces of data
        String[]split = linetoModify.split(",");

        //modify specific data you want
        split[dataToChange] = newData;

        //make a string builder to make new string
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= split.length; i++) {
            sb.append(split[i]);
        }

        String lineToPut = sb.toString();

        //put modified line back into ArrayList
        fileList.set(lineIndex,lineToPut);

        //overrides old file
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        for (String newFileLine : fileList) {
            writer.write(newFileLine);
            writer.newLine();
        }
        writer.close();
    }

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
    public static void updateAccount(User account,String email,String password) throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;
        String updatePasswordSQL = "UPDATE USERS" + " SET PASSWORD = ? " + " WHERE USERNAME = ?";
        String updateEmailSQL = "UPDATE USERS" + " SET EMAIL = ? " + " WHERE USERNAME = ?";
        try {
            dbConnection = DriverManager.getConnection(fileName);
            statement = dbConnection.createStatement();

            // execute update SQL stetement
            PreparedStatement preparedStatement = dbConnection.prepareStatement(updatePasswordSQL);
            PreparedStatement preparedStatement2 = dbConnection.prepareStatement(updateEmailSQL);

            //assigns query to specified user and their password
            preparedStatement.setString(1,password);
            preparedStatement.setString(2,account.getUsername());

            //assigns query to specified user and their email
            preparedStatement2.setString(1,email);
            preparedStatement2.setString(2,account.getUsername());

            //execute update
            int passwordUpdated = preparedStatement.executeUpdate();
            int emailUpdated = preparedStatement2.executeUpdate();

            //check if updates were made for each
            if(passwordUpdated > 0){
                System.out.println("Password updated!");
            }

            if(emailUpdated > 0){
                System.out.println("Email updated!");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }


    /**
     * Operation to delete account from SQL database
     *
     * @param userToRemove the specified user to remove
     *
     */
    public static void removeUser(User userToRemove) {
        try (Connection connection = DriverManager.getConnection(fileName)) {
            String select = "DELETE FROM USERS WHERE USERNAME = ?";
            try (PreparedStatement statement = connection.prepareStatement(select)) {
                statement.setString(1, userToRemove.getUsername());

                int deleted;
                deleted = statement.executeUpdate();

                if (deleted <= 0) {
                    System.out.println("Failed to delete data");
                }
                else{
                    System.out.println("Succesfuly removed user!");
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();

            System.err.println("Failed to connect to database.");
        }
    }

}
