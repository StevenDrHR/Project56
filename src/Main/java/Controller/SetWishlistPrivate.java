package Controller;

import java.sql.SQLException;

/**
 * Created by nilmor on 2/2/2017.
 */
public class SetWishlistPrivate {
    public String setWishlistToPrivate(String username) throws SQLException {
        Connection connection1 = new Connection();
        try{
            java.sql.Connection connection = connection1.connection();
            String Querry = "UPDATE users SET wishlist = 'private' WHERE username = '"+username+"';";
            connection.prepareStatement(Querry).executeUpdate();
            return "Done";
        }
        catch (SQLException e){
            return "failed";
        }
    }
}
