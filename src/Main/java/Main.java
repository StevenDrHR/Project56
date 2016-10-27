
import org.apache.log4j.BasicConfigurator;
import spark.Spark;

import java.sql.Connection;

public class Main {
    //Driver name. Included with this Assignment submission.
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    //Credentials. Might differ for each database.
    static final String DB_URL = "jdbc:postgresql://localhost/postgres";
    static final String USER = "postgres";
    static final String PASS = "123lol123";
    //Public variable to establish SQL connection
    public Connection conn;
    //Classes initialization.
    static final Modal JD = new Modal();

    public static void main(String[] args) {
        BasicConfigurator.configure();
        JD.DataCon();
        Spark.staticFileLocation("/Css");
        String layout = "Css/RegisterScreen.css";
        View createLoginPage= new View();
        createLoginPage.ViewUsers();
        Controller RenderNewView = new Controller();
        RenderNewView.RenderLoginView();

    }





}

