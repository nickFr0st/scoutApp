package util;

import constants.KeyConst;
import scout.dbObjects.User;

import java.io.IOException;
import java.sql.*;
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
    private String dataBase = "jdbc:mysql://localhost:3306/";
    private Properties properties;

    public DBConnector() {
        try {
            properties = new Properties();
            properties.load(getClass().getResourceAsStream("/properties/database.properties"));
        } catch (IOException ioe) {
            // do nothing for now
        }
    }

    static {
        driver = "com.mysql.jdbc.Driver";
    }

    public boolean checkForDBConnection() {
        boolean connectionIsGood = true;

        try {
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

    public int connectToDB(String name, String rootUser, String rootPassword) {
        try {
            Class.forName(driver).newInstance();
            connection = DriverManager.getConnection(dataBase + name, rootUser, rootPassword);

        } catch (SQLException sqle) {
            return sqle.getErrorCode();
        } catch (Exception ignore) {}

        resetProperties(name, rootUser, rootPassword);
        return 0;
    }

    private void resetProperties(String name, String rootUser, String rootPassword) {
        url = dataBase;
        dbName = name;
        userName = rootUser;
        password = rootPassword;

        properties.setProperty(KeyConst.DB_URL.getName(), dataBase);
        properties.setProperty(KeyConst.DB_NAME.getName(), name);
        properties.setProperty(KeyConst.DB_USER_NAME.getName(), rootUser);
        properties.setProperty(KeyConst.DB_PASSWORD.getName(), rootPassword);

        Util.saveProperties(properties, getClass().getResource("/properties/database.properties").toString());
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

    public int createDatabase(String name, String rootUser, String rootPassword) {
        try {
            Statement statement = setupConnection(rootUser, "", rootPassword);

            statement.executeUpdate("CREATE DATABASE " + name);
            connection = DriverManager.getConnection(dataBase + name, rootUser, rootPassword);
            buildDataBase();

        } catch (SQLException sqle) {
            return sqle.getErrorCode();
        } catch (Exception ignore) {}

        resetProperties(name, rootUser, rootPassword);
        return 0;
    }

    private Statement setupConnection(String rootUser, String dbName, String rootPassword) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        Class.forName(driver).newInstance();
        connection = DriverManager.getConnection(dataBase + dbName, rootUser, rootPassword);
        return connection.createStatement();
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

    private int getNextId(String tableName) {
        int id = 0;
        try {
            Statement statement = setupConnection(userName, dbName, password);
            ResultSet rs = statement.executeQuery("SELECT * FROM " + tableName);

            while(rs.next()) {
                int tempId = rs.getInt("id");
                if (tempId > id) {
                    id = tempId;
                }
            }

        } catch (Exception e) {
            // table does not have id field
            return -1;
        }

        return id + 1;
    }

    public void insert(String tableName, String[] tableColumnNames, String[] columnValues) {
        if (tableColumnNames == null || tableColumnNames.length < 1) {
            return;
        }

        try {
            Statement statement = setupConnection(userName, dbName, password);
            int id = getNextId(tableName);


            StringBuilder query = new StringBuilder();
            query.append("INSERT INTO " + tableName).append(" (");

            query.append("id");
            for (String s : tableColumnNames) {
                query.append(",").append(s);
            }
            query.append(") VALUES (");

            query.append("'").append(id).append("'");
            for (String s : columnValues) {
                query.append(",").append("'").append(s).append("'");
            }
            query.append(");");

            statement.executeUpdate(query.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User getUser() {
        try {
            Statement statement = setupConnection(userName, dbName, password);
            ResultSet rs = statement.executeQuery("SELECT * FROM user");

            User user = new User();
            if (rs.next()) {
                user.setId(rs.getInt(KeyConst.USER_ID.getName()));
                user.setTroopLeader(rs.getString(KeyConst.USER_TROOP_LEADER.getName()));
                user.setTroopNumber(rs.getString(KeyConst.USER_TROOP_NUMBER.getName()));
                user.setCouncil(rs.getString(KeyConst.USER_COUNCIL.getName()));
                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
