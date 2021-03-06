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

    public static void save(Camp camp) {
        try {
            synchronized (lock) {
                if (!saveCamp(camp)) {
                    lock.wait(Util.WAIT_TIME);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean saveCamp(Camp camp) {
        if (camp == null) {
            return true;
        }

        if (camp.getId() < 0) {
            camp.setId(getNextId());
        }

        try {
            StringBuilder query = new StringBuilder();
            query.append("INSERT INTO camp VALUES( ");
            query.append(camp.getId()).append(",'");
            query.append(camp.getName()).append("','");
            query.append(camp.getLocation()).append("',");
            query.append(camp.getDuration()).append(",'");
            query.append(Util.DATA_BASE_DATE_FORMAT.format(camp.getDate())).append("'");
            query.append(",'").append(camp.getNote()).append("')");

            Statement statement = connector.createStatement();
            statement.executeUpdate(query.toString());
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
            ResultSet rs = statement.executeQuery("SELECT MAX(id) AS id FROM camp");

            if(rs.next()) {
                id = rs.getInt(KeyConst.CAMP_ID.getName()) + 1;
            }

        } catch (Exception e) {
            id = -1;
        }

        return id;
    }

    public static synchronized void delete(int id) {
        try {
            synchronized (lock) {
                if (!deleteCamp(id)) {
                    lock.wait(Util.WAIT_TIME);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean deleteCamp(int id) {
        if (id <= 0) {
            return true;
        }

        try {
            Statement statement = connector.createStatement();
            statement.executeUpdate("DELETE FROM camp WHERE id = " + id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static synchronized void update(Camp camp) {
        try {
            synchronized (lock) {
                if (!updateCamp(camp)) {
                    lock.wait(Util.WAIT_TIME);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean updateCamp(Camp camp) {
        if (camp == null) {
            return true;
        }

        try {
            StringBuilder query = new StringBuilder();
            query.append("UPDATE camp SET ");
            query.append("name = '").append(camp.getName()).append("'");
            query.append(", location = '").append(camp.getLocation()).append("'");
            query.append(", duration = ").append(camp.getDuration());
            query.append(", date = '").append(Util.DATA_BASE_DATE_FORMAT.format(camp.getDate())).append("'");

            if (!Util.isEmpty(camp.getNote())) {
                query.append(", note = '").append(camp.getNote()).append("'");
            }

            query.append(" WHERE id = ").append(camp.getId());

            Statement statement = connector.createStatement();
            statement.executeUpdate(query.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static List<Camp> findAll() {
        if (!connector.checkForDataBaseConnection()) {
            return null;
        }

        List<Camp> campList = new ArrayList<Camp>();

        try {
            Statement statement = connector.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM camp ORDER BY name");

            while (rs.next()) {
                Camp camp = new Camp();
                camp.setId(rs.getInt(KeyConst.CAMP_ID.getName()));
                camp.setName(rs.getString(KeyConst.CAMP_NAME.getName()));
                camp.setLocation(rs.getString(KeyConst.CAMP_LOCATION.getName()));
                camp.setDuration(rs.getInt(KeyConst.CAMP_DURATION.getName()));
                camp.setDate(rs.getDate(KeyConst.CAMP_DATE.getName()));
                camp.setNote(rs.getString(KeyConst.CAMP_NOTE.getName()));

                campList.add(camp);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return campList;
    }
}
