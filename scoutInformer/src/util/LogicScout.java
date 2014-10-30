package util;

import constants.KeyConst;

import java.sql.ResultSet;
import java.sql.Statement;
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
            ResultSet rs = statement.executeQuery("SELECT name FROM boyScout ORDER BY name");

            while(rs.next()) {
                ScoutNameList.add(rs.getString(KeyConst.SCOUT_NAME.getName()));
            }

        } catch (Exception e) {
            return null;
        }

        return ScoutNameList;
    }
}
