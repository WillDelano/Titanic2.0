package edu;

import edu.authentication.Authentication;
import edu.databaseAccessors.RoomDatabase;
import edu.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AuthenticationTester {
    Authentication a;

    @BeforeEach
    public void makeStuff(){
        a = new Authentication();
    }

    public void validPass() throws UserNotFoundException {
        makeStuff();
        assert(a.login("travel","baylor"));
    }

    @Test
    public void validFail() throws UserNotFoundException {
        assert(!a.login("not","working"));
    }


}
