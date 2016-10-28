import jdk.nashorn.internal.ir.debug.JSONWriter;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Deque;


import static org.postgresql.core.Oid.JSON;

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


    public JSONObject DataInsert( Deque<Modal> list) throws SQLException {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "0906986");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String Querry = "INSERT INTO users (firstname,lastname,email,username,user_password,userlevel) VALUES ('" + list.getFirst().getFname() + "','"+list.getFirst().getLname() +"','"+list.getFirst().getEmail() +"','"+ list.getFirst().getUsername()+"','"+   list.getFirst().getpassword() +"',0 );";
        try {
            connection.prepareStatement(Querry).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JSONObject p = new JSONObject();
            p.putOpt("name","shit");
            System.out.println(p);
            return p;
        }
        connection.close();
        JSONObject p = new JSONObject();
        p.putOpt("name","shit");
        System.out.println(p);


        return p;
    }
}



