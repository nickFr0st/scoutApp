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
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'10', 'Participate in a Scoutmaster conference. Turn in your Boy Scout application and health history form signed by your parent or guardian, then participate in a Scoutmaster conference.', 1, 1)";
        statement.addBatch(insert);

        //  Tenderfoot
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'1', 'Present yourself to your leader, properly dressed, before going on an overnight camping trip. Show the camping gear you will use. Show the right way to pack and carry it.', 1, 2)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'2', 'Spend at least one night on a patrol or troop campout. Sleep in a tent you have helped pitch.', 1, 2)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'3', 'On the campout, assist in preparing and cooking one of your patrols meals. Tell why it is important for each patrol member to share in meal preparation and cleanup, and explain the importance of eating together.', 1, 2)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'4a', 'Demonstrate how to whip and fuse the ends of a rope.', 1, 2)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'4b', 'Demonstrate you know how to tie the following knots and tell what their uses are: two half hitches and the taut-line hitch.', 1, 2)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'4c', 'Using the EDGE method, teach another person how to tie the square knot.', 1, 2)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'5', 'Explain the rules of safe hiking, both on the highway and cross-country, during the day and at night. Explain what to do if you are lost.', 1, 2)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'6', 'Demonstrate how to display, raise, lower, and fold the American flag.', 1, 2)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'7', 'Repeat from memory and explain in your own words the Scout Oath, Law, motto, and slogan.', 1, 2)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'8', 'Know your patrol name, give the patrol yell, and describe your patrol flag.', 1, 2)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'9', 'Explain the importance of the buddy system as it relates to your personal safety on outings and in your neighborhood. Describe what a bully is and how you should respond to one.', 1, 2)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'10a', 'Record your best in the following tests:\n" +
                "Current results\n" +
                "Push-ups ________\n" +
                "Pull-ups ________\n" +
                "Sit-ups ________\n" +
                "Standing long jump (______ ft. ______ in.)\n" +
                "1/4 mile walk/run _____________\n" +
                "30 days later\n" +
                "Push-ups ________\n" +
                "Pull-ups ________\n" +
                "Sit-ups ________\n" +
                "Standing long jump (______ ft. ______ in.)\n" +
                "1/4 mile walk/run _____________', 1, 2)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'10b', 'Show improvement in the activities listed in requirement 10a after practicing for 30 days.', 1, 2)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'11', 'Identify local poisonous plants; tell how to treat for exposure to them.', 1, 2)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'12a', 'Demonstrate how to care for someone who is choking.', 1, 2)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'12b', 'Show first aid for the following:\n" +
                "  - Simple cuts and scrapes\n" +
                "  - Blisters on the hand and foot\n" +
                "  - Minor (thermal/heat) burns or scalds (superficial, or first degree)\n" +
                "  - Bites or stings of insects and ticks\n" +
                "  - Venomous snakebite\n" +
                "  - Nosebleed\n" +
                "  - Frostbite and sunburn', 1, 2)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'13', 'Demonstrate Scout spirit by living the Scout Oath and Scout Law in your everyday life. Discuss four specific examples of how you have lived the points of the Scout Law in your daily life.', 1, 2)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'14', 'Participate in a Scoutmaster conference.', 1, 2)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'15', 'Complete your board of review.', 1, 2)";
        statement.addBatch(insert);

        //  Second Class
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'1a', 'Demonstrate how a compass works and how to orient a map. Explain what map symbols mean.', 1, 3)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'1b', 'Using a compass and a map together, take a five-mile hike (or 10 miles by bike) approved by your adult leader and your parent or guardian.*', 1, 3)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'2', 'Discuss the principles of Leave No Trace.', 1, 3)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'3a', 'Since joining, have participated in five separate troop/patrol activities (other than troop/patrol meetings), two of which included camping overnight.', 1, 3)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'3b', 'On one of these campouts, select your patrol site and sleep in a tent that you pitched. Explain what factors you should consider when choosing a patrol site and where to pitch a tent.', 1, 3)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'3c', 'Demonstrate proper care, sharpening, and use of the knife, saw, and ax, and describe when they should be used.', 1, 3)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'3d', 'Use the tools listed in requirement 3c to prepare tinder, kindling, and fuel for a cooking fire.', 1, 3)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'3e', 'Explain when it is appropriate to use a cooking fire. At an approved outdoor location and at an approved time, and using the tinder, kindling, and fuel wood from requirement 3d, demonstrate how to build a fire; light the fire, unless prohibited by local fire restrictions. After allowing the flames to burn safely for at least two minutes, safely extinguish the flames with minimal impact to the fire site.', 1, 3)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'3f', 'Explain when it is appropriate to use a lightweight stove or propane stove. Set up a lightweight stove or propane stove; light the stove, unless prohibited by local fire restrictions. Describe the safety procedures for using these types of stoves.', 1, 3)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'3g', 'On one campout, plan and cook one hot breakfast or lunch, selecting foods from the MyPlate food guide or the current USDA nutrition model. Explain the importance of good nutrition. Tell how to transport, store, and prepare the foods you selected.', 1, 3)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'4', 'Participate in a flag ceremony for your school, religious institution, chartered organization, community, or troop activity. Explain to your leader what respect is due the flag of the United States.', 1, 3)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'5', 'Participate in an approved (minimum of one hour) service project.', 1, 3)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'6', 'Identify or show evidence of at least 10 kinds of wild animals (birds, mammals, reptiles, fish, mollusks) found in your community.', 1, 3)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'7a', 'Show what to do for \"hurry\" cases of stopped breathing, serious bleeding, and ingested poisoning.', 1, 3)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'7b', 'Prepare a personal first aid kit to take with you on a hike.', 1, 3)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'7c', 'Demonstrate first aid for the following:\n" +
                "  - Object in the eye\n" +
                "  - Bite of a suspected rabid animal\n" +
                "  - Puncture wounds from a splinter, nail, and fishhook\n" +
                "  - Serious burns (partial thickness, or second-degree)\n" +
                "  - Heat exhaustion\n" +
                "  - Shock\n" +
                "  - Heatstroke, dehydration, hypothermia, and hyperventilation', 1, 3)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'8a', 'Tell what precautions must be taken for a safe swim.', 1, 3)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'8b', 'Demonstrate your ability to jump feet first into water over your head in depth, level off and swim 25 feet on the surface, stop, turn sharply, resume swimming, then return to your starting place.', 1, 3)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'8c', 'Demonstrate water rescue methods by reaching with your arm or leg, by reaching with a suitable object, and by throwing lines and objects. Explain why swimming rescues should not be attempted when a reaching or throwing rescue is possible, and explain why and how a rescue swimmer should avoid contact with the victim.', 1, 3)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'9a', 'Participate in a school, community, or troop program on the dangers of using drugs, alcohol, and tobacco, and other practices that could be harmful to your health. Discuss your participation in the program with your family, and explain the dangers of substance addictions.', 1, 3)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'9b', 'Explain the three R''s of personal safety and protection.', 1, 3)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'10', 'Earn an amount of money agreed upon by you and your parent, then save at least 50 percent of that money.', 1, 3)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'11', 'Demonstrate Scout spirit by living the Scout Oath and Scout Law in your everyday life. Discuss four specific examples (different from those used for Tenderfoot requirement 13) of how you have lived the points of the Scout Law in your daily life.', 1, 3)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'12', 'Participate in a Scoutmaster conference.', 1, 3)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'13', 'Complete your board of review.', 1, 3)";
        statement.addBatch(insert);

        //  First Class
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'1', 'Demonstrate how to find directions during the day and at night without using a compass.', 1, 4)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'2', 'Using a map and compass, complete an orienteering course that covers at least one mile and requires measuring the height and/or width of designated items (tree, tower, canyon, ditch, etc.)', 1, 4)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'3', 'Since joining, have participated in 10 separate troop/patrol activities (other than troop/patrol meetings), three of which included camping overnight. Demonstrate the principles of Leave No Trace on these outings.', 1, 4)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'4a', 'Help plan a patrol menu for one campout that includes at least one breakfast, one lunch, and one dinner, and that requires cooking at least two of the meals. Tell how the menu includes the foods from the MyPlate food guide or the current USDA nutrition model and meets nutritional needs.', 1, 4)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'4b', 'Using the menu planned in requirement 4a, make a list showing the cost and food amounts needed to feed three or more boys and secure the ingredients.', 1, 4)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'4c', 'Tell which pans, utensils, and other gear will be needed to cook and serve these meals.', 1, 4)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'4d', 'Explain the procedures to follow in the safe handling and storage of fresh meats, dairy products, eggs, vegetables, and other perishable food products. Tell how to properly dispose of camp garbage, cans, plastic containers, and other rubbish.', 1, 4)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'4e', 'On one campout, serve as your patrols cook. Supervise your assistant(s) in using a stove or building a cooking fire. Prepare the breakfast, lunch, and dinner planned in requirement 4a. Lead your patrol in saying grace at the meals and supervise cleanup.', 1, 4)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'5', 'Visit and discuss with a selected individual approved by your leader (elected official, judge, attorney, civil servant, principal, teacher) your constitutional rights and obligations as a U.S. citizen.', 1, 4)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'6', 'Identify or show evidence of at least 10 kinds of native plants found in your community.', 1, 4)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'7a', 'Discuss when you should and should not use lashings. Then demonstrate tying the timber hitch and clove hitch and their use in square, shear, and diagonal lashings by joining two or more poles or staves together.', 1, 4)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'7b', 'Use lashing to make a useful camp gadget.', 1, 4)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'8a', 'Demonstrate tying the bowline knot and describe several ways it can be used.', 1, 4)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'8b', 'Demonstrate bandages for a sprained ankle and for injuries on the head, the upper arm, and the collarbone.', 1, 4)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'8c', 'Show how to transport by yourself, and with one other person, a person:\n" +
                "   - From a smoke-filled room\n" +
                "   - With a sprained ankle, for at least 25 yards', 1, 4)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'8d', 'Tell the five most common signs of a heart attack. Explain the steps (procedures) in cardiopulmonary resuscitation (CPR).', 1, 4)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'9a', 'Tell what precautions must be taken for a safe trip afloat.', 1, 4)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'9b', 'Successfully complete the BSA swimmer test.*', 1, 4)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'9c', 'With a helper and a practice victim, show a line rescue both as tender and as rescuer. (The practice victim should be approximately 30 feet from shore in deep water.)', 1, 4)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'10', 'Tell someone who is eligible to join Boy Scouts, or an inactive Boy Scout, about your troops activities. Invite him to a troop outing, activity, service project, or meeting. Tell him how to join, or encourage the inactive Boy Scout to become active.', 1, 4)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'11', 'Describe the three things you should avoid doing related to the use of the Internet. Describe a cyberbully and how you should respond to one.', 1, 4)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'12', 'Demonstrate Scout spirit by living the Scout Oath and Scout Law in your everyday life. Discuss four specific examples (different from those used in Tenderfoot requirement 13 and Second Class requirement 11) of how you have lived the points of the Scout Law in your daily life.', 1, 4)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'13', 'Participate in a Scoutmaster conference.', 1, 4)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'14', 'Complete your board of review.', 1, 4)";
        statement.addBatch(insert);

        //  Star
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'1', 'Be active in your unit (and patrol if you are in one) for at least four months as a First Class Scout.', 1, 5)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'2', 'Demonstrate Scout spirit by living the Scout Oath and Scout Law in your everyday life.', 1, 5)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'3', 'Earn six merit badges, including any four from the required list for Eagle.\n" +
                "___________________________________(required for Eagle)*\n" +
                "___________________________________(required for Eagle)*\n" +
                "___________________________________(required for Eagle)*\n" +
                "___________________________________(required for Eagle)*\n" +
                "___________________________________\n" +
                "___________________________________', 1, 5)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'4', 'While a First Class Scout, take part in service projects totaling at least six hours of work. These projects must be approved by your Scoutmaster.', 1, 5)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'5', 'While a First Class Scout, serve actively in your unit for four months in one or more of the following positions of responsibility (or carry out a unit leader-assigned leadership project to help your unit): (see boy scout handbook for available positions)', 1, 5)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'6', 'Take part in a Scoutmaster conference.', 1, 5)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'7', 'Complete your board of review.', 1, 5)";
        statement.addBatch(insert);

        //  Life
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'1', 'Be active in your unit (and patrol if you are in one) for at least six months as a Star Scout.', 1, 6)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'2', 'Demonstrate Scout spirit by living the Scout Oath and Scout Law in your everyday life.', 1, 6)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'3', 'Earn five more merit badges (so that you have 11 in all), including any three more from the required list for Eagle.\n" +
                "___________________________________ (required for Eagle)*\n" +
                "___________________________________ (required for Eagle)*\n" +
                "___________________________________ (required for Eagle)*\n" +
                "___________________________________\n" +
                "___________________________________', 1, 6)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'4', 'While a Star Scout, take part in service projects totaling at least six hours of work. These projects must be approved by your Scoutmaster.', 1, 6)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'5', 'While a Star Scout, serve actively in your unit for six months in one or more of the positions of responsibility listed in requirement 5 for Star Scout (or carry out a Scoutmaster-assigned leadership project to help the troop).', 1, 6)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'6', 'While a Star Scout, use the EDGE method to teach another Scout (preferably younger than you) the skills from ONE of the following seven choices, so that he is prepared to pass those requirements to his unit leader''s satisfaction.\n" +
                "  a.  Second Class: 7a and 7c (first aid)\n" +
                "  b.  Second Class: 1a (outdoor skills)\n" +
                "  c.  Second Class: 3c, 3d, 3e, and 3f (cooking/camping)\n" +
                "  d.  First Class: 8a, 8b, 8c, and 8d (first aid)\n" +
                "  e.  First Class: 1, 7a, and 7b (outdoor skills)\n" +
                "  f.  First Class: 4a, 4b, and 4d (cooking/camping)\n" +
                "  g.  Three requirements from one of the required for Eagle merit badges, as approved by your unit leader.', 1, 6)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'7', 'Take part in a Scoutmaster conference.', 1, 6)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'8', 'Complete your board of review.', 1, 6)";
        statement.addBatch(insert);

        //  Eagle
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'1', 'Be active in your troop, team, crew, or ship for a period of at least six months after you have achieved the rank of Life Scout.', 1, 7)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'2', 'Demonstrate that you live by the principles of the Scout Oath and Scout Law in your daily life. List on your Eagle Scout Rank Application the names of individuals who know you personally and would be willing to provide a recommendation on your behalf, including parents/guardians, religious, educational, and employer references.', 1, 7)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'3', 'Earn a total of 21 merit badges (10 more than you already have), including the following:\n" +
                "  a. First Aid\n" +
                "  b. Citizenship in the Community\n" +
                "  c. Citizenship in the Nation\n" +
                "  d. Citizenship in the World\n" +
                "  e. Communication\n" +
                "  f. Cooking\n" +
                "  g. Personal Fitness\n" +
                "  h. Emergency Preparedness OR Lifesaving*\n" +
                "  i. Environmental Science OR Sustainability*\n" +
                "  j. Personal Management\n" +
                "  k. Swimming OR Hiking OR Cycling*\n" +
                "  l. Camping, and\n" +
                "  m. Family Life\n" +
                "* You must choose only one merit badge listed in items h, i, and k. If you have earned more than one of the badges listed in items h, i, and/or k, choose one and list the remaining badges to make your total of 21.', 1, 7)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'4', 'While a Life Scout, serve actively in your unit for a period of six months in one or more of the following positions of responsibility. List only those positions served after your Life board of review date. (see boy scout handbook for available positions)', 1, 7)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'5', 'While a Life Scout, plan, develop, and give leadership to others in a service project helpful to any religious institution, any school, or your community. (The project should benefit an organization other than Boy Scouting.) The project proposal must be approved by the organization benefiting from the effort, your unit leader and unit committee, and the council or district before you start. You must use the Eagle Scout Leadership Service Project Workbook, BSA publication No. 521-927, in meeting this requirement. (To learn more about the Eagle Scout service project, see the Guide To Advancement, topics 9.0.2.0 through 9.0.2.15.)', 1, 7)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'6', 'Take part in a unit leader conference.', 1, 7)";
        statement.addBatch(insert);
        insert = "INSERT INTO requirement VALUES( " + id++ + ",'7', 'Successfully complete an Eagle Scout board of review. In preparation for your board of review, prepare and attach to your Eagle Scout Rank Application a statement of your ambitions and life purpose and a listing of positions held in your religious institution, school, camp, community, or other organizations, during which you demonstrated leadership skills. Include honors and awards received during this service.', 1, 7)";
        statement.addBatch(insert);

        statement.executeBatch();
    }
}
