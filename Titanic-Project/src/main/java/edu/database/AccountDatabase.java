package edu.core.landingpage;

import edu.core.users.User;

import java.io.*;
import java.util.*;
import java.lang.*;


/**
 * Inventory to record valid accounts and information for accounts
 *
 * This class documents a collection of unique usernames and another collection of unique passwords mapped with
 * user information.
 *
 * @author Michael Okonkwo
 * @version 1.1
 */
public class AccountDatabase {

    static Set<User> accountDatabase;
 

    /**
     * operation to validate login information from input
     *
     * @param username  The username of the user.
     */
    public boolean isValidLogin(String username, String pass){
        boolean isValid = false;
        for(User user:accountDatabase){
            if(user.getUsername().equals(username)){
                if(user.getPassword().equals(pass)){
                    isValid= true;
                }
            }
        }

        return isValid;
    }

    /**
     * operation to register a new account within account database
     *
     * @param newUser  The new user to add to account database
     */
    public void addUser(User newUser){
        accountDatabase.add(newUser);
    }

    /**
     * operation to remove an account within the account database
     *
     * @param userToRemove The specified user to remove
     */
    public void removeUser(User userToRemove){
        accountDatabase.remove(userToRemove);
    }

    /**
     * Operation to validate the existence of a given username.
     *
     * @param username A given username to validate
     */
    public boolean accountExists(String username) {
        boolean accountFlag=false;
        
        for(User u: accountDatabase){
            if(u.getUsername().equals(username)){
                accountFlag= true;
                break;
            }
        }

        return accountFlag;
    }

    /**
     * Operation to modify an account username if valid.
     *
     * @param oldUser User's old username to change
     * @param newUser A given username to change into
     * @return the truth value of successfully modifying account
     */
    public boolean modifyUsername(String oldUser,String newUser){
        boolean changeSuccess= false;

        if(!accountExists(newUser)){
            for(User u: accountDatabase){
                //finding the correct account to change username
                if(u.getUsername().equals(oldUser)){
                    u.setUsername(newUser);
                    changeSuccess = true;
                    break;
                }
            }
        }

        return changeSuccess;
    }

    /**
     * Operation to modify an account password.
     *
     * @param username User's username
     * @param oldPass User's old password to change
     * @param newPass A given password to change into
     */
    public void modifyPassword(String username,String oldPass,String newPass){

        for(User u: accountDatabase){
            if(u.getUsername().equals(username)) {
                if (u.getPassword().equals(oldPass)) {
                    u.setUsername(newPass);
                    break;
                }
            }
        }
    }


    /**
     * Operation to modify an account first name.
     *
     * @param username User's username
     * @param oldFName User's old first name to change
     * @param newFName A given first name to change into
     */
    public void modifyFirstName(String username,String oldFName,String newFName){

        for(User u: accountDatabase){
            if(u.getUsername().equals(username)) {
                if (u.getPassword().equals(oldFName)) {
                    u.setUsername(newFName);
                    break;
                }
            }
        }
    }


    /**
     * Operation to modify an account last name.
     *
     * @param username User's username
     * @param oldLName User's old last name to change
     * @param newLName A given last name to change into
     */
    public void modifyLastName(String username,String oldLName,String newLName){

        for(User u: accountDatabase){
            if(u.getUsername().equals(username)) {
                if (u.getPassword().equals(oldLName)) {
                    u.setUsername(newLName);
                    break;
                }
            }
        }
    }

}
