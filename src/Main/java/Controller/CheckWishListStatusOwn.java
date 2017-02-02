package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by nilmor on 2/2/2017.
 */
public class CheckWishListStatusOwn {
    Connection connection1 = new Connection();
    public ArrayList<Integer> checkWishlistStatusOwn(String username) throws SQLException {
    ArrayList<Integer> list = new ArrayList<>();
        java.sql.Connection connection = connection1.connection(); //Getting the connection to the database
    String Querry = "Select count(username) AS total from users where wishlist = 'public' and username != '"+username+"'"; // Getting all the wishlists
    ResultSet rs = connection.prepareStatement(Querry).executeQuery();
    if (rs.next()) {
        list.add(rs.getInt("total"));
    }
    return list;
}

}
