package util;

import constants.KeyConst;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Created by nmalloch on 6/5/2014
 */
public class DBConnector {
    public static Connection connection;
    private static String driver;
    private static String dbName;
    private static String url;
    private static String userName;
    private static String password;
    String dataBase = "jdbc:mysql://localhost:3306/";

    static {
        driver = "com.mysql.jdbc.Driver";
    }

    public boolean checkForDBConnection() {
        boolean connectionIsGood = true;

        try {
            Properties properties = new Properties();
            properties.load(getClass().getResourceAsStream("/properties/database.properties"));

            String url = properties.getProperty(KeyConst.DB_URL.getName());
            String dbName = properties.getProperty(KeyConst.DB_NAME.getName());
            String userName = properties.getProperty(KeyConst.DB_USER_NAME.getName());
            String password = properties.getProperty(KeyConst.DB_PASSWORD.getName());

            if (Util.isEmpty(url) || Util.isEmpty(dbName) || userName == null || password == null) {
                return false;
            }

            Class.forName(driver).newInstance();

            // test the connection
            DriverManager.getConnection(url + dbName, userName, password);

            DBConnector.url = url;
            DBConnector.dbName = dbName;
            DBConnector.userName = userName;
            DBConnector.password = password;

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

    public boolean connectToDB(String name, String rootUser, String rootPassword) {
        try {
            Class.forName(driver).newInstance();
            connection = DriverManager.getConnection(dataBase + name, rootUser, rootPassword);

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        DBConnector.url = dataBase;
        DBConnector.dbName = name;
        DBConnector.userName = rootUser;
        DBConnector.password = rootPassword;

        return true;
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

    public boolean createDatabase(String name, String rootUser, String rootPassword) {
        try {
            Class.forName(driver).newInstance();
            connection = DriverManager.getConnection(dataBase, rootUser, rootPassword);
            Statement statement = connection.createStatement();

            statement.executeUpdate("CREATE DATABASE " + name);
            connection = DriverManager.getConnection(dataBase + name, rootUser, rootPassword);
            buildDataBase();

        } catch (SQLException sqle) {
            if (sqle.getErrorCode() == 1045) {
                // send message to user of bad password
            }
            sqle.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        DBConnector.url = dataBase;
        DBConnector.dbName = name;
        DBConnector.userName = rootUser;
        DBConnector.password = rootPassword;

        return true;
    }

    private void buildDataBase() throws SQLException {
        Statement statement = connection.createStatement();
        StringBuilder query = new StringBuilder();

        // Create Tables
        query.append("CREATE TABLE user " +
                "(id INT NOT NULL," +
                " troop VARCHAR(45) NULL," +
                " council VARCHAR(255) NULL," +
                " troopLeader VARCHAR(255)," +
                " PRIMARY KEY (id)) ");

        // Insert Advancements

        // Insert Requirements

        statement.executeUpdate(query.toString());
    }
}
