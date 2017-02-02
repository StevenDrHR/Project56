package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nilmor on 2/2/2017.
 */
public class GetWishlistInfo {
    Connection connection1 = new Connection();
    public List<String> getWishlistinfo(String username) throws SQLException {
        ArrayList<String> list = new ArrayList<String>();
        java.sql.Connection connection = connection1.connection(); //Getting the connection to the database
        String Querry = "Select p.modal, p.brand, p.car_type, p.price from products p, wishlist w, users u where p.productid = w.productid and u.userid = w.userid and u.username = '"+username+"'"; //getting the wishlistdata of one item
        ResultSet rs = connection.prepareStatement(Querry).executeQuery();
        while (rs.next()) {
            list.add(rs.getString("modal"));
            list.add(rs.getString("brand"));
            list.add(rs.getString("car_type"));
            list.add(rs.getString("price"));

        }
        return list;
    }
}
