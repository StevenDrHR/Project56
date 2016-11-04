import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Deque;

/**
 * Created by nilmor on 10/27/2016.
 */
public class Controller {
    static Connection connection;
    public String htmlToString(String htmlFile) {
        try {
            // If you are using maven then your files
            // will be in a folder called resources.
            // getResource() gets that folder
            // and any files you specify.

            URL url = getClass().getResource(htmlFile);

            // Return a String which has all
            // the contents of the file.
            Path path = Paths.get(url.toURI());

            return new String(Files.readAllBytes(path), Charset.defaultCharset());
        } catch (IOException | URISyntaxException e) {
            // Add your own exception handlers here.
        }
        return null;
    }

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
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "0906986");
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
}}



