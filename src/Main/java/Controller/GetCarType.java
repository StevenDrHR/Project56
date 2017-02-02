package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nilmor on 2/2/2017.
 */
public class GetCarType {
    Connection connection1 = new Connection();
    public List<String> getCarType() throws SQLException {
        ArrayList<String> list = new ArrayList<String>();
        java.sql.Connection connection = connection1.connection();
        String Querry = "Select car_type, Count(car_type) as totalcartype from products group by car_type";
        ResultSet rs = connection.prepareStatement(Querry).executeQuery();
        while(rs.next()){
            list.add(rs.getString("car_type"));
            list.add(rs.getString("totalcartype"));
        }
        return list;
    }
}
