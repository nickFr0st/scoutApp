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
}
