package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by nilmor on 2/2/2017.
 */
public class CheckWishListStatus {
    Connection connection1 = new Connection();
    public ArrayList<Integer> checkWishlistStatus() throws SQLException {
        ArrayList<Integer> list = new ArrayList<>();
        java.sql.Connection connection = connection1.connection();
        String Querry = "Select count(username) AS total from users where wishlist = 'public'";
        ResultSet rs = connection.prepareStatement(Querry).executeQuery();
        if (rs.next()) {
            list.add(rs.getInt("total"));
        }
        return list;
    }
}
