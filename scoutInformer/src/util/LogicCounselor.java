package util;

import constants.KeyConst;
import scout.dbObjects.Counselor;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nathanael on 10/25/2014
 */
public class LogicCounselor {
    private static DBConnector connector;

    static {
        connector = new DBConnector();
    }

    public static List<Counselor> findAllByBadgeId(int badgeId) {
        if (!connector.checkForDataBaseConnection()) {
            return null;
        }

        List<Counselor> counselorList = new ArrayList<Counselor>();

        try {
            Statement statement = connector.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM counselor WHERE badgeId = " + badgeId + " ORDER BY name");

            while (rs.next()) {
                Counselor counselor = new Counselor();
                counselor.setId(rs.getInt(KeyConst.COUNSELOR_ID.getName()));
                counselor.setBadgeId(rs.getInt(KeyConst.COUNSELOR_BADGE_ID.getName()));
                counselor.setName(rs.getString(KeyConst.COUNSELOR_NAME.getName()));
                counselor.setPhoneNumber(rs.getString(KeyConst.COUNSELOR_PHONE_NUMBER.getName()));
                counselorList.add(counselor);
            }

        } catch (Exception e) {
            return null;
        }

        return counselorList;
    }

    public static void importList(List<Counselor> counselors) {
        if (Util.isEmpty(counselors)) {
            return;
        }

        for (Counselor counselor : counselors) {
            Counselor record = findByNameAndBadgeId(counselor.getName(), counselor.getBadgeId());
            if (record != null) {
                counselor.setId(record.getId());
                update(counselor);
            } else {
                save(counselor);
            }
        }
    }

    public static void save(Counselor counselor) {
        if (counselor == null) {
            return;
        }

        if (counselor.getId() < 0) {
            counselor.setId(getNextId());
        }

        try {
            Statement statement = connector.createStatement();
            statement.executeUpdate("INSERT INTO counselor VALUES( " + counselor.getId() + ", " + counselor.getBadgeId() + ", '" + counselor.getName() + "', '" + counselor.getPhoneNumber() + "')");
        } catch (Exception e) {
            // save error
        }
    }

    public static void update(Counselor counselor) {
        if (counselor == null) {
            return;
        }

        try {
            Statement statement = connector.createStatement();
            statement.executeUpdate("UPDATE counselor SET name = '" + counselor.getName() + "', badgeId = " + counselor.getBadgeId() + ", phoneNumber = '" + counselor.getPhoneNumber() + "' WHERE id = " + counselor.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Counselor findByNameAndBadgeId(String name, int badgeId) {
        if (!connector.checkForDataBaseConnection()) {
            return null;
        }

        Counselor counselor = null;
        try {
            Statement statement = connector.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM counselor WHERE badgeId = " + badgeId + " AND name LIKE '" + name + "'");

            if (rs.next()) {
                counselor = new Counselor();
                counselor.setId(rs.getInt(KeyConst.COUNSELOR_ID.getName()));
                counselor.setBadgeId(rs.getInt(KeyConst.COUNSELOR_BADGE_ID.getName()));
                counselor.setName(rs.getString(KeyConst.COUNSELOR_NAME.getName()));
                counselor.setPhoneNumber(rs.getString(KeyConst.COUNSELOR_PHONE_NUMBER.getName()));
            }

        } catch (Exception e) {
            return null;
        }

        return counselor;
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

    public static void deleteList(List<Integer> counselorIdList) {
        if (Util.isEmpty(counselorIdList)) {
            return;
        }

        try {
            Statement statement = connector.createStatement();
            statement.executeUpdate("DELETE FROM counselor WHERE id IN (" + Util.listToString(counselorIdList) + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
