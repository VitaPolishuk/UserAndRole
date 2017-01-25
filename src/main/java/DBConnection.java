import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by vita on 24.01.2017.
 */
public class DBConnection {
    private Connection connection;
    public DBConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        DriverManager.registerDriver(new FabricMySQLDriver());
    }
    public Connection getConnection(String url, String username, String password) throws SQLException {
        if (connection != null)
            return connection;
       return connection = DriverManager.getConnection(url, username, password);

    }}


