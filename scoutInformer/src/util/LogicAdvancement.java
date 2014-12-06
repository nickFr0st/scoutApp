package util;

import constants.KeyConst;
import scout.dbObjects.Advancement;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Malloch on 7/11/14
 */
public class LogicAdvancement {
    private static DBConnector connector;
    private static final Object lock = new Object();

    static {
        connector = new DBConnector();
    }

    public static Advancement findByName(String name) {
        if (!connector.checkForDataBaseConnection()) {
            return null;
        }

        Advancement advancement = null;

        try {
            Statement statement = connector.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM advancement WHERE name LIKE '" + name + "'");

            if (rs.next()) {
                advancement = new Advancement();
                advancement.setId(rs.getInt(KeyConst.ADVANCEMENT_ID.getName()));
                advancement.setName(rs.getString(KeyConst.ADVANCEMENT_NAME.getName()));
                advancement.setImgPath(rs.getString(KeyConst.ADVANCEMENT_IMG_PATH.getName()));
            }

        } catch (Exception e) {
            return null;
        }

        return advancement;
    }

    public static List<Advancement> findAllAdvancements() {
        if (!connector.checkForDataBaseConnection()) {
            return null;
        }

        List<Advancement> advancementList = new ArrayList<Advancement>();

        try {
            Statement statement = connector.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM advancement ORDER BY name");

            while (rs.next()) {
                Advancement advancement = new Advancement();
                advancement.setId(rs.getInt(KeyConst.ADVANCEMENT_ID.getName()));
                advancement.setName(rs.getString(KeyConst.ADVANCEMENT_NAME.getName()));
                advancement.setImgPath(rs.getString(KeyConst.ADVANCEMENT_IMG_PATH.getName()));
                advancementList.add(advancement);
            }

        } catch (Exception e) {
            return null;
        }

        return advancementList;
    }

    public static List<String> getNameList() {
        if (!connector.checkForDataBaseConnection()) {
            return null;
        }

        List<String> advancementList = new ArrayList<String>();

        try {
            Statement statement = connector.createStatement();
            ResultSet rs = statement.executeQuery("SELECT name FROM advancement ORDER BY name");

            while(rs.next()) {
                advancementList.add(rs.getString(KeyConst.ADVANCEMENT_NAME.getName()));
            }

        } catch (Exception e) {
            return null;
        }

        return advancementList;
    }

    public static synchronized void save(Advancement advancement) {
        try {
            synchronized (lock) {
                if (!saveAdvancement(advancement)) {
                    lock.wait(Util.WAIT_TIME);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean saveAdvancement(Advancement advancement) {
        if (advancement == null) {
            return true;
        }

        if (advancement.getId() < 0) {
            advancement.setId(getNextId());
        }

        try {
            Statement statement = connector.createStatement();
            statement.executeUpdate("INSERT INTO advancement VALUES( " + advancement.getId() + ",'" + advancement.getName() + "', '" + advancement.getImgPath().replace("\\", "\\\\") + "')");
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
            ResultSet rs = statement.executeQuery("SELECT MAX(id) AS id FROM advancement");

            if(rs.next()) {
                id = rs.getInt(KeyConst.ADVANCEMENT_ID.getName()) + 1;
            }

        } catch (Exception e) {
            id = -1;
        }

        return id;
    }

    public static synchronized void delete(String advancementName) {
        try {
            synchronized (lock) {
                if (!deleteAdvancement(advancementName)) {
                    lock.wait(Util.WAIT_TIME);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean deleteAdvancement(String advancementName) {
        if (Util.isEmpty(advancementName)) {
            return true;
        }

        try {
            Statement statement = connector.createStatement();
            statement.executeUpdate("DELETE FROM advancement WHERE name LIKE ('" + advancementName + "')");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static synchronized void update(Advancement advancement) {
        try {
            synchronized (lock) {
                if (!updateAdvancement(advancement)) {
                    lock.wait(Util.WAIT_TIME);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean updateAdvancement(Advancement advancement) {
        if (advancement == null) {
            return true;
        }

        try {
            Statement statement = connector.createStatement();
            statement.executeUpdate("UPDATE advancement SET name = '" + advancement.getName() + "', imgPath = '" + advancement.getImgPath().replace("\\", "\\\\") + "'" + " WHERE id = " + advancement.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static Advancement importAdv(Advancement advancement) {
        Advancement adv = findByName(advancement.getName());
        if (adv != null) {
            if (Util.isEmpty(advancement.getImgPath())) {
                advancement.setImgPath(adv.getImgPath());
            }
            advancement.setId(adv.getId());

            update(advancement);
        } else {
            if (Util.isEmpty(advancement.getImgPath())) {
                advancement.setImgPath("");
            }

            save(advancement);
        }

        return advancement;
    }

    public static Advancement findById(int id) {
        if (!connector.checkForDataBaseConnection()) {
            return null;
        }

        Advancement advancement = null;

        try {
            Statement statement = connector.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM advancement WHERE id = " + id);

            if (rs.next()) {
                advancement = new Advancement();
                advancement.setId(rs.getInt(KeyConst.ADVANCEMENT_ID.getName()));
                advancement.setName(rs.getString(KeyConst.ADVANCEMENT_NAME.getName()));
                advancement.setImgPath(rs.getString(KeyConst.ADVANCEMENT_IMG_PATH.getName()));
            }

        } catch (Exception e) {
            return null;
        }

        return advancement;
    }
}
