package Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mydatabase"
            + "?autoReconnect=true&verifyServerCertificate=false&useSSL=true";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "123456";
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
    }
}
