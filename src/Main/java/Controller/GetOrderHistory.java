package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by nilmor on 2/2/2017.
 */
public class GetOrderHistory {
    Connection connection1 = new Connection();
    public ArrayList GetOrderHistory(String userid) throws SQLException {
        java.sql.Connection connection = connection1.connection();
        String Querry = "Select  op.orderid , op.amount , op.productid from orders_products op,orders o where op.orderid = o.orderid and o.userid = "+userid+";";
        ResultSet rs = connection.prepareStatement(Querry).executeQuery();
        ArrayList result = new ArrayList<>();
        while(rs.next()) {
            result.add(rs.getInt("orderid"));
            result.add(rs.getInt("amount"));
            result.add(rs.getInt("productid"));
        }
        return result;
    }
}
