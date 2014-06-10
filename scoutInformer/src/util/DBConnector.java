package util;

import constants.KeyConst;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by nmalloch on 6/5/2014
 */
public class DBConnector {
    public static Connection connection;
    private static String driver = "com.mysql.jdbc.Driver";
    private static String dbName;
    private static String url;
    private static String userName;
    private static String password;

    public boolean checkForDBConnection() {
        boolean connectionIsGood = true;

        try {
            Properties properties = new Properties();
            properties.load(getClass().getResourceAsStream("/properties/database.properties"));

            String url = properties.getProperty(KeyConst.DB_URL.getName());
            String dbName = properties.getProperty(KeyConst.DB_NAME.getName());
            String userName = properties.getProperty(KeyConst.DB_USER_NAME.getName());
            String password = properties.getProperty(KeyConst.DB_PASSWORD.getName());

            if (url == null || dbName == null || userName == null || password == null) {
                throw new Exception("must select a valid database and server credentials.");
            }

            Class.forName(driver).newInstance();

            // test the connection
            DriverManager.getConnection(url + dbName, userName, password);

            DBConnector.url = url;
            DBConnector.dbName = dbName;
            DBConnector.userName = userName;
            DBConnector.password = password;

        } catch (IOException ioe) {
            connectionIsGood = false;
            ioe.printStackTrace();
        } catch (SQLException sqle) {
            connectionIsGood = false;
            sqle.printStackTrace();
        } catch (Exception e) {
            connectionIsGood = false;
            e.printStackTrace();
        }

        return connectionIsGood;
    }

    public static void connectToDB() {
        try {
//            String url = "jdbc:mysql://localhost:3306/";
//            String dbName = "si";
//            String userName = "admin";
//            String password = "admin";

            Class.forName(driver).newInstance();

            connection = DriverManager.getConnection(url + dbName, userName, password);

        } catch (SQLException sqle) {
            // todo: change this to pop mesages
            System.err.print("Invalid Sql Exception: ");
            System.err.println(sqle.getMessage());
        } catch (Exception e) {
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

    public static void setDbName(String dbName) {
        DBConnector.dbName = dbName;
    }

    public static void setUrl(String url) {
        DBConnector.url = url;
    }
}
