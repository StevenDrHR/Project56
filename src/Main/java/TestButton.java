import junit.framework.Assert;
import org.junit.Test;

import java.sql.SQLException;

/**
 * Created by Gebruiker on 2-2-2017.
 */
public class TestButton {
    @Test
    public void Testbutton() throws SQLException{
        String currentUser = "User";
        try {
            Assert.assertTrue(currentUser.equals("Admin"));
            System.out.println("kaolo goeie man");
    }
        catch (AssertionError e){
            System.out.println("kaolo onsuccesvol");
            throw e;
        }
    }
}
