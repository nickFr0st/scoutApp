package util;

import constants.KeyConst;
import scout.dbObjects.OtherAward;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Malloch on 7/11/14
 */
public class LogicOtherAward {
    private static DBConnector connector;
    private static final Object lock = new Object();

    static {
        connector = new DBConnector();
    }

    public static OtherAward findByName(String name) {
        if (!connector.checkForDataBaseConnection()) {
            return null;
        }

        OtherAward otherAward = null;

        try {
            Statement statement = connector.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM otherAward WHERE name LIKE '" + name + "'");

            if (rs.next()) {
                otherAward = new OtherAward();
                otherAward.setId(rs.getInt(KeyConst.AWARD_ID.getName()));
                otherAward.setName(rs.getString(KeyConst.AWARD_NAME.getName()));
                otherAward.setImgPath(rs.getString(KeyConst.AWARD_IMG_PATH.getName()));
            }

        } catch (Exception e) {
            return null;
        }

        return otherAward;
    }

    public static List<OtherAward> getAllAwards() {
        if (!connector.checkForDataBaseConnection()) {
            return null;
        }

        List<OtherAward> otherAwardList = new ArrayList<OtherAward>();

        try {
            Statement statement = connector.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM otherAward ORDER BY name");

            while (rs.next()) {
                OtherAward otherAward = new OtherAward();
                otherAward.setId(rs.getInt(KeyConst.AWARD_ID.getName()));
                otherAward.setName(rs.getString(KeyConst.AWARD_NAME.getName()));
                otherAward.setImgPath(rs.getString(KeyConst.AWARD_IMG_PATH.getName()));
                otherAwardList.add(otherAward);
            }

        } catch (Exception e) {
            return null;
        }

        return otherAwardList;
    }

    public static List<String> getNameList() {
        if (!connector.checkForDataBaseConnection()) {
            return null;
        }

        List<String> otherAwardList = new ArrayList<String>();

        try {
            Statement statement = connector.createStatement();
            ResultSet rs = statement.executeQuery("SELECT name FROM otherAward ORDER BY name");

            while(rs.next()) {
                otherAwardList.add(rs.getString(KeyConst.AWARD_NAME.getName()));
            }

        } catch (Exception e) {
            return null;
        }

        return otherAwardList;
    }

    public static synchronized void save(OtherAward otherAward) {
        try {
            synchronized (lock) {
                if (!saveAward(otherAward)) {
                    lock.wait(Util.WAIT_TIME);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean saveAward(OtherAward otherAward) {
        if (otherAward == null) {
            return true;
        }

        if (otherAward.getId() < 0) {
            otherAward.setId(getNextId());
        }

        try {
            Statement statement = connector.createStatement();
            statement.executeUpdate("INSERT INTO otherAward VALUES( " + otherAward.getId() + ",'" + otherAward.getName() + "', '" + otherAward.getImgPath().replace("\\", "\\\\") + "')");
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
            ResultSet rs = statement.executeQuery("SELECT MAX(id) AS id FROM otherAward");

            if(rs.next()) {
                id = rs.getInt(KeyConst.AWARD_ID.getName()) + 1;
            }

        } catch (Exception e) {
            id = -1;
        }

        return id;
    }

    public static synchronized void delete(String otherAwardName) {
        try {
            synchronized (lock) {
                if (!deleteAward(otherAwardName)) {
                    lock.wait(Util.WAIT_TIME);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean deleteAward(String otherAwardName) {
        if (Util.isEmpty(otherAwardName)) {
            return true;
        }

        try {
            Statement statement = connector.createStatement();
            statement.executeUpdate("DELETE FROM otherAward WHERE name LIKE ('" + otherAwardName + "')");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static synchronized void update(OtherAward otherAward) {
        try {
            synchronized (lock) {
                if (!updateAward(otherAward)) {
                    lock.wait(Util.WAIT_TIME);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean updateAward(OtherAward otherAward) {
        if (otherAward == null) {
            return true;
        }

        try {
            Statement statement = connector.createStatement();
            statement.executeUpdate("UPDATE otherAward SET name = '" + otherAward.getName() + "', imgPath = '" + otherAward.getImgPath().replace("\\", "\\\\") + "'" + " WHERE id = " + otherAward.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static OtherAward importAward(OtherAward otherAward) {
        if (otherAward == null) {
            return null;
        }

        OtherAward award = findByName(otherAward.getName());
        if (award != null) {
            if (Util.isEmpty(otherAward.getImgPath())) {
                otherAward.setImgPath(award.getImgPath());
            }
            otherAward.setId(award.getId());

            update(otherAward);
        } else {
            if (Util.isEmpty(otherAward.getImgPath())) {
                otherAward.setImgPath("");
            }

            save(otherAward);
        }

        return otherAward;
    }
}
