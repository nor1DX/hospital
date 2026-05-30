package hospital.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initialize() {
        String sql = loadSql("db/create_tables.sql");
        try (Connection conn = DatabaseConfig.getConnection();
                Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database schema", e);
        }
    }

    private static String loadSql(String resourcePath) {
        try (InputStream is = DatabaseInitializer.class.getClassLoader()
                .getResourceAsStream(resourcePath)) {
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load SQL file: " + resourcePath, e);
        }
    }
}
