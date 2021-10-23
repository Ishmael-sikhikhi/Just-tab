
package justtap;

import java.sql.*;

public class DbConnect 
{
    Connection connection = null;
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://localhost/justtap?autoReconnect=true&useSSL=false";

    public DbConnect()
    {
    }
    public static Connection  dbConnect() throws SQLException, ClassNotFoundException
    {
              Class.forName(JDBC_DRIVER);
              return DriverManager.getConnection(DATABASE_URL, "root", "ishmael");
    }
}
