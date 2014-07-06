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
            Class.forName(driver).newInstance();

            connection = DriverManager.getConnection(url + dbName, userName, password);

        } catch (SQLException sqle) {
            System.err.print("Exception in connectToDB(): ");
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

    public void resetProperties(String name, String rootUser, String rootPassword) {
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

    public String getDBName() {
        return dbName;
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
                " imgPath VARCHAR(255) NOT NULL," +
                " requirementsLink VARCHAR(255) NULL," +
                " PRIMARY KEY (id))";
        statement.addBatch(tableAdvancement);

        String tableMeritBadge = "CREATE TABLE meritBadge " +
                "(id INT NOT NULL," +
                " name VARCHAR(225) NOT NULL," +
                " imgPath VARCHAR(255) NOT NULL," +
                " requirementsLink VARCHAR(255) NULL," +
                " requiredForEagle TINYINT NOT NULL," +
                " PRIMARY KEY (id))";
        statement.addBatch(tableMeritBadge);

        // Insert Advancements
        String insert = "INSERT INTO advancement VALUES(1,'New Scout', '/images/advancement/new_scout.png', 'http://www.meritbadge.org/wiki/index.php/Scout_Badge')";
        statement.addBatch(insert);
        insert = "INSERT INTO advancement VALUES(2,'Tenderfoot', '/images/advancement/tenderfoot.png', 'http://www.meritbadge.org/wiki/index.php/Tenderfoot')";
        statement.addBatch(insert);
        insert = "INSERT INTO advancement VALUES(3,'Second Class', '/images/advancement/second_class.png', 'http://www.meritbadge.org/wiki/index.php/Second_Class')";
        statement.addBatch(insert);
        insert = "INSERT INTO advancement VALUES(4,'First Class', '/images/advancement/first_class.png', 'http://www.meritbadge.org/wiki/index.php/First_Class')";
        statement.addBatch(insert);
        insert = "INSERT INTO advancement VALUES(5,'Star', '/images/advancement/star.png', 'http://www.meritbadge.org/wiki/index.php/Star_Scout')";
        statement.addBatch(insert);
        insert = "INSERT INTO advancement VALUES(6,'Life', '/images/advancement/life.png', 'http://www.meritbadge.org/wiki/index.php/Life_Scout')";
        statement.addBatch(insert);
        insert = "INSERT INTO advancement VALUES(7,'Eagle', '/images/advancement/eagle.png', 'http://www.meritbadge.org/wiki/index.php/Eagle_Scout')";
        statement.addBatch(insert);

        // Insert Requirements

        statement.executeBatch();
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
                user.setTroopName(rs.getString(KeyConst.USER_TROOP_NAME.getName()));
                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateById(int id, String tableName, String[] tableColumnNames, String[] columnValues) {
        if (tableColumnNames == null || tableColumnNames.length < 1) {
            return;
        }

        try {
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
}
