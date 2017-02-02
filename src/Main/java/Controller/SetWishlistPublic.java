package Controller;

import java.sql.SQLException;

/**
 * Created by nilmor on 2/2/2017.
 */
public class SetWishlistPublic {
    Connection connection1 = new Connection();
    public String setWishlistToPublic(String username) throws SQLException {
        java.sql.Connection connection = connection1.connection(); //Getting the connection to the database
        String Querry = "UPDATE users SET wishlist = 'public' WHERE username = '"+username+"';"; // Setting a wishlist of a user public
        connection.prepareStatement(Querry).executeUpdate();
        return "Done";
    }
}
