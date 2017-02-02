package Controller;

import java.sql.SQLException;

/**
 * Created by nilmor on 2/2/2017.
 */
public class DeleteUser {
    Connection connection1 = new Connection();
    public String DeleteUser(String username) throws SQLException {
        try{
            java.sql.Connection connection = connection1.connection();
            String Querry = "Delete from users where username ='"+username+"';";
            connection.prepareStatement(Querry).executeUpdate();
            return "Done";
        }
        catch (SQLException e){
            return "failed";
        }
    }
}
