package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nilmor on 2/2/2017.
 */
public class GetMostSold {
    Connection connection1 = new Connection();
    public List<String> getMostSold() throws SQLException {
        ArrayList<String> list = new ArrayList<String>();
        java.sql.Connection connection = connection1.connection(); //Getting the connection to the database
        String Querry = "Select p.modal, p.brand, Count(o.productid)as mostwanted from products p, orders_products o where o.productid = p.productid group by p.modal,p.brand limit 3";// Getting the top 3 of most sold cars
        ResultSet rs = connection.prepareStatement(Querry).executeQuery();
        while(rs.next()){
            list.add(rs.getString("modal"));
            list.add(rs.getString("brand"));
            list.add(rs.getString("mostwanted"));
        }
        return list;
    }
}
