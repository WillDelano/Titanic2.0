package edu.authentication;

import edu.core.users.CurrentGuest;
import edu.databaseAccessors.AccountDatabase;
import edu.uniqueID.UniqueID;
import edu.core.users.Guest;

import java.lang.*;
import java.util.Objects;

/**
 * Interface for any authentication features
 *
 * <p>
 * Contains all the Authentication methods each user class must implement.
 * </p>
 *
 * @author Gabriel Choi
 * @version 1.1
 */
public class Authentication {


    /**
     * creates the account of the user.
     *
     * @param username   The username of the user.
     * @param password   The password of the user.
     * @param firstName  The first name of the user.
     * @param lastName   The last name of the user.
     *
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
        AccountDatabase loginList = new AccountDatabase();
        boolean validLogin = false;

        //first check if username and pw are valid  and connected
        if(loginList.isValidLogin(username,password)){
            System.out.println("Success");
            validLogin=true;

            if (Objects.equals(AccountDatabase.getAccountType(username), "Guest")) {
                System.err.println("HERE");
                CurrentGuest.setCurrentGuest((Guest) AccountDatabase.getUser(username));
                CurrentGuest.getCurrentGuest().setId(AccountDatabase.getUser(username).getId());
            }
        }
        return validLogin;
    }
}
