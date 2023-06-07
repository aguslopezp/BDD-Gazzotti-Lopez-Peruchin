import java.sql.*;

public class ConnectionMySQL {
    
    private static String url = "jdbc:mysql://localhost:3306/Tp_grupo_9";
    private static String username = "root";
    private static String password = "ivan2708";
    private static Connection connection = null;

    public static Connection getInstance() throws SQLException{
        if(connection == null){
            connection = DriverManager.getConnection(url, username, password);
        }
        return connection;
    }
}