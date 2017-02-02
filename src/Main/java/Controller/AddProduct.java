package Controller;

import java.sql.SQLException;
import java.util.Deque;

/**
 * Created by nilmor on 2/2/2017.
 */
public class AddProduct {
    Connection connection1 = new Connection(); //initialize connection from connection class
    public String AddProducts(Deque<Model.AddProductModels> list) throws SQLException {
        try{
            java.sql.Connection connection = connection1.connection(); //connect with the database with the connect method
            String Querry = "INSERT INTO products (modal,brand,car_type,build_year,price,deliverytime,description) VALUES ('" + list.getFirst().getModal() + "','"+list.getFirst().getBrand() +"','"+ list.getFirst().getType() +"','"+ list.getFirst().getYear()+"',"+ list.getFirst().getPrice() +",'"+list.getFirst().getDeliverytime()+"','"+list.getFirst().getDescription()+"');"; //query statement with flexible variable
            connection.prepareStatement(Querry).executeUpdate(); //execute query
            return "Done";
        }
        catch (SQLException e){
            return "failed";
        }
    }
}
