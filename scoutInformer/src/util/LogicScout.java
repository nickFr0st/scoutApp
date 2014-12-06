package util;

import constants.KeyConst;
import scout.dbObjects.Scout;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nathanael on 10/29/2014
 */
public class LogicScout {
    private static DBConnector connector;
    private static final Object lock = new Object();

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
                scout.setAdvancementDate(rs.getDate(KeyConst.SCOUT_ADVANCEMENT_DATE.getName()));
                scout.setPosition(rs.getString(KeyConst.SCOUT_POSITION.getName()));
                scout.setPostionDate(rs.getDate(KeyConst.SCOUT_POSITION_DATE.getName()));
                scout.setTypeId(rs.getInt(KeyConst.SCOUT_TYPE_ID.getName()));
            }

        } catch (Exception e) {
            return null;
        }

        return scout;
    }

    public static synchronized void save(Scout scout) {
        try {
            synchronized (lock) {
                if (!saveScout(scout)) {
                    lock.wait(Util.WAIT_TIME);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean saveScout(Scout scout) {
        if (scout == null) {
            return true;
        }

        if (scout.getId() < 0) {
            scout.setId(getNextId());
        }

        try {
            Statement statement = connector.createStatement();
            statement.executeUpdate("INSERT INTO scout VALUES( " + scout.getId() + ",'" + scout.getName() + "', '" + Util.DATA_BASE_DATE_FORMAT.format(scout.getBirthDate()) + "'," + scout.getCurrentAdvancementId() + ",'" + Util.DATA_BASE_DATE_FORMAT.format(scout.getAdvancementDate()) + "','" + scout.getPosition() + "','" + Util.DATA_BASE_DATE_FORMAT.format(scout.getPostionDate()) + "'," + scout.getTypeId() + ")");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static synchronized void update(Scout scout) {
        try {
            synchronized (lock) {
                if (!updateScout(scout)) {
                    lock.wait(Util.WAIT_TIME);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean updateScout(Scout scout) {
        if (scout == null) {
            return true;
        }

        try {
            Statement statement = connector.createStatement();
            statement.executeUpdate("UPDATE scout SET name = '" + scout.getName() + "', birthDate = '" + Util.DATA_BASE_DATE_FORMAT.format(scout.getBirthDate()) + "', advancementId = " + scout.getCurrentAdvancementId() + ", position = '" + scout.getPosition() + "', positionDate = '" + Util.DATA_BASE_DATE_FORMAT.format(scout.getPostionDate()) + "' WHERE id = " + scout.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
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

    public static synchronized void delete(int id) {
        try {
            synchronized (lock) {
                if (!deleteScout(id)) {
                    lock.wait(Util.WAIT_TIME);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean deleteScout(int id) {
        if (id <= 0) {
            return true;
        }

        try {
            Statement statement = connector.createStatement();
            statement.executeUpdate("DELETE FROM scout WHERE id = " + id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
