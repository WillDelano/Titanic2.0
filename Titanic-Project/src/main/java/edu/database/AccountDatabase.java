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
 * @version 1.0
 */
public class AccountDatabase {

    static Set<User> accountDatabase;
 

    /**
     * operation to validate username from input
     *
     * @param username   The username of the user.
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
     * operation to remove an account within the login Inventory
     *
     * @param userToRemove The specified user to remove
     */
    public void removeUser(User userToRemove){
        accountDatabase.remove(userToRemove);
    }

    public boolean accountExists(String username) {
        boolean accountFlag=false;
        
        for(User u: accountDatabase){
            if(u.getUsername().equals(username)){
                accountFlag= true;
            }
        }
        
        return accountFlag;
    }
}
