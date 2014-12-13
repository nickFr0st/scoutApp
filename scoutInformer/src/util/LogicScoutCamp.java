package util;

import constants.KeyConst;
import scout.dbObjects.ScoutCamp;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nathanael on 12/9/2014
 */
public class LogicScoutCamp {
    private static DBConnector connector;
    private static final Object lock = new Object();

    static {
        connector = new DBConnector();
    }

    public static List<ScoutCamp> findAllByCampId(int campId) {
        if (!connector.checkForDataBaseConnection()) {
            return null;
        }

        List<ScoutCamp> scoutCampList = new ArrayList<ScoutCamp>();

        try {
            Statement statement = connector.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM scoutCamp WHERE campId = " + campId);

            while(rs.next()) {
                ScoutCamp scoutCamp = new ScoutCamp();
                scoutCamp.setId(rs.getInt(KeyConst.SCOUT_CAMP_ID.getName()));
                scoutCamp.setCampId(rs.getInt(KeyConst.SCOUT_CAMP_CAMP_ID.getName()));
                scoutCamp.setScoutId(rs.getInt(KeyConst.SCOUT_CAMP_SCOUT_ID.getName()));

                scoutCampList.add(scoutCamp);
            }

        } catch (Exception e) {
            return null;
        }

        return scoutCampList;
    }

    public static List<ScoutCamp> findAllByScoutId(int scoutId) {
        if (!connector.checkForDataBaseConnection()) {
            return null;
        }

        List<ScoutCamp> scoutCampList = new ArrayList<ScoutCamp>();

        try {
            Statement statement = connector.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM scoutCamp WHERE scoutId = " + scoutId);

            while(rs.next()) {
                ScoutCamp scoutCamp = new ScoutCamp();
                scoutCamp.setId(rs.getInt(KeyConst.SCOUT_CAMP_ID.getName()));
                scoutCamp.setCampId(rs.getInt(KeyConst.SCOUT_CAMP_CAMP_ID.getName()));
                scoutCamp.setScoutId(rs.getInt(KeyConst.SCOUT_CAMP_SCOUT_ID.getName()));

                scoutCampList.add(scoutCamp);
            }

        } catch (Exception e) {
            return null;
        }

        return scoutCampList;
    }
}
