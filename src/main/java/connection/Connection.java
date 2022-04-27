package connection;

import constants.JDBC;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection {
    public static java.sql.Connection getConnection() {
        java.sql.Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String jdbcURL = JDBC.JDBC_URL;
            String jdbcUsername = JDBC.JDBC_USERNAME;
            String jdbcPassword = JDBC.JDBC_PASSWORD;
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
