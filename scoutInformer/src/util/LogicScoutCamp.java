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

    public static synchronized void save(int campId, List<Integer> scoutIdList) {
        try {
            synchronized (lock) {
                if (!saveScoutCamp(campId, scoutIdList)) {
                    lock.wait(Util.WAIT_TIME);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean saveScoutCamp(int campId, List<Integer> scoutIdList) {
        if (campId < 0 || Util.isEmpty(scoutIdList)) {
            return true;
        }

        for (Integer scoutId : scoutIdList) {
            try {
                ScoutCamp scoutCamp = new ScoutCamp();
                scoutCamp.setId(getNextId());
                scoutCamp.setCampId(campId);
                scoutCamp.setScoutId(scoutId);

                StringBuilder query = new StringBuilder();
                query.append("INSERT INTO scoutCamp VALUES(");
                query.append(scoutCamp.getId()).append(",");
                query.append(scoutCamp.getScoutId()).append(",");
                query.append(scoutCamp.getCampId()).append(")");

                Statement statement = connector.createStatement();
                statement.executeUpdate(query.toString());
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }


    private static int getNextId() {
        int id = 1;

        try {
            Statement statement = connector.createStatement();
            ResultSet rs = statement.executeQuery("SELECT MAX(id) AS id FROM scoutCamp");

            if(rs.next()) {
                id = rs.getInt(KeyConst.SCOUT_CAMP_ID.getName()) + 1;
            }

        } catch (Exception e) {
            id = -1;
        }

        return id;
    }

    public static synchronized void deleteAllByCampId(int campId) {
        try {
            synchronized (lock) {
                if (!deleteByCampId(campId)) {
                    lock.wait(Util.WAIT_TIME);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean deleteByCampId(int campId) {
        if (campId <= 0) {
            return true;
        }

        try {
            Statement statement = connector.createStatement();
            statement.executeUpdate("DELETE FROM scoutCamp WHERE campId = " + campId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static boolean deleteByScoutId(int scoutId) {
        if (scoutId <= 0) {
            return true;
        }

        try {
            Statement statement = connector.createStatement();
            statement.executeUpdate("DELETE FROM scoutCamp WHERE scoutId = " + scoutId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static synchronized void deleteAllByScoutId(int scoutId) {
        try {
            synchronized (lock) {
                if (!deleteByScoutId(scoutId)) {
                    lock.wait(Util.WAIT_TIME);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
