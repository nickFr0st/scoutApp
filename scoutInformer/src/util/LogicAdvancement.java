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
            ResultSet rs = statement.executeQuery("SELECT name FROM advancement");

            while(rs.next()) {
                advancementList.add(rs.getString(KeyConst.ADVANCEMENT_NAME.getName()));
            }

        } catch (Exception e) {
            return null;
        }

        return advancementList;
    }
}
