package pro;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static final Properties props = new Properties();

    static {
        try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new RuntimeException(
                    "db.properties not found. Copy db.properties.example to db.properties " +
                    "and fill in your MySQL details, then make sure it's on the classpath."
                );
            }
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load db.properties", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
            props.getProperty("db.url"),
            props.getProperty("db.user"),
            props.getProperty("db.password")
        );
    }
}
