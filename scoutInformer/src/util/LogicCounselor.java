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
    private static final Object lock = new Object();

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

    public static List<Integer> findAllIdsByBadgeId(int badgeId) {
        if (!connector.checkForDataBaseConnection()) {
            return null;
        }

        List<Integer> counselorIdList = new ArrayList<Integer>();

        try {
            Statement statement = connector.createStatement();
            ResultSet rs = statement.executeQuery("SELECT id FROM counselor WHERE badgeId = " + badgeId);

            while (rs.next()) {
                counselorIdList.add(rs.getInt(KeyConst.MERIT_BADGE_ID.getName()));
            }

        } catch (Exception e) {
            return null;
        }

        return counselorIdList;
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

    public static synchronized void save(Counselor counselor) {
        try {
            synchronized (lock) {
                if (!saveCounselor(counselor)) {
                    lock.wait(Util.WAIT_TIME);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean saveCounselor(Counselor counselor) {
        if (counselor == null) {
            return true;
        }

        if (counselor.getId() < 0) {
            counselor.setId(getNextId());
        }

        try {
            Statement statement = connector.createStatement();
            statement.executeUpdate("INSERT INTO counselor VALUES( " + counselor.getId() + ", " + counselor.getBadgeId() + ", '" + counselor.getName() + "', '" + counselor.getPhoneNumber() + "')");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static synchronized void update(Counselor counselor) {
        try {
            synchronized (lock) {
                if (!updateCounselor(counselor)) {
                    lock.wait(Util.WAIT_TIME);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static boolean updateCounselor(Counselor counselor) {
        if (counselor == null) {
            return true;
        }

        try {
            Statement statement = connector.createStatement();
            statement.executeUpdate("UPDATE counselor SET name = '" + counselor.getName() + "', badgeId = " + counselor.getBadgeId() + ", phoneNumber = '" + counselor.getPhoneNumber() + "' WHERE id = " + counselor.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
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
            ResultSet rs = statement.executeQuery("SELECT MAX(id) AS id FROM counselor");

            if(rs.next()) {
                id = rs.getInt(KeyConst.MERIT_BADGE_ID.getName()) + 1;
            }

        } catch (Exception e) {
            id = -1;
        }

        return id;
    }

    public static synchronized void deleteList(List<Integer> counselorIdList) {
        try {
            synchronized (lock) {
                if (!deleteCounselorList(counselorIdList)) {
                    lock.wait(Util.WAIT_TIME);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean deleteCounselorList(List<Integer> counselorIdList) {
        if (Util.isEmpty(counselorIdList)) {
            return true;
        }

        try {
            Statement statement = connector.createStatement();
            statement.executeUpdate("DELETE FROM counselor WHERE id IN (" + Util.listToString(counselorIdList) + ")");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void saveList(List<Counselor> counselorList) {
        if (!connector.checkForDataBaseConnection()) {
            return;
        }

        if (Util.isEmpty(counselorList)) {
            return;
        }

        for (Counselor counselor : counselorList) {
            save(counselor);
        }
    }

    public static void updateList(List<Counselor> counselorList, int meritBadgeId) {
        for (Counselor counselor : counselorList) {
            if (counselor.getId() < 0) {
                save(counselor);
            } else {
                update(counselor);
            }
        }

        List<Integer> existingCounselorIdList = findAllIdsByBadgeId(meritBadgeId);
        List<Integer> tempList = new ArrayList<Integer>();

        for (Counselor counselor : counselorList) {
            if (counselor.getId() < 0) {
                continue;
            }

            tempList.add(counselor.getId());
        }

        if (Util.isEmpty(tempList)) {
            deleteList(existingCounselorIdList);
            return;
        }

        List<Integer> deletionList = new ArrayList<Integer>();

        for (Integer id : existingCounselorIdList) {
            if (!tempList.contains(id)) {
                deletionList.add(id);
            }
        }

        deleteList(deletionList);
    }

    public static synchronized void deleteAllByBadgeId(int badgeId) {
        try {
            synchronized (lock) {
                if (!deleteAllCounselorsByBadgeId(badgeId)) {
                    lock.wait(Util.WAIT_TIME);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean deleteAllCounselorsByBadgeId(int badgeId) {
        if (!connector.checkForDataBaseConnection()) {
            return false;
        }

        try {
            Statement statement = connector.createStatement();
            statement.executeUpdate("DELETE FROM counselor WHERE badgeId = " + badgeId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
