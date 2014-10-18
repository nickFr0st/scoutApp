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

    static {
        connector = new DBConnector();
    }

    public static Advancement findAdvancementByName(String name) {
        if (!connector.checkForDataBaseConnection()) {
            return null;
        }

        Advancement advancement = new Advancement();

        try {
            Statement statement = connector.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM advancement WHERE name LIKE '" + name + "'");

            if (rs.next()) {
                advancement.setId(rs.getInt(KeyConst.ADVANCEMENT_ID.getName()));
                advancement.setName(rs.getString(KeyConst.ADVANCEMENT_NAME.getName()));
                advancement.setImgPath(rs.getString(KeyConst.ADVANCEMENT_IMG_PATH.getName()));
            }

        } catch (Exception e) {
            return null;
        }

        return advancement;
    }

    public static List<String> getAdvancementList() {
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

    public static void save(Advancement advancement) {
        if (advancement == null) {
            return;
        }

        if (advancement.getId() < 0) {
            advancement.setId(getNextId());
        }

        try {
            Statement statement = connector.createStatement();
            statement.executeUpdate("INSERT INTO advancement VALUES( " + advancement.getId() + ",'" + advancement.getName() + "', '" + advancement.getImgPath().replace("\\", "\\\\") + "')");
        } catch (Exception e) {
            // save error
        }
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

    public static void delete(String advancementName) {
        if (Util.isEmpty(advancementName)) {
            return;
        }

        try {
            Statement statement = connector.createStatement();
            statement.executeUpdate("DELETE FROM advancement WHERE name LIKE ('" + advancementName + "')");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
