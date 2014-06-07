package scout;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by nmalloch on 6/5/2014
 */
public class DBConnector {
    public static Connection connection;

    public static void connectToDB() {
        try {
            String url = "jdbc:mysql://localhost:3306/";
            String dbName = "si";
            String driver = "com.mysql.jdbc.Driver";
            String userName = "admin";
            String password = "admin";

            Class.forName(driver).newInstance();

            connection = DriverManager.getConnection(url + dbName, userName, password);

            System.out.println("Success");
        } catch (Exception e) {
            System.err.println("not good");
            System.err.println(e.getMessage());
        }
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("connection close failed.");
            System.out.println(e.getMessage());
        }
    }
}
