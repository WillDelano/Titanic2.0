package edu;

import edu.core.users.User;
import edu.databaseAccessors.AccountDatabase;
import edu.exceptions.UserNotFoundException;
import org.junit.jupiter.api.*;

public class AccountDatabaseTester {
    static AccountDatabase accounts = new AccountDatabase();



    @BeforeEach
    public void instantiate(){
       accounts = new AccountDatabase();
    }


    @Test
    public void loginPass() throws UserNotFoundException {
        assert(accounts.isValidLogin("travel","baylor"));
    }

    @Test
    public void loginFail() throws UserNotFoundException {
        Assertions.assertFalse( accounts.isValidLogin("FAILFAILFAIL","FAILFAILFAIL"));
    }

    @Test
    public void getTypeTest(){
        String agent = "Agent";
        Assertions.assertEquals(accounts.getAccountType("travel"),agent);
    }


    @Test
    public void existsPass(){
        assert (accounts.accountExists("travel"));
    }

    @Test
    public void existFail(){
        assert(!accounts.accountExists("thisdoesntexist"));
    }

    @Test
    public void modifyPassword() throws UserNotFoundException {
        accounts.modifyPassword("travel","baylor3");

        User a = accounts.getUser("travel");

        assert(a.getPassword().equals("baylor3"));
    }



    @Test
   public void modifyEmailTest() throws UserNotFoundException {
        User a = accounts.getUser("travel");


        //User account, String email, String password, String username, String firstName, String lastName)
        accounts.updateAccount(a,"new@gmail.com",a.getPassword(),a.getUsername(),a.getFirstName(),a.getLastName());

        User newChange = accounts.getUser("travel");


        assert(newChange.getEmail().equals("new@gmail.com"));
    }


    @AfterEach
    public void backToDefault() throws UserNotFoundException {
        accounts.modifyPassword("travel","baylor");
        User  p = accounts.getUser("travel");
                accounts.updateAccount(p,"agent@gmail.com",p.getPassword(),p.getUsername(),p.getFirstName(),p.getLastName());

    }




}
