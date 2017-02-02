package Controller;

import java.sql.SQLException;
import java.util.Deque;

/**
 * Created by nilmor on 2/2/2017.
 */
public class AddProduct {
    Connection connection1 = new Connection();
    public String AddProducts(Deque<Model.AddProductModels> list) throws SQLException {
        try{
            java.sql.Connection connection = connection1.connection();
            String Querry = "INSERT INTO products (modal,brand,car_type,build_year,price,deliverytime,description) VALUES ('" + list.getFirst().getModal() + "','"+list.getFirst().getBrand() +"','"+ list.getFirst().getType() +"','"+ list.getFirst().getYear()+"',"+ list.getFirst().getPrice() +",'"+list.getFirst().getDeliverytime()+"','"+list.getFirst().getDescription()+"');";
            connection.prepareStatement(Querry).executeUpdate();
            return "Done";
        }
        catch (SQLException e){
            return "failed";
        }
    }
}
