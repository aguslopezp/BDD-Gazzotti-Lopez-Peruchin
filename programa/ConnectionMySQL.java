public class ConnectionMySQL {
    public String driver;
    public String url;
    public String username;
    public String password;

    public Connect(){
        driver = "com.mysql.cj.jdbc.Driver";
        url = "jdbc:mysql://localhost:3306/prueba?serverTimezone=UTC";
        username = "root";
        password = "root";
    }
}