package util;

import constants.KeyConst;
import scout.dbObjects.User;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Malloch on 7/11/14
 */
public class LogicUser {
    private static DBConnector connector;

    static {
        connector = new DBConnector();
    }

    public static User getUser() {
        if (!connector.checkForDBConnection()) {
            return null;
        }

        try {
            Statement statement = connector.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM user");

            User user = new User();
            if (rs.next()) {
                user.setId(rs.getInt(KeyConst.USER_ID.getName()));
                user.setTroopLeader(rs.getString(KeyConst.USER_TROOP_LEADER.getName()));
                user.setTroopNumber(rs.getString(KeyConst.USER_TROOP_NUMBER.getName()));
                user.setCouncil(rs.getString(KeyConst.USER_COUNCIL.getName()));
                user.setTroopName(rs.getString(KeyConst.USER_TROOP_NAME.getName()));
                return user;
            }

        } catch (Exception ignore) {
            return null;
        }

        return null;
    }
}
