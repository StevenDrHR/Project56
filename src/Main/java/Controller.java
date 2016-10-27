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


    public void DataInsert( Deque<Modal> list) throws SQLException {{
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "123lol123");
        String Querry = "INSERT INTO users (firstname,lastname,email,username,user_password,userlevel) VALUES ('" + list.getFirst().getFname() + "','"+list.getFirst().getLname() +"','"+list.getFirst().getEmail() +"','"+ list.getFirst().getUsername()+"','"+   list.getFirst().getpassword() +"',0 );";
        connection.prepareStatement(Querry).executeUpdate();
        connection.close();
    }
}
}


