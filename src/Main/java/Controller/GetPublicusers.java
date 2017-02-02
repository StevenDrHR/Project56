package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nilmor on 2/2/2017.
 */
public class GetPublicusers {
    Connection connection1 = new Connection();
    public List<String> getPublicusers(String CurrentUser) throws SQLException {
        ArrayList<String> list = new ArrayList<String>();
        java.sql.Connection connection = connection1.connection(); //Getting the connection to the database
        String Querry = "select username from users where wishlist = 'public' and username != '"+CurrentUser+"'";// Get all wishlist of other people that are public
        ResultSet rs = connection.prepareStatement(Querry).executeQuery();
        while (rs.next()) {
            Querry = "select u.username from users u ,wishlist w where u.username ='"+rs.getString("username")+"' and u.userid = w.userid and w.userid > 0;";
            ResultSet rs2 = connection.prepareStatement(Querry).executeQuery();
            if(rs2.next()) {
                list.add(rs2.getString("username"));
            }

        }
        return list;
    }
}
