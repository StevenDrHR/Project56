package Controller;

import java.sql.SQLException;

/**
 * Created by nilmor on 2/2/2017.
 */
public class ResetPassword {
    Connection connection1 = new Connection();
    public String ResetPassword(String username) throws SQLException {
        java.sql.Connection connection = connection1.connection();
        try{
            String Querry = "Update users set user_password = '12345' where username ='"+username+"';";
            connection.prepareStatement(Querry).executeUpdate();
            return "Done";
        }
        catch (SQLException e){
            return "failed";
        }
    }
}
