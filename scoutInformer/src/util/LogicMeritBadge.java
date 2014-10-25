package util;

import constants.KeyConst;
import scout.dbObjects.MeritBadge;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nathanael on 10/25/2014
 */
public class LogicMeritBadge {
    private static DBConnector connector;

    static {
        connector = new DBConnector();
    }

    public static List<String> getNameList() {
        if (!connector.checkForDataBaseConnection()) {
            return null;
        }

        List<String> meritBadgeNameList = new ArrayList<String>();

        try {
            Statement statement = connector.createStatement();
            ResultSet rs = statement.executeQuery("SELECT name FROM meritBadge ORDER BY name");

            while(rs.next()) {
                meritBadgeNameList.add(rs.getString(KeyConst.MERIT_BADGE_NAME.getName()));
            }

        } catch (Exception e) {
            return null;
        }

        return meritBadgeNameList;
    }

    public static MeritBadge findByName(String name) {
        if (!connector.checkForDataBaseConnection()) {
            return null;
        }

        MeritBadge meritBadge = null;

        try {
            Statement statement = connector.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM meritBadge WHERE name LIKE '" + name + "'");

            if (rs.next()) {
                meritBadge = new MeritBadge();
                meritBadge.setId(rs.getInt(KeyConst.MERIT_BADGE_ID.getName()));
                meritBadge.setName(rs.getString(KeyConst.MERIT_BADGE_NAME.getName()));
                meritBadge.setImgPath(rs.getString(KeyConst.MERIT_BADGE_IMG_PATH.getName()));
                meritBadge.setRequiredForEagle(rs.getBoolean(KeyConst.MERIT_BADGE_REQ_FOR_EAGLE.getName()));
            }

        } catch (Exception e) {
            return null;
        }

        return meritBadge;
    }
}
