import junit.framework.Assert;
import org.junit.Test;

import java.sql.SQLException;

/**
 * Created by Gebruiker on 2-2-2017.
 */
public class TestButton2 {
    @Test
    public void Testbutton2() throws SQLException{
        String currentUser = "User";
        try {
            Assert.assertTrue(currentUser.equals("User"));
            System.out.println("True");

        }
        catch (AssertionError e){
            System.out.println("False");
            throw e;
        }
    }
}
