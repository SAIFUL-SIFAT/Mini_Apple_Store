import java.sql.*;

public class dbFunc
{
    public static Connection getConnection(String dbPath)
    {
        Connection connection = null;      
        try {
            Class.forName("org.sqlite.JDBC");
           connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
           System.out.println("database opened successfully");
        } catch ( Exception e ) {
           System.err.println( e.getClass().getName() + ": " + e.getMessage() );
           System.exit(0);
        }

        return connection;        
    }

}