package Controller;

import java.sql.SQLException;

/**
 * Created by nilmor on 2/2/2017.
 */
public class SetToWishList {
    Connection connection1 = new Connection();
    public String setToWishlist(String userid, String productid) throws SQLException {
        java.sql.Connection connection = connection1.connection();
        String Querry = "insert INTO wishlist (userid, productid) VALUES ("+userid+" , "+productid+");";
        try {
            connection.prepareStatement(Querry).executeUpdate();
        } catch (SQLException e) {
            return "Already added to the wishlist";
        }


        return "Done";
    }
}
