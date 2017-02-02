package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nilmor on 2/2/2017.
 */
public class GetProductInfo {
    Connection connection1 = new Connection();
    public List<String> getPinfo(String pid) throws SQLException {
        java.sql.Connection connection = connection1.connection(); //Getting the connection to the database
        ArrayList<String> list = new ArrayList<String>();
        String Querry = "Select * from products where productid = "+pid; //Getting the product info of a product
        ResultSet rs = connection.prepareStatement(Querry).executeQuery();
        while (rs.next()) {
            list.add(rs.getString("modal"));
            list.add(rs.getString("brand"));
            list.add(rs.getString("car_type"));
            list.add(rs.getString("price"));
        }
        return list;
    }
}
