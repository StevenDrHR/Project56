package Db;
import java.sql.*;

public class JDBCExample {
    //Driver name. Included with this Assignment submission.
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    //Credentials. Might differ for each database.
    static final String DB_URL = "jdbc:postgresql://localhost/postgres";
    static final String USER = "postgres";
    static final String PASS = "0906986";
    //Public variable to establish SQL connection
    public Connection conn;
    //Classes initialization.
    static final JDBCExample JD = new JDBCExample();

    //Main. Uncomment classes before use.
    public static void main(String[] args) {

        //Just verify connection. Nothing else.
        JD.DataCon();

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
        //When something goes horribly wrong
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
