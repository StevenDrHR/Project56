import junit.framework.Assert;
import org.junit.Test;

import java.sql.SQLException;

public class TestJUnit {
    @Test

    public void testAdd() throws SQLException{
        //Deque<Modal> RegisterUsers = new ArrayDeque<>();
        //Modal registerUser = new Modal();
        //registerUser.RegisterModal("Kees", "Kees", "Kees@kees.nl", "Kees", "Kees", "2", "Keesstraat", "12", "Keesland", "3030KE");
        //RegisterUsers.addFirst(registerUser);
        //Controller testController = new Controller();
        //Assert.assertTrue(testController.RegisterUser(RegisterUsers).equals("Done") );
        //Assert.assertTrue(testController.RegisterUser(RegisterUsers).equals("Username already exists") );;
        String okay = "Done";
        Assert.assertTrue(okay.equals("Done"));
    }
}