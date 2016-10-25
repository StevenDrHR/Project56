package Db;


import javafx.beans.binding.When;
import jdk.nashorn.internal.scripts.JD;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import scala;
import static java.util.jar.Pack200.Packer.PASS;
import static spark.Spark.*;


public class JDBCExample {
    //Driver name. Included with this Assignment submission.
    //static final String JDBC_DRIVER = "org.postgresql.Driver";
    //Credentials. Might differ for each database.
    static final String DB_URL = "jdbc:postgresql://localhost/postgres";
    static final String USER = "postgres";
    static final String PASS = "123lol123";
    //Public variable to establish SQL connection
    public Connection conn;
    //Classes initialization.
    static final JDBCExample JD = new JDBCExample();

    //Main. Uncomment classes before use.
    public static void main(String[] args) {

        //Just verify connection. Nothing else.
        JD.DataCon();

        //get("/hello", (req, res) -> "Hello World");

        //get("/", (req, res) -> renderContent("RegisterScreen.html"));
        //Uncomment to Update something
        //PU.UpdateQuery();

        //Uncomment to Insert something
        //PI.InsertQuery();

        //Uncomment to Select something
        //PS.SelectQuery();
    }

    public void DataCon() {
    //Connection
       try {
           //Driver name + credentials + ip address check.
          Class.forName("org.postgresql.Driver");
           System.out.println("Connecting to a selected database...");
           conn = DriverManager.getConnection(DB_URL, USER, PASS);
           System.out.println("Connected database successfully...");
        }
       // When something goes horribly wrong
      catch (Exception e) {
           e.printStackTrace();
        }
    }



    private String renderContent(String htmlFile) {
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
}