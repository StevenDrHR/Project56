import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Created by nilmor on 10/27/2016.
 */
public class Controller {
    static Connection connection;

    public String checkUserLevel(String UserName) throws SQLException{
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "0906986");
        String Querry = "Select username from users where username ='"+UserName+"' and userlevel = 1";
        ResultSet rs = connection.prepareStatement(Querry).executeQuery();
            if(rs.next()) {
                return "admin";
            }
        Querry = "Select username from users where username ='"+UserName+"' and userlevel = 0";
        rs = connection.prepareStatement(Querry).executeQuery();
        if(rs.next()) {
            return "user";
        }
            return "not registered";
        }



    public String RegisterUser( Deque<Modal> list) throws SQLException {
        int userid = 34;
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "0906986");
        String Querry = "Select username from users where username ='"+list.getFirst().getUsername()+"';";
        ResultSet rs = connection.prepareStatement(Querry).executeQuery();
        if(rs.next()) {
            return "Username already exists";
        }
        Querry = "Select email from users where email ='"+list.getFirst().getEmail()+"';";
        rs = connection.prepareStatement(Querry).executeQuery();
        if(rs.next()) {
            return "This Email adress is already used";
        }
        Querry = "INSERT INTO users (firstname,lastname,email,username,user_password,userlevel,age) VALUES ('" + list.getFirst().getFname() + "','"+list.getFirst().getLname() +"','"+list.getFirst().getEmail() +"','"+ list.getFirst().getUsername()+"','"+   list.getFirst().getpassword() +"',0,'"+list.getFirst().getAge()+"' );";
        try {
            connection.prepareStatement(Querry).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            String m = e.getMessage();
            return m;
        }

        Querry ="Select userid from users where email='"+ list.getFirst().getEmail()+"'";
        rs = connection.prepareStatement(Querry).executeQuery();
        if(rs.next()) {
          userid = rs.getInt("userid") ;
        }
        Querry = "INSERT INTO adress (userid,country,streetname,streetnumber,postalcode) VALUES (" + userid + ",'"+list.getFirst().getCounrty() +"','"+list.getFirst().getStreet() +"','"+ list.getFirst().getStreetNumber()+"','"+   list.getFirst().getPostalCode() +"' );";
        try {
            connection.prepareStatement(Querry).executeUpdate();
        } catch (SQLException e) {
            Querry = "Delete From users Where userid='"+userid+"';";;
            connection.prepareStatement(Querry).executeUpdate();
            e.printStackTrace();
            String m = e.getMessage();
            return m;
        }
        connection.close();
        return "Done";
    }

    public String LoginUser( Deque<Modal> list) throws SQLException {
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "0906986");
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
public List<String> GetUsers() throws SQLException {
    ArrayList<String> list = new ArrayList<String>();
    connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "0906986");
    String Querry = "Select username from users where userlevel = 0";
    ResultSet rs = connection.prepareStatement(Querry).executeQuery();
    while(rs.next()){
        list.add(rs.getString("username"));
        System.out.println(rs.getString("username"));
        String zin = "hallo,shithoofd";
        String[] parts = zin.split(",");

        for (int i = 0; i < parts.length; i++){
            System.out.println(parts[i]);
        }
    }
    return list;
}
}



