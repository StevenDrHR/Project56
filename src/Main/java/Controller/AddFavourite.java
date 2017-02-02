package Controller;

import java.sql.SQLException;

/**
 * Created by nilmor on 2/2/2017.
 */
public class AddFavourite {
    Connection connection1 = new Connection(); //initialize connection from connection class
    public String addFavourite(String productid, String userid) throws SQLException {
        try{
            java.sql.Connection connection = connection1.connection(); //connect with the database with the connect method
            String Querry = "INSERT INTO favourites (productid,userid) VALUES ("+productid+", "+userid+");"; //query statement with flexible variable
            connection.prepareStatement(Querry).executeUpdate();//execute query
            return "Done";
        }
        catch (SQLException e){
            return "failed";
        }
    }
}
