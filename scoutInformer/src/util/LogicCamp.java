package util;

import constants.KeyConst;
import scout.dbObjects.Camp;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nathanael on 12/9/2014
 */
public class LogicCamp {
    private static DBConnector connector;
    private static final Object lock = new Object();

    static {
        connector = new DBConnector();
    }

    public static List<String> getNameList() {
        if (!connector.checkForDataBaseConnection()) {
            return null;
        }

        List<String> campNameList = new ArrayList<String>();

        try {
            Statement statement = connector.createStatement();
            ResultSet rs = statement.executeQuery("SELECT name FROM camp ORDER BY name");

            while(rs.next()) {
                campNameList.add(rs.getString(KeyConst.CAMP_NAME.getName()));
            }

        } catch (Exception e) {
            return null;
        }

        return campNameList;
    }

    public static Camp findByName(String name) {
        if (!connector.checkForDataBaseConnection()) {
            return null;
        }

        Camp camp = null;

        try {
            Statement statement = connector.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM camp WHERE name LIKE '" + name + "'");

            if (rs.next()) {
                camp = new Camp();
                camp.setId(rs.getInt(KeyConst.CAMP_ID.getName()));
                camp.setName(rs.getString(KeyConst.CAMP_NAME.getName()));
                camp.setLocation(rs.getString(KeyConst.CAMP_LOCATION.getName()));
                camp.setDuration(rs.getInt(KeyConst.CAMP_DURATION.getName()));
                camp.setDate(rs.getDate(KeyConst.CAMP_DATE.getName()));
                camp.setNote(rs.getString(KeyConst.CAMP_NOTE.getName()));
            }

        } catch (Exception e) {
            return null;
        }

        return camp;
    }
}
