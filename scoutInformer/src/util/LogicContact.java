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

    public static void saveList(List<Contact> contactList) {
        if (Util.isEmpty(contactList) || !connector.checkForDataBaseConnection()) {
            return;
        }

        for (Contact contact : contactList) {

            if (contact.getId() < 0) {
                contact.setId(getNextId());
            }

            try {
                Statement statement = connector.createStatement();
                statement.executeUpdate("INSERT INTO contact VALUES( " + contact.getId() + "," + contact.getScoutId() + "," + contact.getTypeId() + ",'" + contact.getName() + "', '" + contact.getRelation() + "','" + contact.getData() + "')");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static int getNextId() {
        int id = 1;

        try {
            Statement statement = connector.createStatement();
            ResultSet rs = statement.executeQuery("SELECT MAX(id) AS id FROM contact");

            if(rs.next()) {
                id = rs.getInt(KeyConst.CONTACT_ID.getName()) + 1;
            }

        } catch (Exception e) {
            id = -1;
        }

        return id;
    }
}
