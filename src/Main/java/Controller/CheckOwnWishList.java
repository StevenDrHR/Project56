package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by nilmor on 2/2/2017.
 */
public class CheckOwnWishList {
    Connection connection1 = new Connection(); //initialize connection from connection class
    public ArrayList<Integer> checkOwnWishlist(String username) throws SQLException {
        ArrayList<Integer> list = new ArrayList<>(); //arraylist for result
        java.sql.Connection connection = connection1.connection(); //connect with the database with the connect method
        String Querry = "Select count(p.modal) AS total from products p, wishlist w, users u where p.productid = w.productid and u.userid = w.userid and u.username = '"+username+"'";
        ResultSet rs = connection.prepareStatement(Querry).executeQuery(); //execute query and go through the result
        if (rs.next()) { //if there is a next put it in the list
            list.add(rs.getInt("total")); //request result from a specific table
        }
        return list; //return the list
    }
}
