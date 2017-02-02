package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nilmor on 2/2/2017.
 */
public class GetModels {
    Connection connection1 = new Connection();
    public List<String> GetModels(String Category) throws SQLException {
        ArrayList<String> list = new ArrayList<String>();
        java.sql.Connection connection = connection1.connection();
        if(Category.equals("")) {
            String Querry = "Select modal from products";
            ResultSet rs = connection.prepareStatement(Querry).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("modal"));

            }
        }
        else if(Category.equals("brand")){
            String Querry = "Select modal from products order by brand ASC";
            ResultSet rs = connection.prepareStatement(Querry).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("modal"));
            }
        }
        else if(Category.equals("type")){
            String Querry = "Select modal from products order by car_type ASC";
            ResultSet rs = connection.prepareStatement(Querry).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("modal"));
            }
        }
        else if(Category.equals("year")){
            String Querry = "Select modal from products order by build_year ASC";
            ResultSet rs = connection.prepareStatement(Querry).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("modal"));
            }
        }
        else if(Category.equals("pricelth")){
            String Querry = "Select modal from products order by price ASC";
            ResultSet rs = connection.prepareStatement(Querry).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("modal"));
            }
        }
        else if(Category.equals("pricehtl")){
            String Querry = "Select modal from products order by price DESC";
            ResultSet rs = connection.prepareStatement(Querry).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("modal"));
            }
        }
        return list;
    }
}
