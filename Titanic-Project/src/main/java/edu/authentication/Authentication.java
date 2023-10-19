package edu.authentication;

import edu.database.AccountDatabase;
import edu.core.uniqueID.UniqueID;
import edu.core.users.Admin;
import edu.core.users.Guest;
import edu.core.users.TravelAgent;

import java.io.*;
import java.util.*;
import java.lang.*;
/**
 * Interface for any authentication features
 *
 * <p>
 * Contains all the Authentication methods each user class must implement.
 * </p>
 *
 * @author Gabriel Choi
 * @version 1.0
 */
public class Authentication {


    /**
     * creates the account of the user.
     *
     * @param username   The username of the user.
     * @param password   The password of the user.
     * @param firstName  The first name of the user.
     * @param lastName   The last name of the user.
     * @return if the account has successfully been created
     */
    public void createAccount(String username, String password, String firstName, String lastName, String email){

        AccountDatabase d = new AccountDatabase();

        if(!d.accountExists(username)){
            Guest guest = new Guest(username,password,new UniqueID().getId(),firstName,lastName,0, email);
            d.addUser(guest);
        }


    }



    /**
     * operation to validate login information from input
     *
     * @param username   The given username for potential user account.
     * @param password   The given password for potential user account.
     */
    public boolean login(String username, String password){
        AccountDatabase loginList= new AccountDatabase();
        boolean validLogin = false;

        //first check if username and pw are valid  and connected
        if(loginList.isValidLogin(username,password)){
            validLogin=true;
        }
        return validLogin;
    }


}
