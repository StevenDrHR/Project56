package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by nilmor on 2/2/2017.
 */
public class CheckOwnWishList {
    Connection connection1 = new Connection();
    public ArrayList<Integer> checkOwnWishlist(String username) throws SQLException {
        ArrayList<Integer> list = new ArrayList<>();
        java.sql.Connection connection = connection1.connection(); //Getting the connection to the database
        String Querry = "Select count(p.modal) AS total from products p, wishlist w, users u where p.productid = w.productid and u.userid = w.userid and u.username = '"+username+"'"; //Getting the current users wishlist out of the database
        ResultSet rs = connection.prepareStatement(Querry).executeQuery();
        if (rs.next()) {
            list.add(rs.getInt("total"));
        }
        return list;
    }
}
