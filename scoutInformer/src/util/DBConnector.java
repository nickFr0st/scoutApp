package util;

import constants.KeyConst;

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
            connection = DriverManager.getConnection(url + dbName, userName, password);

            DBConnector.url = url;
            DBConnector.dbName = dbName;
            DBConnector.userName = userName;
            DBConnector.password = password;

        } catch (Exception e) {
            return false;
        }

        return true;
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

    public static String getDBName() {
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
        return createStatement();
    }

    public Statement createStatement() throws SQLException {
        return connection.createStatement();
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

    public static void setDBName(String dbNameText) {
        dbName = dbNameText;
    }

    private void buildDataBase() throws SQLException {
        Statement statement = createStatement();
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
                " imgPath VARCHAR(255) NULL," +
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

        String tableRequirement = "CREATE TABLE requirement " +
                "(id INT NOT NULL," +
                " name VARCHAR(45) NOT NULL," +
                " description BLOB NOT NULL," +
                " typeId INT NOT NULL," +
                " parentId INT NOT NULL," +
                " PRIMARY KEY (id))";
        statement.addBatch(tableRequirement);

        // Insert Advancements
        int id = 1;
        String insert = "INSERT INTO advancement VALUES( " + id++ + ",'New Scout', '/images/advancements/new_scout.png')";
        statement.addBatch(insert);
        insert = "INSERT INTO advancement VALUES( " + id++ + ",'Tenderfoot', '/images/advancements/tenderfoot.png')";
        statement.addBatch(insert);
        insert = "INSERT INTO advancement VALUES( " + id++ + ",'Second Class', '/images/advancements/second_class.png')";
        statement.addBatch(insert);
        insert = "INSERT INTO advancement VALUES( " + id++ + ",'First Class', '/images/advancements/first_class.png')";
        statement.addBatch(insert);
        insert = "INSERT INTO advancement VALUES( " + id++ + ",'Star', '/images/advancements/star.png')";
        statement.addBatch(insert);
        insert = "INSERT INTO advancement VALUES( " + id++ + ",'Life', '/images/advancements/life.png')";
        statement.addBatch(insert);
        insert = "INSERT INTO advancement VALUES( " + id + ",'Eagle', '/images/advancements/eagle.png')";
        statement.addBatch(insert);

        // Insert Requirements
        //  New Scout
        id = 1;
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'1', 'Meet the age requirements. Be a boy who is 11 years old, or one who has completed the fifth grade or earned the Arrow of Light Award and is at least 10 years old, but is not yet 18 years old.', 1, 1)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'2', 'Find a Scout troop near your home.', 1, 1)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'3', 'Complete a Boy Scout application and health history signed by your parent or guardian.', 1, 1)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'4', 'Repeat the Pledge of Allegiance.', 1, 1)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'5', 'Demonstrate the Scout sign, salute, and handshake.', 1, 1)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'6', 'Demonstrate tying the square knot (a joining knot).', 1, 1)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'7', 'Understand and agree to live by the Scout Oath, Scout Law, motto, slogan, and the Outdoor Code.', 1, 1)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'8', 'Describe the Scout badge.', 1, 1)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'9', 'Complete the pamphlet exercises. With your parent or guardian, complete the exercises in the pamphlet \"How to Protect Your Children from Child Abuse: A Parents Guide\".', 1, 1)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id + ",'10', 'Participate in a Scoutmaster conference. Turn in your Boy Scout application and health history form signed by your parent or guardian, then participate in a Scoutmaster conference.', 1, 1)";
        statement.addBatch(insert);

        statement.executeBatch();
    }
}
