package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by nilmor on 2/2/2017.
 */
public class GetFavourites {
    Connection connection1 = new Connection();
    public ArrayList<String> getFavourites(String userid) throws SQLException {
        ArrayList<String> list = new ArrayList<String>();
        java.sql.Connection connection = connection1.connection();
        String Querry = "Select p.brand, p.modal, p.car_type, p.price from products p, favourites f where p.productid = f.productid and f.userid = "+userid;
        ResultSet rs = connection.prepareStatement(Querry).executeQuery();
        while(rs.next()){
            list.add(rs.getString("modal"));
            list.add(rs.getString("brand"));
            list.add(rs.getString("car_type"));
            list.add(rs.getString("price"));

        }
        return list;
    }
}
