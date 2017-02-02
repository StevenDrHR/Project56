package Controller;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by nilmor on 2/2/2017.
 */
public class Connection {
    static java.sql.Connection connection;

    public java.sql.Connection connection() throws SQLException {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://83.86.251.189:5432/postgres2", "postgres", "postgres");
            //connection string with the database
        } catch (SQLException e) {
            System.out.println("Failed"); //print failed in the console if the connection went wrong
        }
            return connection = DriverManager.getConnection("jdbc:postgresql://83.86.251.189:5432/postgres2", "postgres", "postgres");
       }//return connection if succes
}
