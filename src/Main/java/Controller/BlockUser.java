package Controller;

import java.sql.SQLException;

/**
 * Created by nilmor on 2/2/2017.
 */
public class BlockUser {
    Connection connection1 = new Connection();
    public String BlockUser(String username) throws SQLException {
        try{
            java.sql.Connection connection = connection1.connection(); //Getting the connection to the database
            String Querry = "Update users set userstatus = 'Blocked' where username ='"+username+"';"; // Blocking an User in the Database
            connection.prepareStatement(Querry).executeUpdate();
            return "Done";
        }
        catch (SQLException e){
            return "failed";
        }
    }
}
