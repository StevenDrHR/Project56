import junit.framework.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

//import java.sql.SQLException;

public class TestJUnit {
    @Test

    public void testAdd() throws SQLException {
        Deque<Modal> RegisterUsers = new ArrayDeque<>();
        Modal registerUser = new Modal();
        registerUser.RegisterModal("Kees", "Kees", "Kees@kees.nl", "Kees", "Kees", "2", "Keesstraat", "12", "Keesland", "3030KE");
        RegisterUsers.addFirst(registerUser);
        Controller testController = new Controller();
        try {
            Assert.assertTrue(testController.RegisterUser(RegisterUsers).equals("Done"));
            System.out.println("User added - passed");
            Assert.assertTrue(testController.RegisterUser(RegisterUsers).equals("Username already exists"));
            System.out.println("User already exists - passed");
        } catch (AssertionError e) {
            System.out.println("User added - failed");
            System.out.println("User already exists - failed");
            System.out.println("Banaan");
            throw e;
        }

    }


}



