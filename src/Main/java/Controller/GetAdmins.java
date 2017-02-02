package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nilmor on 2/2/2017.
 */
public class GetAdmins {
    Connection connection1 = new Connection();
    public List<String> GetAdmins() throws SQLException {
        ArrayList<String> list = new ArrayList<String>();
        java.sql.Connection connection = connection1.connection();
        String Querry = "Select username from users where userlevel = 1";
        ResultSet rs = connection.prepareStatement(Querry).executeQuery();
        while(rs.next()){
            list.add(rs.getString("username"));

        }
        return list;
    }
}
