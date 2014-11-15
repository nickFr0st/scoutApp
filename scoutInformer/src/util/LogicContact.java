package util;

import constants.KeyConst;
import scout.dbObjects.Contact;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nathanael on 10/29/2014
 */
public class LogicContact {
    private static DBConnector connector;

    static {
        connector = new DBConnector();
    }

    public static List<Contact> findAllByScoutId(int scoutId) {
        if (!connector.checkForDataBaseConnection()) {
            return null;
        }

        List<Contact> contactList = new ArrayList<Contact>();

        try {
            Statement statement = connector.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM contact WHERE scoutId = " + scoutId);

            if (rs.next()) {
                Contact contact = new Contact();
                contact.setId(rs.getInt(KeyConst.CONTACT_ID.getName()));
                contact.setScoutId(rs.getInt(KeyConst.CONTACT_SCOUT_ID.getName()));
                contact.setTypeId(rs.getInt(KeyConst.CONTACT_TYPE_ID.getName()));
                contact.setName(rs.getString(KeyConst.CONTACT_NAME.getName()));
                contact.setRelation(rs.getString(KeyConst.CONTACT_RELATION.getName()));
                contact.setData(rs.getString(KeyConst.CONTACT_DATA.getName()));

                contactList.add(contact);
            }

        } catch (Exception e) {
            return null;
        }

        return contactList;
    }
}
