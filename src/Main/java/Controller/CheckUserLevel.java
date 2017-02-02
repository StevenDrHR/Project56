package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by nilmor on 2/2/2017.
 */
public class CheckUserLevel {
    Connection connection1 = new Connection();
    public String checkUserLevel(String UserName) throws SQLException {
        java.sql.Connection connection = connection1.connection(); //Getting the connection to the database
        String Querry = "Select username from users where username ='"+UserName+"' and userlevel = 1"; // checking if the user is a admin
        ResultSet rs = connection.prepareStatement(Querry).executeQuery();
        if(rs.next()) {
            return "admin";
        }
        Querry = "Select username from users where username ='"+UserName+"' and userlevel = 0"; // checking if the user is a user
        rs = connection.prepareStatement(Querry).executeQuery();
        if(rs.next()) {
            return "user";
        }
        return "not registered";
    }
}
