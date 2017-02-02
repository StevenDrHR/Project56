package Controller;

import java.sql.SQLException;

/**
 * Created by nilmor on 2/2/2017.
 */
public class UnBlockUser {
    Connection connection1 = new Connection();
    public String UnblockUser(String username) throws SQLException {
        java.sql.Connection connection = connection1.connection();
        String Querry = "Update users set userstatus = 'Available' where username ='"+username+"';";
        connection.prepareStatement(Querry).executeUpdate();
        return "Done";
    }
}
