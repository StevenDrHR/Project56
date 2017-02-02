import junit.framework.Assert;
import org.apache.commons.collections.functors.FalsePredicate;
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
            Assert.assertFalse(currentUser.equals("Admin"));
            System.out.println("True");
    }
        catch (AssertionError e){
            System.out.println("False");
            throw e;
        }
    }
}
