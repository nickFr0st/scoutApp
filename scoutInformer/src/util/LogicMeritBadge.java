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
            }

        } catch (Exception e) {
            return null;
        }

        return meritBadge;
    }

    public static MeritBadge importBadge(MeritBadge meritBadge) {
        if (meritBadge == null) {
            return null;
        }

        MeritBadge badge = findByName(meritBadge.getName());
        if (badge != null) {
            if (Util.isEmpty(meritBadge.getImgPath())) {
                meritBadge.setImgPath(badge.getImgPath());
            }
            meritBadge.setId(badge.getId());

            update(meritBadge);
        } else {
            if (Util.isEmpty(meritBadge.getImgPath())) {
                meritBadge.setImgPath("");
            }

            save(meritBadge);
        }

        return meritBadge;
    }

    public static void save(MeritBadge meritBadge) {
        if (meritBadge == null) {
            return;
        }

        if (meritBadge.getId() < 0) {
            meritBadge.setId(getNextId());
        }

        try {
            Statement statement = connector.createStatement();
            statement.executeUpdate("INSERT INTO meritBadge VALUES( " + meritBadge.getId() + ",'" + meritBadge.getName() + "', '" + meritBadge.getImgPath().replace("\\", "\\\\") + "', " + getIntValue(meritBadge.isRequiredForEagle()) + ")");
        } catch (Exception e) {
            // save error
        }
    }

    public static void update(MeritBadge meritBadge) {
        if (meritBadge == null) {
            return;
        }

        try {
            Statement statement = connector.createStatement();
            statement.executeUpdate("UPDATE meritBadge SET name = '" + meritBadge.getName() + "', imgPath = '" + meritBadge.getImgPath().replace("\\", "\\\\") + "'" + ", requiredForEagle = " + getIntValue(meritBadge.isRequiredForEagle()) + " WHERE id = " + meritBadge.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                meritBadgeList.add(meritBadge);
            }

        } catch (Exception e) {
            return null;
        }

        return meritBadgeList;
    }

    public static void delete(int meritBadgeId) {
        if (meritBadgeId < 0) {
            return;
        }

        try {
            Statement statement = connector.createStatement();
            statement.executeUpdate("DELETE FROM meritBadge WHERE id = " + meritBadgeId + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
