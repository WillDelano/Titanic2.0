package org.example;

import java.util.*;
import java.io.*;


/**
 * Implementation to handle login events
 *
 * This class parses user input for their username and password then validates their login information
 * within the login Inventory and communicates the validity of their account information.
 *
 * @author Michael Okonkwo
 * @version 1.0
 */

public class Login {

    /**
     * operation to validate login information from input
     *
     * @param username   The given username for potential user account.
     * @param password   The given password for potential user account.
     */
    public boolean login(String username, String password){
        LoginInventory loginList= new LoginInventory();
        boolean validLogin = false;

        //first check if username and pw are valid  and connected
        if(loginList.isValidUserName(username)){
            if(loginList.isValidPassword(password)){
                //if the loginList contains the password and the subsequent username linked
                if (loginList.loginInfoList.get(password).contains(username)){
                    validLogin= true;
                }
            }
        }

        return validLogin;
    }
}
