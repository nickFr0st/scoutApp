package util;

import constants.KeyConst;

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
}
