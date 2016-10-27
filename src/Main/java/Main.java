
import org.apache.log4j.BasicConfigurator;
import spark.Spark;

import java.sql.Connection;
import java.sql.DriverManager;

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
    static final Main JD = new Main();



    public static void main(String[] args) {
        BasicConfigurator.configure();
        JD.DataCon();
        Spark.staticFileLocation("/Css");
        String layout = "Css/RegisterScreen.css";
        registerView createUser= new registerView();
        createUser.controlUsers();
        RenderHTML RenderNewView = new RenderHTML();
        RenderNewView.RenderLoginView();

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
        //When something goes horribly wrong
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}

