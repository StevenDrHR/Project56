package Controller;

import java.sql.SQLException;

/**
 * Created by nilmor on 2/2/2017.
 */
public class AddFavourite {
    Connection connection1 = new Connection();
    public String addFavourite(String productid, String userid) throws SQLException {
        try{
            java.sql.Connection connection = connection1.connection(); //Getting the connection to the database
            String Querry = "INSERT INTO favourites (productid,userid) VALUES ("+productid+", "+userid+");"; //Adding a product to the favourite in the database
            connection.prepareStatement(Querry).executeUpdate();
            return "Done";
        }
        catch (SQLException e){
            return "failed";
        }
    }
}
