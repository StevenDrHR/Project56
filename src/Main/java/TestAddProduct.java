import junit.framework.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

/**
 * Created by Gebruiker on 2-2-2017.
 */
public class TestAddProduct {
    @Test
    public void Testaddproduct() throws SQLException {
        ArrayList<String> wishlistitems = new ArrayList<String>();
        Controller.SetToWishList newWishlist = new Controller.SetToWishList();
        newWishlist.setToWishlist("4", "4");
        Controller.SetToWishList testController = new Controller.SetToWishList();
        try {
            Assert.assertTrue(testController.setToWishlist("4", "4").equals("Done"));
            System.out.println("Product added - passed");
            Assert.assertTrue(testController.setToWishlist("4", "4").equals("Already added to the wishlist"));
            System.out.println("Product already exists - passed");
        } catch (AssertionError e) {
            System.out.println("Product added - failed");
            System.out.println("Product already exists - failed");
            System.out.println("appel");
            throw e;
        }
    }
}
