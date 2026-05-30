package hospital.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConfig {

    private static final Properties PROPERTIES = loadProperties();

    private static Properties loadProperties() {
        Properties props = new Properties();
        try (InputStream is = DatabaseConfig.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            props.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load application.properties", e);
        }
        return props;
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                PROPERTIES.getProperty("db.url"),
                PROPERTIES.getProperty("db.user"),
                PROPERTIES.getProperty("db.password")
        );
    }
}
