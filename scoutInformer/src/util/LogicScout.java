package util;

import constants.KeyConst;
import scout.dbObjects.Scout;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nathanael on 10/29/2014
 */
public class LogicScout {
    private static DBConnector connector;

    static {
        connector = new DBConnector();
    }


    public static List<String> getNameList() {
        if (!connector.checkForDataBaseConnection()) {
            return null;
        }

        List<String> ScoutNameList = new ArrayList<String>();

        try {
            Statement statement = connector.createStatement();
            ResultSet rs = statement.executeQuery("SELECT name FROM scout ORDER BY name");

            while(rs.next()) {
                ScoutNameList.add(rs.getString(KeyConst.SCOUT_NAME.getName()));
            }

        } catch (Exception e) {
            return null;
        }

        return ScoutNameList;
    }

    public static Scout findByName(String name) {
        if (!connector.checkForDataBaseConnection()) {
            return null;
        }

        Scout scout = null;

        try {
            Statement statement = connector.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM scout WHERE name LIKE '" + name + "'");

            if (rs.next()) {
                scout = new Scout();
                scout.setId(rs.getInt(KeyConst.SCOUT_ID.getName()));
                scout.setName(rs.getString(KeyConst.SCOUT_NAME.getName()));
                scout.setBirthDate(rs.getDate(KeyConst.SCOUT_BIRTH_DATE.getName()));
                scout.setCurrentAdvancementId(rs.getInt(KeyConst.SCOUT_ADVANCEMENT_ID.getName()));
                scout.setTypeId(rs.getInt(KeyConst.SCOUT_TYPE_ID.getName()));
            }

        } catch (Exception e) {
            return null;
        }

        return scout;
    }

    public static void save(Scout scout) {
        if (scout == null) {
            return;
        }

        if (!connector.checkForDataBaseConnection()) {
            return;
        }

        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");

        if (scout.getId() < 0) {
            scout.setId(getNextId());
        }

        try {
            Statement statement = connector.createStatement();
            statement.executeUpdate("INSERT INTO scout VALUES( " + scout.getId() + ",'" + scout.getName() + "', '" + df.format(scout.getBirthDate()) + "'," + scout.getCurrentAdvancementId() + "," + scout.getTypeId() + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int getNextId() {
        int id = 1;

        try {
            Statement statement = connector.createStatement();
            ResultSet rs = statement.executeQuery("SELECT MAX(id) AS id FROM scout");

            if(rs.next()) {
                id = rs.getInt(KeyConst.SCOUT_ID.getName()) + 1;
            }

        } catch (Exception e) {
            id = -1;
        }

        return id;
    }
}
