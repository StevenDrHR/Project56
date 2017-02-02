package Controller;

import java.sql.SQLException;

/**
 * Created by nilmor on 2/2/2017.
 */
public class SetWishlistPrivate {
    public String setWishlistToPrivate(String username) throws SQLException {
        Connection connection1 = new Connection(); //Getting the connection to the database
        try{
            java.sql.Connection connection = connection1.connection();
            String Querry = "UPDATE users SET wishlist = 'private' WHERE username = '"+username+"';"; // Setting the wishlist of an user private
            connection.prepareStatement(Querry).executeUpdate();
            return "Done";
        }
        catch (SQLException e){
            return "failed";
        }
    }
}
