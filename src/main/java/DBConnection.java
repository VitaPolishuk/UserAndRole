import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private Connection connection;
    private static final String  URL = "jdbc:mysql://localhost:3306/mysql";
    public DBConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        DriverManager.registerDriver(new FabricMySQLDriver());
    }
    public Connection getConnection(String username, String password) throws SQLException {
        if (connection != null)
            return connection;
       return connection = DriverManager.getConnection(URL, username, password);

    }}


