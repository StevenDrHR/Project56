package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by nilmor on 2/2/2017.
 */
public class SetOrderHistory {
    Connection connection1 = new Connection();
    public String SetOrderHistory(String userid,String[] products, String[] amounts) throws SQLException {
        java.sql.Connection connection = connection1.connection(); //Getting the connection to the database
        int orderid = 0;
        ArrayList<String> list = new ArrayList<String>();
        String Querry = "INSERT INTO orders(userid) Values ("+userid+");";
        connection.prepareStatement(Querry).executeUpdate();
        Querry = "Select orderid from orders where userid = '"+userid+"' order by orderid desc"; // storring the users order history
        ResultSet rs = connection.prepareStatement(Querry).executeQuery();
        if(rs.next()) {
            orderid = rs.getInt("orderid") ;
        }
        for (int i = 0; i<products.length; i++) {
            System.out.println(products[i] + " shamlalalalalalal");
            Querry = "INSERT INTO orders_products(orderid,productid,amount) Values(" + orderid + "," + products[i] + "," + amounts[i] + ") ";
            connection.prepareStatement(Querry).executeUpdate();
        }
        return "Done";
    }
}
