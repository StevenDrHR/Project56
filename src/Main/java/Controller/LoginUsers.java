package Controller;



import Model.LoginModals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Deque;



/**
 * Created by nilmor on 2/2/2017.
 */
public class LoginUsers {
    Connection connection1 = new Connection();
    public String LoginUser( Deque<LoginModals> list) throws SQLException {
        java.sql.Connection connection = connection1.connection();
        try{
            String Querry = "Select userid from users where username='"+ list.getFirst().getUsername()+"' and user_password='"+ list.getFirst().getpassword()+"'";
            ResultSet rs = connection.prepareStatement(Querry).executeQuery();
            if(rs.next()){
                return list.getFirst().getUsername();
            }
            Querry = "Select userid from users where username='"+ list.getFirst().getUsername()+"'";
            rs = connection.prepareStatement(Querry).executeQuery();
            if(rs.next()){
                return "Wrong Password";
            }
            else{return "Wrong Username";

            }
        }
        catch (SQLException e){
            return "failed";
        }
    }
}
