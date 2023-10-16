package org.example;

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
public class LoginInventory {
    static Set<String> usernameList;

    //password as key
    //<username,firstname,lastname,email> as value.
    static Map<String,String> loginInfoList;

    /**
     * operation to validate username from input
     *
     * @param username   The username of the user.
     */
    public boolean isValidUserName(String username){
        return (usernameList.contains(username));
    }

    /**
     * operation to validate password from input
     *
     * @param pass   The pass of the user.
     */
    public boolean isValidPassword(String pass){
        return !(loginInfoList.containsKey(pass));
    }

    /**
     * operation to register a new account within login Inventory
     *
     * @param user   The username of the user.
     * @param pw     The password of the user.
     * @param fName  The first name of the user
     * @param lName  The last name of the user
     * @param email  The email address of the user
     * @param id     Generated id that is unique to user
     */
    public void addUser(String user,String pw,String fName, String lName,String email,int id){
        usernameList.add(user);

        String values =  user+","+fName+","+lName+","+email+","+id;
        loginInfoList.put(pw,values);
    }

    /**
     * operation to remove an account within the login Inventory
     *
     * @param user   The username of the existing user.
     * @param pw     The password of the existing user.
     */
    public void removeUser(String user,String pw){
        usernameList.remove(user);
        loginInfoList.remove(pw);
    }

}
