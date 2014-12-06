package util;

import constants.KeyConst;
import scout.dbObjects.MeritBadge;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Nathanael on 10/25/2014
 */
public class LogicMeritBadge {
    private static DBConnector connector;
    private static final Object lock = new Object();

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

    public static List<String> getFilteredList(String name, boolean reqForEagle) {
        if (!connector.checkForDataBaseConnection()) {
            return null;
        }

        List<String> meritBadgeNameList = new ArrayList<String>();

        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT name FROM meritBadge ");

            boolean hasWhere = false;
            if (!Util.isEmpty(name)) {
                query.append("WHERE UPPER(name) LIKE UPPER('%").append(name).append("%') ");
                hasWhere = true;
            }

            if (reqForEagle) {
                if (!hasWhere) {
                    query.append("WHERE ");
                } else {
                    query.append("AND ");
                }

                query.append("requiredForEagle = ").append(1).append(" ");
            }

            query.append("ORDER BY name");

            Statement statement = connector.createStatement();
            ResultSet rs = statement.executeQuery(query.toString());

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
                meritBadge.setRevisionDate(rs.getDate(KeyConst.MERIT_BADGE_REVISION_DATE.getName()));
            }

        } catch (Exception e) {
            return null;
        }

        return meritBadge;
    }

    public static synchronized MeritBadge importBadge(MeritBadge meritBadge) {
        if (meritBadge == null) {
            return null;
        }

        MeritBadge badge = findByName(meritBadge.getName());
        if (badge != null) {
            if (Util.isEmpty(meritBadge.getImgPath())) {
                meritBadge.setImgPath(badge.getImgPath());
            }
            if (meritBadge.getRevisionDate() == null) {
                meritBadge.setRevisionDate(badge.getRevisionDate());
            }
            meritBadge.setId(badge.getId());

            update(meritBadge);
        } else {
            if (Util.isEmpty(meritBadge.getImgPath())) {
                meritBadge.setImgPath("");
            }
            if (meritBadge.getRevisionDate() == null) {
                meritBadge.setRevisionDate(new Date());
            }

            save(meritBadge);
        }

        return meritBadge;
    }

    public static synchronized void save(MeritBadge meritBadge) {
        try {
            synchronized (lock) {
                if (!saveMeritBadge(meritBadge)) {
                    lock.wait(Util.WAIT_TIME);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static boolean saveMeritBadge(MeritBadge meritBadge) {
        if (meritBadge == null) {
            return true;
        }

        if (meritBadge.getId() < 0) {
            meritBadge.setId(getNextId());
        }

        try {
            Statement statement = connector.createStatement();
            statement.executeUpdate("INSERT INTO meritBadge VALUES( " + meritBadge.getId() + ",'" + meritBadge.getName() + "', '" + meritBadge.getImgPath().replace("\\", "\\\\") + "', " + getIntValue(meritBadge.isRequiredForEagle()) + ",'" + Util.DATA_BASE_DATE_FORMAT.format(meritBadge.getRevisionDate()) + "')");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static synchronized void update(MeritBadge meritBadge) {
        try {
            synchronized (lock) {
                if (!updateMeritBadge(meritBadge)) {
                    lock.wait(Util.WAIT_TIME);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean updateMeritBadge(MeritBadge meritBadge) {
        if (meritBadge == null) {
            return true;
        }

        try {
            Statement statement = connector.createStatement();
            statement.executeUpdate("UPDATE meritBadge SET name = '" + meritBadge.getName() + "', imgPath = '" + meritBadge.getImgPath().replace("\\", "\\\\") + "'" + ", requiredForEagle = " + getIntValue(meritBadge.isRequiredForEagle()) + ", revisionDate = '" + Util.DATA_BASE_DATE_FORMAT.format(meritBadge.getRevisionDate()) + "' WHERE id = " + meritBadge.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static int getIntValue(boolean requiredForEagle) {
        return requiredForEagle ? 1 : 0;
    }

    private static int getNextId() {
        int id = 1;

        try {
            Statement statement = connector.createStatement();
            ResultSet rs = statement.executeQuery("SELECT MAX(id) AS id FROM meritBadge");

            if(rs.next()) {
                id = rs.getInt(KeyConst.MERIT_BADGE_ID.getName()) + 1;
            }

        } catch (Exception e) {
            id = -1;
        }

        return id;
    }

    public static List<MeritBadge> findAll() {
        if (!connector.checkForDataBaseConnection()) {
            return null;
        }

        List<MeritBadge> meritBadgeList = new ArrayList<MeritBadge>();

        try {
            Statement statement = connector.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM meritBadge ORDER BY name");

            while (rs.next()) {
                MeritBadge meritBadge = new MeritBadge();
                meritBadge.setId(rs.getInt(KeyConst.MERIT_BADGE_ID.getName()));
                meritBadge.setRequiredForEagle(rs.getBoolean(KeyConst.MERIT_BADGE_REQ_FOR_EAGLE.getName()));
                meritBadge.setName(rs.getString(KeyConst.MERIT_BADGE_NAME.getName()));
                meritBadge.setImgPath(rs.getString(KeyConst.MERIT_BADGE_IMG_PATH.getName()));
                meritBadge.setRevisionDate(rs.getDate(KeyConst.MERIT_BADGE_REVISION_DATE.getName()));
                meritBadgeList.add(meritBadge);
            }

        } catch (Exception e) {
            return null;
        }

        return meritBadgeList;
    }

    public static synchronized void delete(int meritBadgeId) {
        try {
            synchronized (lock) {
                if (!deleteMeritBadge(meritBadgeId)) {
                    lock.wait(Util.WAIT_TIME);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteMeritBadge(int meritBadgeId) {
        if (!connector.checkForDataBaseConnection()) {
            return false;
        }

        if (meritBadgeId < 0) {
            return true;
        }

        try {
            Statement statement = connector.createStatement();
            statement.executeUpdate("DELETE FROM meritBadge WHERE id = " + meritBadgeId + "");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
