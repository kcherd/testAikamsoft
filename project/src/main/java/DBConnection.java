import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/shop";
    private static final String USER = "admin";
    private static final String PASS = "1234";

    public static Connection connect() throws Exception {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(Project.DB_URL, Project.USER, Project.PASS);
            return conn;
        } catch (SQLException e) {
            throw new Exception("Ошибка при подключении к базе данных: " + e.getMessage());
        }
    }

    public static void closeDB(Connection connection) throws Exception {
        try {
            connection.close();
        }catch (SQLException e){
            throw new Exception("Ошибка при закрытии базы данных: " + e.getMessage());
        }
    }
}
