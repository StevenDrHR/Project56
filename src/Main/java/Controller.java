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

    public String checkUserStatus(String UserName) throws SQLException {
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "123lol123");
        String Querry = "Select username from users where username ='" + UserName + "' and userstatus = 'Blocked'";
        ResultSet rs = connection.prepareStatement(Querry).executeQuery();
        if (rs.next()) {
            return "Blocked";
        }
        return "Available";
    }

    public String checkUserLevel(String UserName) throws SQLException{
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "123lol123");
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
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "123lol123");
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
        Querry = "INSERT INTO users (firstname,lastname,email,username,user_password,userlevel,age,userstatus) VALUES ('" + list.getFirst().getFname() + "','"+list.getFirst().getLname() +"','"+list.getFirst().getEmail() +"','"+ list.getFirst().getUsername()+"','"+   list.getFirst().getpassword() +"',0,'"+list.getFirst().getAge()+"','Available' );";
        try {
            connection.prepareStatement(Querry).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return "Something went wrong with the regestration";
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
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "123lol123");
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
    connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "123lol123");
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
    public String DeleteUser(String username) throws SQLException {
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "123lol123");
        String Querry = "Delete from users where username ='"+username+"';";
        connection.prepareStatement(Querry).executeUpdate();
        return "Done";
        }

    public String ResetPassword(String username) throws SQLException{
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "123lol123");
        String Querry = "Update users set user_password = '12345' where username ='"+username+"';";
        connection.prepareStatement(Querry).executeUpdate();
        return "Done";
    }
    public String BlockUser(String username) throws SQLException{
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "123lol123");
        String Querry = "Update users set userstatus = 'Blocked' where username ='"+username+"';";
        connection.prepareStatement(Querry).executeUpdate();
        return "Done";
    }
    public String UnblockUser(String username) throws SQLException{
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "123lol123");
        String Querry = "Update users set userstatus = 'Available' where username ='"+username+"';";
        connection.prepareStatement(Querry).executeUpdate();
        return "Done";
    }
}




