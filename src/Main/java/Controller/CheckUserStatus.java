package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Created by nilmor on 2/2/2017.
 */
public class CheckUserStatus {
    Connection connection1 = new Connection();


    public String checkUserStatus(String UserName) throws SQLException {
        java.sql.Connection connection = connection1.connection();
        String Querry = "Select username from users where username ='" + UserName + "' and userstatus = 'Blocked'";     // query statement with flexible variable
        ResultSet rs = connection.prepareStatement(Querry).executeQuery();      // execute query
        if (rs.next()) {        // checks if there is a result, go in the if
            return "Blocked";
        }
        return "Available";
    }
}
