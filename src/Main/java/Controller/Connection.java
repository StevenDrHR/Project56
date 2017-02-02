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
//        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "0906986");
        } catch (SQLException e) {
            System.out.println("Failed");
        }
            return connection = DriverManager.getConnection("jdbc:postgresql://83.86.251.189:5432/postgres2", "postgres", "postgres");
       }
}
