package util;

import constants.KeyConst;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * Created by nmalloch on 6/5/2014
 */
public class DBConnector {
    private Connection connection;
    private String driver;
    private String dbPath;
    private Properties properties;

    {
        dbPath = "jdbc:mysql://localhost:3306/";
        driver = "com.mysql.jdbc.Driver";
    }

    public DBConnector() {
        try {
            properties = new Properties();
            properties.load(getClass().getResourceAsStream("/properties/database.properties"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
            // do nothing for now
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public String getDriver() {
        return driver;
    }

    public String getDbName() {
        return properties.getProperty(KeyConst.DB_NAME.getName());
    }

    public String getUrl() {
        return properties.getProperty(KeyConst.DB_URL.getName());
    }

    public String getPassword() {
        return properties.getProperty(KeyConst.DB_PASSWORD.getName());
    }

    public boolean checkForDataBaseConnection() {
        try {
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
            connection = DriverManager.getConnection(url + dbName, userName, password);

        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public int connectToDB(String name, String rootUser, String rootPassword) {
        try {
            Class.forName(driver).newInstance();
            connection = DriverManager.getConnection(dbPath + name, rootUser, rootPassword);

        } catch (SQLException sqle) {
            return sqle.getErrorCode();
        } catch (Exception ignore) {}

        resetProperties(name, rootUser, rootPassword);
        return 0;
    }

    public void resetProperties(String name, String rootUser, String rootPassword) {
        properties.setProperty(KeyConst.DB_URL.getName(), dbPath);
        properties.setProperty(KeyConst.DB_NAME.getName(), name);
        properties.setProperty(KeyConst.DB_USER_NAME.getName(), rootUser);
        properties.setProperty(KeyConst.DB_PASSWORD.getName(), rootPassword);

        Util.saveProperties(properties, getClass().getResource("/properties/database.properties").toString());
    }

    public int createDatabase(String name, String rootUser, String rootPassword) {
        try {
            Statement statement = setupConnection(rootUser, "", rootPassword);

            statement.executeUpdate("CREATE DATABASE " + name);
            connection = DriverManager.getConnection(dbPath + name, rootUser, rootPassword);
            buildDataBase();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return sqle.getErrorCode();
        } catch (Exception e) {
            e.printStackTrace();
        }

        resetProperties(name, rootUser, rootPassword);
        return 0;
    }

    private Statement setupConnection(String rootUser, String dbName, String rootPassword) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        Class.forName(driver).newInstance();
        connection = DriverManager.getConnection(dbPath + dbName, rootUser, rootPassword);
        return createStatement();
    }

    public Statement createStatement() throws SQLException {
        return connection.createStatement();
    }

    private int getNextId(String tableName) {
        int id = 0;
        try {
            String dbName = properties.getProperty(KeyConst.DB_NAME.getName());
            String userName = properties.getProperty(KeyConst.DB_USER_NAME.getName());
            String password = properties.getProperty(KeyConst.DB_PASSWORD.getName());

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
            String dbName = properties.getProperty(KeyConst.DB_NAME.getName());
            String userName = properties.getProperty(KeyConst.DB_USER_NAME.getName());
            String password = properties.getProperty(KeyConst.DB_PASSWORD.getName());

            Statement statement = setupConnection(userName, dbName, password);
            int id = getNextId(tableName);


            StringBuilder query = new StringBuilder();
            query.append("INSERT INTO ").append(tableName).append(" (");

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

    public void updateById(int id, String tableName, String[] tableColumnNames, String[] columnValues) {
        if (tableColumnNames == null || tableColumnNames.length < 1) {
            return;
        }

        try {
            String dbName = properties.getProperty(KeyConst.DB_NAME.getName());
            String userName = properties.getProperty(KeyConst.DB_USER_NAME.getName());
            String password = properties.getProperty(KeyConst.DB_PASSWORD.getName());

            Statement statement = setupConnection(userName, dbName, password);
            StringBuilder query = new StringBuilder();

            query.append("UPDATE ").append(tableName);
            query.append(" SET ");

            boolean first = true;
            for (int i = 0; i < tableColumnNames.length; ++i) {
                if (first) {
                    query.append(tableColumnNames[i]).append("=").append("'").append(columnValues[i]).append("'");
                    first = false;
                } else {
                    query.append(",").append(tableColumnNames[i]).append("=").append("'").append(columnValues[i]).append("'");
                }
            }

            query.append(" WHERE id = ").append(id);

            statement.executeUpdate(query.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void buildDataBase() throws SQLException {
        Statement statement = createStatement();

        // Create Tables
        String tableUser = "CREATE TABLE user " +
                "(id INT NOT NULL," +
                " troop VARCHAR(45) NULL," +
                " council VARCHAR(255) NULL," +
                " troopLeader VARCHAR(255) NULL," +
                " troopName VARCHAR(255) NULL," +
                " PRIMARY KEY (id))";
        statement.addBatch(tableUser);

        String tableAdvancement = "CREATE TABLE advancement " +
                "(id INT NOT NULL," +
                " name VARCHAR(225) NOT NULL," +
                " imgPath VARCHAR(255) NULL," +
                " PRIMARY KEY (id))";
        statement.addBatch(tableAdvancement);

        String tableOtherAward = "CREATE TABLE otherAward " +
                "(id INT NOT NULL," +
                " name VARCHAR(225) NOT NULL," +
                " imgPath VARCHAR(255) NULL," +
                " PRIMARY KEY (id))";
        statement.addBatch(tableOtherAward);

        String tableMeritBadge = "CREATE TABLE meritBadge " +
                "(id INT NOT NULL," +
                " name VARCHAR(225) NOT NULL," +
                " imgPath VARCHAR(255) NOT NULL," +
                " requiredForEagle TINYINT NOT NULL," +
                " PRIMARY KEY (id))";
        statement.addBatch(tableMeritBadge);

        String tableRequirement = "CREATE TABLE requirement " +
                "(id INT NOT NULL," +
                " name VARCHAR(45) NOT NULL," +
                " description BLOB NOT NULL," +
                " typeId INT NOT NULL," +
                " parentId INT NOT NULL," +
                " PRIMARY KEY (id))";
        statement.addBatch(tableRequirement);

        String tableCounselor = "CREATE TABLE counselor " +
                "(id INT NOT NULL," +
                " badgeId INT NOT NULL," +
                " name VARCHAR(90) NOT NULL," +
                " phoneNumber VARCHAR(20) NOT NULL," +
                " PRIMARY KEY (id))";
        statement.addBatch(tableCounselor);

        String tableBoyScout = "CREATE TABLE boyScout " +
                "(id INT NOT NULL," +
                " name VARCHAR(90) NOT NULL," +
                " birthDate DATE NOT NULL," +
                " advancementId INT NOT NULL," +
                " typeId INT NOT NULL," +
                " PRIMARY KEY (id))";
        statement.addBatch(tableBoyScout);

        String tableContact = "CREATE TABLE contact " +
                "(id INT NOT NULL," +
                " scoutId INT NOT NULL," +
                " typeId INT NOT NULL," +
                " name VARCHAR(90) NOT NULL," +
                " relation VARCHAR(90) NOT NULL," +
                " data VARCHAR(255) NOT NULL," +
                " PRIMARY KEY (id))";
        statement.addBatch(tableContact);

        String tableScoutAwards = "CREATE TABLE scoutAwards " +
                "(id INT NOT NULL," +
                " scoutId INT NOT NULL," +
                " awardId INT NOT NULL," +
                " reqIdList VARCHAR(255) NULL," +
                " PRIMARY KEY (id))";
        statement.addBatch(tableScoutAwards);

        String tableScoutMeritBadges = "CREATE TABLE ScoutMeritBadges " +
                "(id INT NOT NULL," +
                " scoutId INT NOT NULL," +
                " meritBadgeId INT NOT NULL," +
                " reqIdList VARCHAR(255) NULL," +
                " PRIMARY KEY (id))";
        statement.addBatch(tableScoutMeritBadges);

        statement.executeBatch();
    }
}
