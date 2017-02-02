package Controller;

import java.sql.SQLException;

/**
 * Created by nilmor on 2/2/2017.
 */
public class BlockUser {
    Connection connection1 = new Connection();//initialize connection from connection class with database
    public String BlockUser(String username) throws SQLException {
        try{
            java.sql.Connection connection = connection1.connection(); //connect with the database with the connect method
            String Querry = "Update users set userstatus = 'Blocked' where username ='"+username+"';"; //query statement with flexible variable
            connection.prepareStatement(Querry).executeUpdate();//execute query
            return "Done";
        }
        catch (SQLException e){
            return "failed";
        }
    }
}
