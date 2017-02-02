package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by nilmor on 2/2/2017.
 */
public class GetUserData {
    Connection connection1 = new Connection();
    public ArrayList<String> getUserData(String username) throws SQLException {
        java.sql.Connection connection = connection1.connection(); //Getting the connection to the database
        String Querry = "Select * from users where username ='"+username+"';"; // Getting all the data of an user
        ResultSet rs = connection.prepareStatement(Querry).executeQuery();
        ArrayList<String> result = new ArrayList<>();
        if (rs.next()) {
            result.add(rs.getString("firstname"));
            result.add(rs.getString("lastname"));
            result.add(rs.getString("age"));
            result.add(rs.getString("email"));
            result.add(rs.getString("user_password"));
            result.add(rs.getString("userstatus"));
            result.add(rs.getString("userid"));
            result.add(rs.getString("wishlist"));
        }

        return result;
    }
}
